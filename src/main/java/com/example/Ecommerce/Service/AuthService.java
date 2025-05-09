package com.example.Ecommerce.Service;


import com.example.Ecommerce.Models.*;
import com.example.Ecommerce.Repository.CartRepository;
import com.example.Ecommerce.Repository.CustomerRepository;
import com.example.Ecommerce.Repository.SupplierRepository;
import com.example.Ecommerce.Repository.UserRepository;
import com.example.Ecommerce.Models.auth.AuthenticateBody;
import com.example.Ecommerce.Models.auth.AuthenticationResponse;
import com.example.Ecommerce.Models.auth.RegisterBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final CartRepository cartRepository;



    /**
     * Creates and saves a user based on the provided role and registration details
     *
     * @param role The role of the user being registered
     * @param body The registration details
     * @return The saved user entity
     */
    public User saveUser(Role role, RegisterBody body) {
        // Validate input parameters
        if (role == null || body == null) {
            throw new IllegalArgumentException("Role and registration body must not be null");
        }

        // Create appropriate user type based on role
        switch (role.name()) {
            case "Customer":
                return saveCustomer(body);
            case "Supplier":
                return saveSupplier(body);
            default:
                return saveDefaultUser(body);
        }
    }

    private Customer saveCustomer(RegisterBody body) {
        Customer customer = buildCustomerFromBody(body);
        Cart cart = Cart.builder()
                .customer(customer)
                .totalAmount(0)
                .totalPrice(0.0)
                .cartProducts(List.of())
                .build();
        customer.setCart(cart);
        customerRepository.save(customer);
        cartRepository.save(cart);
        return customer;
    }
    private Supplier saveSupplier(RegisterBody body) {
        Supplier supplier = buildSupplierFromBody(body);
        supplierRepository.save(supplier);
        return supplier;
    }

    private User saveDefaultUser(RegisterBody body) {
        User user = buildUserFromBody(body);
        userRepository.save(user);
        return user;
    }

    private Customer buildCustomerFromBody(RegisterBody body) {
        return Customer.builder()
                .email(body.getEmail())
                .userName(body.getUserName())
                .firstName(body.getFirstName())
                .lastName(body.getLastName())
                .address(body.getAddress())
                .phoneNumber(body.getPhoneNumber())
                .role(body.getRole())
                .dateOfBirth(body.getDateOfBirth())
                .password(passwordEncoder.encode(body.getPassword()))
                .money(0.0)
                .build();
    }

    private Supplier buildSupplierFromBody(RegisterBody body) {
        return Supplier.builder()
                .email(body.getEmail())
                .userName(body.getUserName())
                .firstName(body.getFirstName())
                .lastName(body.getLastName())
                .address(body.getAddress())
                .phoneNumber(body.getPhoneNumber())
                .role(body.getRole())
                .dateOfBirth(body.getDateOfBirth())
                .password(passwordEncoder.encode(body.getPassword()))
                .rating(5.0)
                .build();
    }

    private User buildUserFromBody(RegisterBody body) {
        return User.builder()
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
    }

    public AuthenticationResponse register(RegisterBody body) {
        Role role = body.getRole();
        User user = saveUser(role,body);
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
