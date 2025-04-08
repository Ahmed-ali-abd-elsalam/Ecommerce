package com.example.Ecommerce.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
public class CartProductsKey implements Serializable {
    @Column(name = "Cart_Id")
    Long cartId;
    @Column(name = "Product_Id")
    Long productId;
}
