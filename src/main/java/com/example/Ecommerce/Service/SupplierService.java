package com.example.Ecommerce.Service;


import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.DTOS.ProductRequestDto;
import com.example.Ecommerce.DTOS.SupplierRequestDto;
import com.example.Ecommerce.DTOS.SupplierResponseDto;
import com.example.Ecommerce.Mappers.ProductMapper;
import com.example.Ecommerce.Mappers.SupplierMapper;
import com.example.Ecommerce.Models.Product;
import com.example.Ecommerce.Models.Supplier;
import com.example.Ecommerce.Models.auth.AuthenticationResponse;
import com.example.Ecommerce.Repository.ProductRepository;
import com.example.Ecommerce.Repository.SupplierRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Supplier returnCurrentSupplier(Authentication authentication){
        return supplierRepository.findByEmail(authentication.getName()).orElseThrow(()->new UsernameNotFoundException("this Supplier "+authentication.getName() +" not in  database"));
    }
    public SupplierResponseDto getSupplierInfo(Authentication authentication) {

        return SupplierMapper.MapToResponseDto(returnCurrentSupplier(authentication));
    }

    @Transactional
    public AuthenticationResponse editSupplierInfo(Authentication authentication, SupplierRequestDto supplierRequestDto) {
        Supplier supplier1 = returnCurrentSupplier(authentication);
//        Supplier supplier = SupplierMapper.MapToSupplier(supplierRequestDto);
        supplier1.setFirstName(supplierRequestDto.firstName());
        supplier1.setLastName(supplierRequestDto.lastName());
        supplier1.setUserName(supplierRequestDto.userName());
        supplier1.setEmail(supplierRequestDto.email());
        supplier1.setPassword(passwordEncoder.encode(supplierRequestDto.password()));
        supplier1.setPhoneNumber(supplierRequestDto.phoneNumber());
        supplier1.setAddress(supplierRequestDto.address());
        supplier1.setRating(supplierRequestDto.rating());
        String jwt = jwtService.generateToken(supplier1);
        return AuthenticationResponse.builder().JWTtoken(jwt).build();
    }

    @Transactional
    public ProductDto addNewProduct(Authentication authentication, ProductRequestDto productRequestDto) {
//        if product already exists go to edit if not add new product
        Supplier supplier = returnCurrentSupplier(authentication);
        List<Product> products = supplier.getProducts();
        for(Product _product:products){
            if( _product.getName().equals(productRequestDto.name()) &&
                    _product.getCategory().equals(productRequestDto.category()) &&
                    _product.getSubCategory().equals(productRequestDto.subCategory())){
                editProductInfo(authentication,_product.getId(),productRequestDto);
                Product product = ProductMapper.MapRequestToProduct(productRequestDto);
                product.setSupplier(supplier);
                return ProductMapper.MapToDto(product);
            }
        }
        Product product = ProductMapper.MapRequestToProduct(productRequestDto);
        product.setSupplier(supplier);
        productRepository.save(product);
        return ProductMapper.MapToDto(product);
    }
    @Transactional
    public String editProductInfo(Authentication authentication, Long productID, ProductRequestDto product) {
        Supplier supplier = returnCurrentSupplier(authentication);
        Product product1 = productRepository.findById(productID).orElseThrow(()->new NoSuchElementException("product with ID "+productID+" name "+product.name()+" Doesn't exist"));
        if(!product1.getSupplier().equals((supplier))){
            return "You cant edit this product its not yours";
        }
        product1.setName(product.name());
        product1.setDescription(product.description());
        product1.setCategory(product.category());
        product1.setSubCategory(product.subCategory());
        product1.setBrand(product.brand());
        product1.setPrice(product.price());
        product1.setQuantity(product.quantity());
        product1.setImageUrl(product.imageUrl());
        return "Edit complete";
    }
}

