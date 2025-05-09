package com.example.Ecommerce.Controller;

import com.example.Ecommerce.DTOS.CartDto;
import com.example.Ecommerce.DTOS.OrderDto;
import com.example.Ecommerce.DTOS.ProductDto;


import com.example.Ecommerce.Service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;
    @GetMapping
    public CartDto GetCurrentCart(Authentication authentication){
        return cartService.getCurrentCart(authentication);
    }

    @PutMapping(path = "/{CartID}")
    public CartDto editCartItems(Authentication authentication, @PathVariable Long CartID,@Valid @RequestBody List<ProductDto> productMap){
        return cartService.editCartItems(authentication,CartID,productMap);
    }

    @PostMapping(path = "/Finalize/{CartID}")
    public OrderDto finalizePurchase(Authentication authentication, @PathVariable Long CartID,@RequestBody String paymentMethod){
        return cartService.finalizePurchase(authentication,CartID,paymentMethod);
    }
}
