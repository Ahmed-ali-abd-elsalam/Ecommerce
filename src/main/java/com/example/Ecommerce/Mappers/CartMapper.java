package com.example.Ecommerce.Mappers;

import com.example.Ecommerce.DTOS.CartDto;
import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.Models.Cart;
import com.example.Ecommerce.Models.CartProducts;
import com.example.Ecommerce.Models.Product;

import java.util.List;

public class CartMapper {
    public static CartDto CartToDtoMapper(Cart cart){

        List<ProductDto> productDtoList = cart.getCartProducts().stream().map(cartProducts -> {
            Product product = cartProducts.getProduct();
            product.setPrice(cartProducts.getPrice());
            product.setQuantity(cartProducts.getQuantity());
            return ProductMapper.MapToDto(product);
        }).toList();
        return new CartDto(cart.getId(),
                cart.getTotalAmount(),
                cart.getTotalPrice(),
                productDtoList);
    }
    public static Cart DtoToCartMapper(CartDto cartDto
//            , Customer customer
    )
    {
        Cart cart = Cart.builder()
//                .products(cartDto.products().stream().map(ProductMapper::MapDtoToProduct).toList())
                .Id(cartDto.Id())
                .totalPrice(cartDto.totalPrice())
                .totalPrice(cartDto.totalPrice())
                .build();
        List<CartProducts> cartProductsList = cartDto.products().stream().map(productDto ->
                CartProducts.builder()
                        .cart(cart)
                        .product(ProductMapper.MapDtoToProduct(productDto))
                        .quantity(productDto.quantity())
                        .price(productDto.price())
                        .build()).toList();
        cart.setCartProducts(cartProductsList);
        return cart;
    }
}
