package com.example.Ecommerce.Mappers;

import com.example.Ecommerce.DTOS.CartDto;
import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.Models.Cart;
import com.example.Ecommerce.Models.Customer;

import java.util.List;

public class CartMapper {
    public static CartDto CartToDtoMapper(Cart cart){
        List<ProductDto> productDtoList = cart.getProducts().stream().map(ProductMapper::MapToDto).toList();
        return new CartDto(cart.getId(),
                cart.getTotalAmount(),
                cart.getTotalPrice(),
                productDtoList);
    }
    public static Cart DtoToCartMapper(CartDto cartDto, Customer customer){
        return Cart.builder()
                .products(cartDto.products().stream().map(ProductMapper::MapDtoToProduct).toList())
                .Id(cartDto.Id())
                .totalPrice(cartDto.totalPrice())
                .totalPrice(cartDto.totalPrice())
                .build();
    }
}
