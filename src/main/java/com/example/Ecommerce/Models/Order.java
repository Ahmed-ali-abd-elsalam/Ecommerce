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
@Table(name = "_Orders")
public class Order {
    @Id
    @SequenceGenerator(name = "OrderId",initialValue = 1,allocationSize = 1)
    @GeneratedValue(generator = "OrderId",strategy = GenerationType.SEQUENCE)
    private Long Id;
    private LocalDate orderDate;
    private Integer totalAmount;
    private Double totalPrice;
    private String  paymentMethod;

    @ManyToMany(cascade = CascadeType.ALL)
            @JoinTable(name = "OrderProducts",joinColumns =
                    @JoinColumn(name = "ProductId"),inverseJoinColumns = {@JoinColumn(name = "PastOrderId")}
            )
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "Cust_id")
    private Customer customer;
}
