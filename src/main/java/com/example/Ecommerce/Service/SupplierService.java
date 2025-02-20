package com.example.Ecommerce.Service;


import com.example.Ecommerce.Models.Product;
import com.example.Ecommerce.Models.Supplier;
import com.example.Ecommerce.Repository.ProductRepository;
import com.example.Ecommerce.Repository.SupplierRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public Supplier returnCurrentSupplier(Authentication authentication){
        return supplierRepository.findByEmail(authentication.getName()).orElseThrow(()->new UsernameNotFoundException("this Supplier "+authentication.getName() +" not in  database"));
    }
    public Supplier getSupplierInfo(Authentication authentication) {
        return  returnCurrentSupplier(authentication);
    }

    @Transactional
    public String  editSupplierInfo(Authentication authentication, Supplier supplier) {
        Supplier supplier1 = returnCurrentSupplier(authentication);
        supplier1.setFirstName(supplier.getFirstName());
        supplier1.setLastName(supplier.getLastName());
        supplier1.setUserName(supplier.getUsername());
        supplier1.setEmail(supplier.getEmail());
        supplier1.setPhoneNumber(supplier.getPhoneNumber());
        supplier1.setAddress(supplier.getPhoneNumber());
        supplier1.setRating(supplier.getRating());
        return "Done";
    }

    public Product addNewProduct(Authentication authentication, Product product) {
//        if product already exists go to edit if not add new product
        Supplier supplier = returnCurrentSupplier(authentication);
        List<Product> products = supplier.getProducts();
        for(Product _product:products){
            if( _product.getName().equals(product.getName()) &&
                    _product.getCategory().equals(product.getCategory()) &&
                    _product.getSubCategory().equals(product.getSubCategory())){
                editProductInfo(authentication,_product.getId(),product);
                return product;
            }
        }
        product.setSupplier(supplier);
        productRepository.save(product);
        return product;
    }

    @Transactional
    public String editProductInfo(Authentication authentication, Long productID, Product product) {
        Supplier supplier = returnCurrentSupplier(authentication);
        Product product1 = productRepository.findById(productID).orElseThrow(()->new NoSuchElementException("product with name "+product.getName()+"Doesn't exist"));
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setCategory(product.getCategory());
        product1.setSubCategory(product.getSubCategory());
        product1.setBrand(product.getBrand());
        product1.setPrice(product.getPrice());
        product1.setQuantity(product.getQuantity());
        product1.setImageUrl(product.getImageUrl());
        return "Edit complete";
    }
}

