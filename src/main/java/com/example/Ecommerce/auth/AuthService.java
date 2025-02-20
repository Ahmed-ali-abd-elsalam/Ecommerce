package com.example.Ecommerce.auth;


import com.example.Ecommerce.Models.Customer;
import com.example.Ecommerce.Models.Supplier;
import com.example.Ecommerce.Models.User;
import com.example.Ecommerce.Repository.CustomerRepository;
import com.example.Ecommerce.Repository.SupplierRepository;
import com.example.Ecommerce.Repository.UserRepository;
import com.example.Ecommerce.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Ecommerce.Models.Role;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;

    public User saveUser(Role role,RegisterBody body){
        User user;
        if (role.name().equals("Customer")){
            Customer customer = Customer.builder()
                    .email(body.getEmail())
                    .userName(body.getUserName())
                    .firstName(body.getFirstName())
                    .lastName(body.getLastName())
                    .address(body.getAddress())
                    .phoneNumber(body.getPhoneNumber())
                    .role(body.getRole())
                    .dateOfBirth(body.getDateOfBirth())
                    .password(passwordEncoder.encode(body.getPassword()))
                    .build();
            customerRepository.save(customer);
            user = customer;
        }
        else if (role.name().equals("Customer")){
            Supplier supplier = Supplier.builder()
                    .email(body.getEmail())
                    .userName(body.getUserName())
                    .firstName(body.getFirstName())
                    .lastName(body.getLastName())
                    .address(body.getAddress())
                    .phoneNumber(body.getPhoneNumber())
                    .role(body.getRole())
                    .dateOfBirth(body.getDateOfBirth())
                    .password(passwordEncoder.encode(body.getPassword()))
                    .build();
            supplierRepository.save(supplier);
            user = supplier;
        }
        else{
            User usera = User.builder()
                    .email(body.getEmail())
                    .userName(body.getUserName())
                    .firstName(body.getFirstName())
                    .lastName(body.getLastName())
                    .address(body.getAddress())
                    .phoneNumber(body.getPhoneNumber())
                    .role(body.getRole())
                    .dateOfBirth(body.getDateOfBirth())
                    .password(passwordEncoder.encode(body.getPassword()))
                    .build();
            userRepository.save(usera);
            user =usera;
        }
        return  user;
    }

    public AuthenticationResponse register(RegisterBody body) {
        Role role = body.getRole();
        User user = saveUser(role,body);
//        Map<String, Object> extraClaims = new HashMap<>();
//        extraClaims.put("Id",user.getID());
        return AuthenticationResponse.builder()
                .JWTtoken(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse Authenticate(AuthenticateBody body) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        body.getEmail()
                        , body.getPassword()
                )
        );
        User user = userRepository.findByEmail(body.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Not Found Exception"));
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .JWTtoken(jwt).build();
    }

}
