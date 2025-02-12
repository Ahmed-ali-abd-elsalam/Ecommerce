package com.example.Ecommerce.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class PastOrder {
    @Id
    @SequenceGenerator(name = "OrderId",initialValue = 1,allocationSize = 1)
    @GeneratedValue(generator = "OrderId",strategy = GenerationType.SEQUENCE)
    Long Id;
    LocalDate orderDate;
    int totalAmount;
    double totalPrice;
    String  paymentMethod;
    String State;

    @ManyToMany(cascade = CascadeType.ALL)
            @JoinTable(name = "OrderProducts",joinColumns =
                    @JoinColumn(name = "ProductId"),inverseJoinColumns = {@JoinColumn(name = "PastOrderId")}
            )
    List<Product> products;

    @ManyToOne
    @JoinColumn(name = "Cust_id")
    Customer customer;
}
