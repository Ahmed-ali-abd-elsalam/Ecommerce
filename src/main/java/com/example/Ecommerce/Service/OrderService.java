package com.example.Ecommerce.Service;


import com.example.Ecommerce.DTOS.OrderDto;
import com.example.Ecommerce.Mappers.OrderMapper;
import com.example.Ecommerce.Models.*;
import com.example.Ecommerce.Repository.CustomerRepository;
import com.example.Ecommerce.Repository.OrderProductsRepository;
import com.example.Ecommerce.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final OrderProductsRepository orderProductsRepository;

    public OrderDto SaveOrder(Long customerId, Cart cart, String paymentMethod) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("no customer with id"+customerId));
        List<OrderProducts> orderProductsList = new ArrayList<>();
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .customer(customer)
                .totalPrice(cart.getTotalPrice())
                .totalAmount(cart.getTotalAmount())
                .paymentMethod(paymentMethod)
                .build();
        cart.getCartProducts().forEach(cartProducts -> {
            OrderProducts orderProducts = OrderProducts.builder()
                    .order(order)
                    .product(cartProducts.getProduct())
                    .price(cartProducts.getPrice())
                    .quantity(cartProducts.getQuantity())
                    .build();
            orderProductsList.add(orderProducts);
        });
        order.setOrderproducts(orderProductsList);
        orderRepository.save(order);
        orderProductsRepository.saveAll(orderProductsList);
        return OrderMapper.OrderToDtoMapper(order);
    }

    public List<OrderDto> getCustomerOrders(Authentication authentication) {
        Customer customer = customerService.returnCustomer(authentication);
        return customer.getOrders().stream().map(OrderMapper::OrderToDtoMapper).toList();
    }


    public OrderDto getCustomerOrder(Authentication authentication,Long orderID) {
        Customer customer = customerService.returnCustomer(authentication);
        Order order =  orderRepository.findByIdAndCustomer(orderID,customer).orElseThrow(()->new NoSuchElementException("Order with ID "+orderID + " doesn't belong to current user "));
        return OrderMapper.OrderToDtoMapper(order);
    }


}
