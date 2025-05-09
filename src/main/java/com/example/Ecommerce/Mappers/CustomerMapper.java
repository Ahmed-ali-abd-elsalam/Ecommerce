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
                CartMapper.CartToDtoMapper(customer.getCart()),
                customer.getOrders().stream().map(order -> OrderMapper.OrderToDtoMapper(order)).toList()
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
                .money(customerRequestDto.money())
                .build();
    }
}
