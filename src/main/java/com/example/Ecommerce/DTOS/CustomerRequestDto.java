package com.example.Ecommerce.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

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
        String phoneNumber,
        String address,
        @Past
        LocalDate dateOfBirth,
        @PositiveOrZero
        Double money
) {
}
