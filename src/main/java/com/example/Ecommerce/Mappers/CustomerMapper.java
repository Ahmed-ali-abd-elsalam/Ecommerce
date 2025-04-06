package com.example.Ecommerce.Mappers;

import com.example.Ecommerce.DTOS.CustomerRequestDto;
import com.example.Ecommerce.DTOS.CustomerResponseDto;
import com.example.Ecommerce.Models.Customer;

public class CustomerMapper {
    public static CustomerResponseDto MapToResponseDto(Customer customer){
        return new CustomerResponseDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getUsername(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAddress(),
                customer.getDateOfBirth(),
                customer.getRole(),
                customer.getMoney(),
                customer.getCart(),
                customer.getOrders()
                );
    }
    public static Customer MapToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .firstName(customerRequestDto.firstName())
                .lastName(customerRequestDto.lastName())
                .userName(customerRequestDto.userName())
                .email(customerRequestDto.email())
                .password(customerRequestDto.password())
                .phoneNumber(customerRequestDto.phoneNumber())
                .address(customerRequestDto.address())
                .dateOfBirth(customerRequestDto.dateOfBirth())
                .role(customerRequestDto.role())
                .money(customerRequestDto.money())
                .cart(CartMapper.DtoToCartMapper(customerRequestDto.cart()))
                .orders(customerRequestDto.orders())
                .build();
    }
}
