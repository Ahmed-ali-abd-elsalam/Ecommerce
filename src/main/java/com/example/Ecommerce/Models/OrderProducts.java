package com.example.Ecommerce.Models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderProducts {

    @EmbeddedId
    OrderProductsKey Id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "Order_Id")
    Order order;


    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "Product_Id")
    Product product;

    @Column(nullable = false)
    Integer quantity;
    @Column(nullable = false)
    Double price;



}
