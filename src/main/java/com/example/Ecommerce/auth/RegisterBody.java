package com.example.Ecommerce.auth;

import com.example.Ecommerce.Models.Role;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBody {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private Role role;

}
