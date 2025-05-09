package com.example.Ecommerce.DTOS;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Valid
public record SupplierRequestDto(
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
        @PositiveOrZero
                @DecimalMax(value = "5.0")
        Double rating
) {
}
