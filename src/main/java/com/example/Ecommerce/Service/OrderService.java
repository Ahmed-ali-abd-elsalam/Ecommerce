package com.example.Ecommerce.Service;


import com.example.Ecommerce.DTOS.OrderDto;
import com.example.Ecommerce.Mappers.OrderMapper;
import com.example.Ecommerce.Models.*;
import com.example.Ecommerce.Repository.OrderProductsRepository;
import com.example.Ecommerce.Repository.OrderRepository;
import com.example.Ecommerce.Repository.ProductRepository;
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

    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final OrderProductsRepository orderProductsRepository;



    public OrderDto SaveOrder(Customer customer, Cart cart, String paymentMethod) {
        List<OrderProducts> orderProductsList = new ArrayList<>();
        List<CartProducts> cartProductsList = cart.getCartProducts();
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .customer(customer)
                .totalPrice(cart.getTotalPrice())
                .totalAmount(cart.getTotalAmount())
                .paymentMethod(paymentMethod)
                .build();
        for (int i = 0; i < cartProductsList.size(); i++) {
            CartProducts cartProducts = cartProductsList.get(i);
            OrderProductsKey key = new OrderProductsKey(order.getId(),cartProducts.getProduct().getId());
            OrderProducts orderProducts = OrderProducts.builder()
                    .Id(key)
                    .product(cartProducts.getProduct())
                    .order(order)
                    .price(cartProducts.getPrice())
                    .quantity(cartProducts.getQuantity())
                    .build();
            orderProductsList.add(orderProducts);
        }
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
