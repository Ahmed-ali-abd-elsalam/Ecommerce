package com.example.Ecommerce.auth;


import com.example.Ecommerce.Models.User;
import com.example.Ecommerce.Repository.UserRepository;
import com.example.Ecommerce.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    public AuthenticationResponse register(RegisterBody body) {
        User user = User.builder()
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
        userRepository.save(user);
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
