package com.example.Ecommerce.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

public class Seller extends User {

    private double rating;

    @ManyToMany(mappedBy = "suppliers")
    private List<Product> products;
}
