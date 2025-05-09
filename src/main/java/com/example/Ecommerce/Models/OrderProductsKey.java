package com.example.Ecommerce.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderProductsKey implements Serializable {
    @Column(name = "Order_Id")
    Long orderId;
    @Column(name = "Product_Id")
    Long productId;
}
