package com.example.Ecommerce.Repository;

import com.example.Ecommerce.Models.OrderProducts;
import com.example.Ecommerce.Models.OrderProductsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, OrderProductsKey> {
}
