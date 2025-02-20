package com.example.Ecommerce.Repository;

import com.example.Ecommerce.Models.Product;
import com.example.Ecommerce.Models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByNameAndSupplier(String name, Supplier supplier);
}
