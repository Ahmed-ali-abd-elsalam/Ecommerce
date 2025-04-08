package com.example.Ecommerce.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Product {
    @Id
    @SequenceGenerator(initialValue = 1,allocationSize = 1,name = "ProductId")
    @GeneratedValue(generator = "ProductId",strategy = GenerationType.SEQUENCE)
    private Long Id;
    private String name;
    private String description;
    private String category;
    private String subCategory;
    private String brand;
    private double price;
    private int quantity;
//    image url
    private String imageUrl;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "Supplier_ID")
    private Supplier supplier;

//    @ManyToMany(mappedBy = "products")
//    private List<Order> orders;
//    @ManyToMany(mappedBy = "products")
//    private List<Cart> carts;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    List<OrderProducts> Orderproducts;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    List<CartProducts> cartProducts;

}
