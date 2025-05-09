package com.example.Ecommerce.Models.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateBody {
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
}
