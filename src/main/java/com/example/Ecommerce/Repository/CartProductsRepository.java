package com.example.Ecommerce.Repository;

import com.example.Ecommerce.Models.Cart;
import com.example.Ecommerce.Models.CartProducts;
import com.example.Ecommerce.Models.CartProductsKey;
import com.example.Ecommerce.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts, CartProductsKey> {

    Optional<CartProducts> findByCartAndProduct(Cart cart, Product product);

}
