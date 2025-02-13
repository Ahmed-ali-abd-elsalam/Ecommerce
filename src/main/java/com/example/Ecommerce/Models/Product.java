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

    @ManyToMany(cascade = CascadeType.ALL)
            @JoinTable(joinColumns = {@JoinColumn  (name = "ProductId")},inverseJoinColumns = {@JoinColumn(name = "SupplierId")})
    private List<Seller> suppliers;

    @ManyToMany(mappedBy = "products")
    private List<PastOrder> orders;


}
