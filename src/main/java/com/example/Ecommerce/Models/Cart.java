package com.example.Ecommerce.Models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Transactional
@Entity
@Table
public class Cart {

    @Id
    @SequenceGenerator(name = "CartId",initialValue = 1,allocationSize = 1)
    @GeneratedValue(generator = "CartId",strategy = GenerationType.SEQUENCE)
    private Long Id;
    private Integer totalAmount;
    private Double totalPrice;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(name = "CartProducts",joinColumns =
                @JoinColumn(name = "ProductId"),inverseJoinColumns = {@JoinColumn(name = "CartId")}
                )

//    possible issue in here
    private List<Product> products;

    @OneToOne(mappedBy = "cart")
    private Customer customer;


}
