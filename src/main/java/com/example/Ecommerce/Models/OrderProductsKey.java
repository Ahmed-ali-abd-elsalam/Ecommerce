package com.example.Ecommerce.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
public class OrderProductsKey implements Serializable {
    @Column(name = "Order_Id")
    Long orderId;
    @Column(name = "Product_Id")
    Long productId;
}
