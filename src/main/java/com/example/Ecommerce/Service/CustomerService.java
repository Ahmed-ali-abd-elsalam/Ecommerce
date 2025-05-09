package com.example.Ecommerce.Service;

import com.example.Ecommerce.DTOS.CustomerRequestDto;
import com.example.Ecommerce.DTOS.CustomerResponseDto;
import com.example.Ecommerce.Mappers.CustomerMapper;
import com.example.Ecommerce.Models.Customer;
import com.example.Ecommerce.Models.auth.AuthenticationResponse;
import com.example.Ecommerce.Repository.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public Customer returnCustomer(Authentication authentication){
        String email = authentication.getName();
        return  customerRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Email "+ email+ " Not a customer in database"));
    }

    public CustomerResponseDto getCustomerInfo(Authentication authentication) {
        return CustomerMapper.MapToResponseDto(returnCustomer(authentication));
    }

    @Transactional
//    after edit return new token
    public AuthenticationResponse editCustomerInfo(Authentication authentication, CustomerRequestDto customer) {
        Customer customer1 = returnCustomer(authentication);
        customer1.setFirstName(customer.firstName());
        customer1.setLastName(customer.lastName());
        customer1.setUserName(customer.userName());
        customer1.setEmail(customer.email());
        customer1.setPassword(passwordEncoder.encode(customer.password()));
        customer1.setPhoneNumber(customer.phoneNumber());
        customer1.setAddress(customer.address());
        customer1.setMoney(customer.money());
        String jwt = jwtService.generateToken(customer1);
        return AuthenticationResponse.builder().JWTtoken(jwt).build();
    }

}
