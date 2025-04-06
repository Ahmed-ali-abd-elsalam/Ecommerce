package com.example.Ecommerce.DTOS;

import com.example.Ecommerce.Models.Cart;
import com.example.Ecommerce.Models.Order;
import com.example.Ecommerce.Models.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;

@Valid
public record CustomerResponseDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String userName,
        @Email
        String email,
        @NotBlank
        String phoneNumber,
        String address,
        @Past
        LocalDate dateOfBirth,
        Role role,
        @PositiveOrZero
        Double money,
        Cart cart,
        List<Order> orders

) {
}
