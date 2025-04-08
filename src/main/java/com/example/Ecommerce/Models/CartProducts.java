package com.example.Ecommerce.Models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartProducts {
    @EmbeddedId
    CartProductsKey Id;

    @ManyToOne
            @MapsId("cartId")
            @JoinColumn(name = "Cart_Id")
    Cart cart;

    @ManyToOne
            @MapsId("productId")
            @JoinColumn(name = "Product_Id")

    Product product;

    @Column(nullable = false)
    Integer quantity;
    @Column(nullable = false)
    Double price;
}
