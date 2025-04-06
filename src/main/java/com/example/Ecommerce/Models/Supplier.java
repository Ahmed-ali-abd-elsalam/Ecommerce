package com.example.Ecommerce.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

public class Supplier extends User {

    private Double rating;

    @ManyToMany(mappedBy = "supplier",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> products;
}
