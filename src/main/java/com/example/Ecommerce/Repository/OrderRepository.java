package com.example.Ecommerce.Repository;

import com.example.Ecommerce.Models.Customer;
import com.example.Ecommerce.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByIdAndCustomer(Long orderID, Customer customer);
}
