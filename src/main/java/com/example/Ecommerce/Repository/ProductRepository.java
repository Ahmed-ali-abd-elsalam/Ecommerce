package com.example.Ecommerce.Repository;

import com.example.Ecommerce.Models.Product;
import com.example.Ecommerce.Models.Supplier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByNameAndSupplier(String name, Supplier supplier);

    @Query(value = "SELECT DISTINCT category FROM Product",nativeQuery = true)
    List<String> findAllDistinctCategories();

    List<Product> findByCategory(String category, Pageable page);

    List<Product> findByNameContaining(String name, Pageable page);

}
