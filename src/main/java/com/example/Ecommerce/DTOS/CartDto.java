package com.example.Ecommerce.DTOS;

import com.example.Ecommerce.Models.Customer;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record CartDto(Long Id,
                      @PositiveOrZero
                      Integer totalAmount,
                      @PositiveOrZero
                      Double totalPrice,
                      List<ProductDto> products
                ) {
}
