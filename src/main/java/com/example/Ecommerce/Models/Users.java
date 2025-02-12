package com.example.Ecommerce.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table
public class Users {

    @Id
    Long ID;
    String userName;
    String email;
    String password;
    String phoneNumber;
    String Address;
    LocalDate DateOfBirth;
}
