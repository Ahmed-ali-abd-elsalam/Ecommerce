package com.example.Ecommerce.DTOS;

import com.example.Ecommerce.Models.Supplier;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Valid
public record ProductDto(
        @Positive
        Long Id,
        @NotBlank
        String name,
        String description,
        String category,
        String subCategory,
        String brand,
        @Positive
        double price,
        @PositiveOrZero
        int quantity,
        @Pattern(
                regexp = "^(https?:\\/\\/)?([\\w-]+(\\.[\\w-]+)+)(\\/[-\\w@:%_+.~#?&/=]*)?$",
                message = "Invalid URL format"
        )
        String imageUrl,
        @Valid
        Supplier supplier
) {
}
