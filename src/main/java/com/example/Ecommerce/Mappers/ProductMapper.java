package com.example.Ecommerce.Mappers;

import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.DTOS.ProductRequestDto;
import com.example.Ecommerce.Models.Product;

public class ProductMapper {
    public static Product MapRequestToProduct(ProductRequestDto productDto){
        return Product.builder()
                .name(productDto.name())
                .description(productDto.description())
                .category(productDto.category())
                .subCategory(productDto.subCategory())
                .brand(productDto.brand())
                .price(productDto.price())
                .quantity(productDto.quantity())
                .imageUrl(productDto.imageUrl())
                .build();
    }
    public static ProductDto MapToDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getSubCategory(),
                product.getBrand(),
                product.getPrice(),
                product.getQuantity(),
                product.getImageUrl(),
                product.getSupplier().getUsername(),
                product.getSupplier().getID());
//                SupplierMapper.MapToResponseDto(product.getSupplier()));
    }
    public static Product MapDtoToProduct(ProductDto productDto){
        return Product.builder()
                .Id(productDto.Id())
                .name(productDto.name())
                .description(productDto.description())
                .category(productDto.category())
                .subCategory(productDto.subCategory())
                .brand(productDto.brand())
                .price(productDto.price())
                .quantity(productDto.quantity())
                .imageUrl(productDto.imageUrl())

//                .supplier(SupplierMapper.MapToSupplier(productDto.supplier()))
                .build();
    }
}
