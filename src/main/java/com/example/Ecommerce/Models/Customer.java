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
public class Customer extends Users{
    double money;

    @OneToMany(mappedBy ="customer" )
    List<PastOrder> orders;
}
