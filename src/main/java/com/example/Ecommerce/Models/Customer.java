package com.example.Ecommerce.Models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Transactional
@Entity
@Table
public class Customer extends User{

    private Double money;

    @OneToMany(mappedBy ="customer" )
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "CartId")
    private Cart cart;
}
