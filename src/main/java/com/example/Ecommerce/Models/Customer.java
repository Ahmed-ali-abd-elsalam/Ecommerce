package com.example.Ecommerce.Models;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table
public class Customer extends User{
    private double money;

    @OneToMany(mappedBy ="customer" )
    private List<PastOrder> orders;
}
