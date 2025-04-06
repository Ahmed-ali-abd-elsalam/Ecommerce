package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Service.AuthService;
import com.example.Ecommerce.auth.AuthenticateBody;
import com.example.Ecommerce.auth.AuthenticationResponse;
import com.example.Ecommerce.auth.RegisterBody;
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
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterBody body){
        return  ResponseEntity.ok(authService.register(body));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticateBody body){
        return  ResponseEntity.ok(authService.Authenticate(body));
    }

}
