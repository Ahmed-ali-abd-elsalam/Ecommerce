package com.example.Ecommerce.DTOS;

import com.example.Ecommerce.Models.Customer;

import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        Long Id,
        LocalDate orderDate,
        Integer totalAmount,
        Double totalPrice,
        String  paymentMethod,
        List<ProductDto> products
) {
}
