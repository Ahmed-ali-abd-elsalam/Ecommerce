package com.example.Ecommerce.DTOS;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Valid
public record ProductRequestDto (
        @NotBlank
        String name,
        String description,
        String category,
        String subCategory,
        String brand,
        @Positive
        double price,
        @Positive
        int quantity,
        @Pattern(
                regexp = "^(https?:\\/\\/)?([\\w-]+(\\.[\\w-]+)+)(\\/[-\\w@:%_+.~#?&/=]*)?$",
                message = "Invalid URL format"
        )
        String imageUrl
){
}
