package com.example.Ecommerce.Controller;


import com.example.Ecommerce.Models.Customer;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Models.User;
import com.example.Ecommerce.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/Customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public Customer getCustomerInfo(Authentication authentication){
        return customerService.getCustomerInfo(authentication);
    }

    @PutMapping
    public String editCustomerInfo(Authentication authentication,@RequestBody Customer customer){
        return customerService.editCustomerInfo(authentication,customer);
    }

    @GetMapping(path = "/Orders")
    public List<Order> getCustomerOrders(Authentication authentication){
        return customerService.getCustomerOrders(authentication);
    }
    @GetMapping(path = "/Orders/{OrderID}")
    public Order getCustomerOrder(@PathVariable Long OrderID,Authentication authentication){
        return customerService.getCustomerOrder(authentication,OrderID);
    }

}
