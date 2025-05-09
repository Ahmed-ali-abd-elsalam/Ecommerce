package com.example.Ecommerce.Models.auth;

import com.example.Ecommerce.Models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBody {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String userName;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String phoneNumber;
    private String address;
    @Past
    private LocalDate dateOfBirth;
    private Role role;
}
