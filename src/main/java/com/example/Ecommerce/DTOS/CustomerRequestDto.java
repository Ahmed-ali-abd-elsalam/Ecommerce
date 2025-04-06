package com.example.Ecommerce.DTOS;

import com.example.Ecommerce.Models.Cart;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;

public record CustomerRequestDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String userName,
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String phoneNumber,
        String address,
        @Past
        LocalDate dateOfBirth,
        Role role,
        @PositiveOrZero
        Double money,
        CartDto cart,
        List<Order> orders
) {
}
