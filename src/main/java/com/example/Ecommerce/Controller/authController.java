package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Service.AuthService;
import com.example.Ecommerce.Models.auth.AuthenticateBody;
import com.example.Ecommerce.Models.auth.AuthenticationResponse;
import com.example.Ecommerce.Models.auth.RegisterBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class authController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterBody body){
        return  ResponseEntity.ok(authService.register(body));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody AuthenticateBody body){
        return  ResponseEntity.ok(authService.Authenticate(body));
    }

}
