package com.example.Ecommerce.Models;

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
    Long Id;
    String name;
    String description;
    String category;
    String subCategory;
    String brand;
    double price;
    int quantity;
//    image url
    String imageUrl;

    @ManyToMany(cascade = CascadeType.ALL)
            @JoinTable(joinColumns = {@JoinColumn  (name = "ProductId")},inverseJoinColumns = {@JoinColumn(name = "SupplierId")})
    List<Seller> suppliers;

    @ManyToMany(mappedBy = "products")
    List<PastOrder> orders;


}
