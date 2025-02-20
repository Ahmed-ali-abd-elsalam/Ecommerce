package com.example.Ecommerce.Repository;

import com.example.Ecommerce.Models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    Optional<Supplier> findByEmail(String email);
}
