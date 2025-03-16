package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Models.Cart;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Models.Product;

import com.example.Ecommerce.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/Cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;
    @GetMapping
    public Cart GetCurrentCart(Authentication authentication){
        return cartService.getCurrentCart(authentication);
    }

    @PutMapping(path = "/{CartID}")
    public Cart editCartItems(Authentication authentication, @PathVariable Long CartID, Map<Long, Product> productMap){
        return cartService.editCartItems(authentication,CartID,productMap);
    }

    @PostMapping(path = "/{CartID}/Finalize")
    public Order finalizePurchase(Authentication authentication,@PathVariable Long CartID,String paymentMethod){
        return cartService.finalizePurchase(authentication,CartID,paymentMethod);
    }
}
