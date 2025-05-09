package com.example.Ecommerce.Models;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartProducts {
    @EmbeddedId
    CartProductsKey Id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @MapsId("cartId")
    Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @MapsId("productId")
    Product product;

    @Column(nullable = false)
    Integer quantity;
    @Column(nullable = false)
    Double price;
}
