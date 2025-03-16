package com.example.Ecommerce.Service;


import com.example.Ecommerce.Models.Cart;
import com.example.Ecommerce.Models.Customer;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Repository.CustomerRepository;
import com.example.Ecommerce.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;

    public Order SaveOrder(Long customerId, Cart cart,String paymentMethod) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("no customer with id"+customerId));
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .customer(customer)
                .totalPrice(cart.getTotalPrice())
                .totalAmount(cart.getTotalAmount())
                .paymentMethod(paymentMethod)
                .build();
        orderRepository.save(order);
        return order;
    }

    public List<Order> getCustomerOrders(Authentication authentication) {
        Customer customer = customerService.returnCustomer(authentication);
        return customer.getOrders();
    }


    public Order getCustomerOrder(Authentication authentication,Long orderID) {
        Customer customer = customerService.returnCustomer(authentication);
        return  orderRepository.findByIdAndCustomer(orderID,customer).orElseThrow(()->new NoSuchElementException("Order with ID "+orderID + " doesn't belong to current user "));
    }


}
