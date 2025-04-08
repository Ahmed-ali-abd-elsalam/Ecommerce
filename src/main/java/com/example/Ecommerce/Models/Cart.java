package com.example.Ecommerce.Models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Cart {

    @Id
    @SequenceGenerator(name = "CartId",initialValue = 1,allocationSize = 1)
    @GeneratedValue(generator = "CartId",strategy = GenerationType.SEQUENCE)
    private Long Id;
    private Integer totalAmount;
    private Double totalPrice;
//    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonManagedReference
//    @JoinTable(name = "CartProducts",joinColumns =
//                @JoinColumn(name = "ProductId"),inverseJoinColumns = {@JoinColumn(name = "CartId")}
//                )
//
////    possible issue in here
//    private List<Product> products;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    List<CartProducts> cartProducts;

    @OneToOne(mappedBy = "cart")
    private Customer customer;


}
