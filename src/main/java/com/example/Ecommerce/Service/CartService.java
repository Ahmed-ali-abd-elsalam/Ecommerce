package com.example.Ecommerce.Service;

import com.example.Ecommerce.DTOS.CartDto;
import com.example.Ecommerce.DTOS.OrderDto;
import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.Mappers.CartMapper;
import com.example.Ecommerce.Models.*;
import com.example.Ecommerce.Repository.CartProductsRepository;
import com.example.Ecommerce.Repository.CartRepository;
import com.example.Ecommerce.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CustomerService customerService;
    private final CartRepository cartRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final CartProductsRepository cartProductsRepository;


    //    check correct cart
    public Cart checkCart(Authentication authentication,Long cartId){
        Customer customer = customerService.returnCustomer(authentication);
        if(!customer.getCart().getId().equals(cartId)){
            throw new RuntimeException("cart with Id"+cartId+"Doesn't belong to current user");
        }
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("no cart In Repo"));
    }

    public CartDto getCurrentCart(Authentication authentication) {
        Customer customer = customerService.returnCustomer(authentication);
        return CartMapper.CartToDtoMapper(customer.getCart());
    }


    public CartDto editCartItems(Authentication authentication, Long cartID, List<ProductDto> productDtoList) {
        Cart cart = checkCart(authentication,cartID);
        List<CartProducts> cartProductsList = cart.getCartProducts();
        HashMap<Long,Integer> productsIndex = new HashMap<>();
        int diffAmount=0;
        double diffPrice =0;
        for(int i =0;i<cartProductsList.size();i++){
            productsIndex.put(cartProductsList.get(i).getId().getProductId(), i);
        }
        for(int i = 0; i< productDtoList.size(); i++){
                Long Id = productDtoList.get(i).Id();
            if(productsIndex.containsKey(Id)){
                CartProducts cartProducts = cartProductsList.get(productsIndex.get(Id));
                ProductDto updated = productDtoList.get(i);
                int diff =  updated.quantity()- cartProductsList.get(i).getQuantity();
                diffAmount = diffAmount+diff;
                diffPrice = diffPrice+diff*cartProducts.getProduct().getPrice();
                cartProducts.setQuantity(cartProductsList.get(i).getQuantity()+diff);
                cartProducts.setPrice(cartProducts.getPrice()+diff*cartProducts.getProduct().getPrice());
                cartProductsList.set(i,cartProducts);
            }
        }
        cart.setTotalPrice(cart.getTotalPrice()+diffPrice);
        cart.setTotalAmount(cart.getTotalAmount()+diffAmount);
        cart.setCartProducts(cartProductsList);
        cartRepository.save(cart);
        return CartMapper.CartToDtoMapper(cart);
    }



@Transactional
    public OrderDto finalizePurchase(Authentication authentication, Long cartID, String paymentMethod) {
        Customer customer = customerService.returnCustomer(authentication);
        Cart cart = checkCart(authentication,cartID);

        OrderDto orderDto = orderService.SaveOrder(customer,cart,paymentMethod);
        List<CartProducts> cartProductsList = cart.getCartProducts();
        for (CartProducts cartProduct : cartProductsList) {
            CartProductsKey key = cartProduct.getId();
            int amount = cartProduct.getQuantity();
            productService.editStock(key.getProductId(), amount);
        }
        cartProductsRepository.deleteAllByCart(cart);
        cart.setTotalPrice(0.0);
        cart.setTotalAmount(0);
        cart.getCartProducts().clear();
        cartRepository.save(cart);
        return orderDto;
    }
}
