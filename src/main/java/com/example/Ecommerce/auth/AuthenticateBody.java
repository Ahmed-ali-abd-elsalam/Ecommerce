package com.example.Ecommerce.auth;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateBody {
    private String email;
    private String password;
}
