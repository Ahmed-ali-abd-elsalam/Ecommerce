package com.example.Ecommerce.Controller;


import com.example.Ecommerce.Models.Customer;

import com.example.Ecommerce.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



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



}
