package com.example.Ecommerce.Controller;

import com.example.Ecommerce.DTOS.OrderDto;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/Orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getCustomerOrders(Authentication authentication){
        return orderService.getCustomerOrders(authentication);
    }
    @GetMapping(path = "/{OrderID}")
    public OrderDto getCustomerOrder(@PathVariable Long OrderID, Authentication authentication){
        return orderService.getCustomerOrder(authentication,OrderID);
    }
}
