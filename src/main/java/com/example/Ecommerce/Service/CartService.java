package com.example.Ecommerce.Service;

import com.example.Ecommerce.DTOS.CartDto;
import com.example.Ecommerce.DTOS.OrderDto;
import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.Mappers.CartMapper;
import com.example.Ecommerce.Models.*;
import com.example.Ecommerce.Repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CustomerService customerService;
    private final CartRepository cartRepository;
    private final OrderService orderService;
    private final ProductService productService;

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

    @Transactional
    public CartDto editCartItems(Authentication authentication, Long cartID, Map<Long, ProductDto> productDtoMap) {
        Cart cart = checkCart(authentication,cartID);
        List<CartProducts> cartProductsList = cart.getCartProducts();
        for(int i = 0; i< cartProductsList.size(); i++){
                Long Id = cartProductsList.get(i).getProduct().getId();
                CartProducts cartProducts = cartProductsList.get(i);
            if(productDtoMap.containsKey(Id)){
                ProductDto updated = productDtoMap.get(Id);
                int diff =  updated.quantity()- cartProductsList.get(i).getQuantity();
                cart.setTotalAmount(cart.getTotalAmount()+diff);
                cart.setTotalPrice(cart.getTotalPrice()+diff*updated.price());
                cartProducts.setQuantity(updated.quantity());
                cartProducts.setPrice(updated.price());
                cartProductsList.set(i,cartProducts);
            }
        }
        cart.setCartProducts(cartProductsList);
        return CartMapper.CartToDtoMapper(cart);
    }

    @Transactional
    public OrderDto finalizePurchase(Authentication authentication, Long cartID, String paymentMethod) {
//        check cart and customer then pass them to order to finalize
        Customer customer = customerService.returnCustomer(authentication);
        Cart cart = checkCart(authentication,cartID);
        OrderDto orderdto = orderService.SaveOrder(customer.getID(),cart,paymentMethod);
//        List<Product> products = cart.getProducts();
        List<CartProducts> cartProductsList = cart.getCartProducts();

        for (CartProducts cartProduct : cartProductsList) {
            Long productID = cartProduct.getProduct().getId();
            int amount = cartProduct.getQuantity();
            productService.editStock(productID, amount);
        }
        cartProductsList.clear();
//        need to check if cart is cleared and cartproducts is cleared with finalize or not
        cart.setCartProducts(cartProductsList);
        cart.setTotalPrice(0.0);
        cart.setTotalAmount(0);
//        return orderDto
        return orderdto;
    }
}
