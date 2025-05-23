package com.example.Ecommerce.Mappers;

import com.example.Ecommerce.DTOS.SupplierResponseDto;
import com.example.Ecommerce.Models.Supplier;

public class SupplierMapper {
    public static SupplierResponseDto MapToResponseDto(Supplier supplier){
        return new SupplierResponseDto(
                supplier.getFirstName(),
                supplier.getLastName(),
                supplier.getUsername(),
                supplier.getEmail(),
                supplier.getPhoneNumber(),
                supplier.getAddress(),
                supplier.getDateOfBirth(),
                supplier.getRole(),
                supplier.getRating(),
                supplier.getProducts().stream().map(product -> ProductMapper.MapToDto(product)).toList()
        );
    }
    public static Supplier MapToSupplier(SupplierResponseDto supplierRequest){
        return Supplier.builder().firstName(supplierRequest.firstName())
                .lastName(supplierRequest.lastName())
                .userName(supplierRequest.userName())
                .email(supplierRequest.email())
                .phoneNumber(supplierRequest.phoneNumber())
                .address(supplierRequest.address())
                .dateOfBirth(supplierRequest.dateOfBirth())
                .rating(supplierRequest.rating())
                .build();
    }
}
