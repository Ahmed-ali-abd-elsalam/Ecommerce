package com.example.Ecommerce.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CartProductsKey implements Serializable {
    @Column(name = "cart_id")
    private Long cartId;
    @Column(name = "product_id")
    private Long productId;
}
