package com.example.Ecommerce.Service;

import com.example.Ecommerce.Models.Customer;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Repository.CustomerRepository;
import com.example.Ecommerce.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public Customer returnCustomer(Authentication authentication){
        String email = authentication.getName();
        return  customerRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Email "+ email+ " Not in database"));
    }

    public Customer getCustomerInfo(Authentication authentication) {
        return  returnCustomer(authentication);
    }

    @Transactional
    public String editCustomerInfo(Authentication authentication, Customer customer) {
        Customer customer1 = returnCustomer(authentication);
        customer1.setFirstName(customer.getFirstName());
        customer1.setLastName(customer.getLastName());
        customer1.setUserName(customer.getUsername());
        customer1.setEmail(customer.getEmail());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        customer1.setAddress(customer.getPhoneNumber());
        customer1.setMoney(customer.getMoney());
        return "Done";
    }



    public List<Order> getCustomerOrders(Authentication authentication) {
        Customer customer = returnCustomer(authentication);
        return customer.getOrders();
    }


    public Order getCustomerOrder(Authentication authentication,Long orderID) {
        Customer customer = returnCustomer(authentication);
        return  orderRepository.findByIdAndCustomer(orderID,customer).orElseThrow(()->new NoSuchElementException("Order with ID "+orderID + " doesn't belong to current user "));
//        return orderRepository.findById(orderID).orElseThrow(()->new NoSuchElementException("Order with ID "+orderID + " Doesn't exist"));
    }
}
