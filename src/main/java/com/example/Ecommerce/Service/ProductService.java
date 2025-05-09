package com.example.Ecommerce.Service;

import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.Mappers.ProductMapper;
import com.example.Ecommerce.Models.*;
import com.example.Ecommerce.Repository.CartProductsRepository;
import com.example.Ecommerce.Repository.CartRepository;
import com.example.Ecommerce.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final CartProductsRepository cartProductsRepository;
    private final CartRepository cartRepository;

    public void editStock(long productID,int amount){
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new NoSuchElementException("product with ID"+productID+"Doesn't Exist"));
        product.setQuantity(product.getQuantity()-amount);
        productRepository.save(product);
    }

    public List<String> getProductsCategories() {
        return productRepository.findAllDistinctCategories();
    }

    public List<ProductDto> getProductsByCategory(String category, int pageNumber, int size) {
        List<Product> products =  productRepository.findByCategory(category, PageRequest.of(pageNumber,size));
        if(products.isEmpty()) throw new NoSuchElementException();
        return products.stream().map(product -> ProductMapper.MapToDto(product) ).toList();
    }

    public List<Product> filterCategories(List<Product> products,List<String> Categories){
        if(Categories.isEmpty()){
            return products;
        }else {
            products = products.stream().filter(product -> Categories.contains(product.getCategory())).toList();
            return products;
        }
    }
    public List<Product> filterSubCategories(List<Product> products,List<String> subCategories){
        if(subCategories.isEmpty()){
            return products;
        }else {
            products = products.stream().filter(product -> subCategories.contains(product.getSubCategory())).toList();
            return products;
        }
    }
    public List<Product> filterBrand(List<Product> products,List<String> brands){
        if(brands.isEmpty()){
            return products;
        }else {
            products = products.stream().filter(product -> brands.contains(product.getBrand())).toList();
            return products;
        }
    }
    public List<Product> filterPrices(List<Product> products,List<String> prices){
        if(prices.isEmpty()){
            return products;
        }else {
            products = products.stream().filter(product ->
                    {
                        double price =product.getPrice();
                        return (price<= Double.parseDouble(prices.get(1)) ||price>=Double.parseDouble(prices.get(0)));
                    }
                    ).toList();
            return products;
        }
    }

    public List<ProductDto> getProductsByFilter(Map<String,List<String>>filter,String productName, int pageNumber, int size) {
        List<Product> products = productRepository.findByNameContaining(productName,PageRequest.of(pageNumber,size));
        System.out.println(productName+" "+products.size());
        products = filterCategories(products,filter.get("categories"));
        products = filterSubCategories(products,filter.get("subCategories"));
        products = filterBrand(products,filter.get("brands"));
        products = filterPrices(products,filter.get("prices"));
        List<ProductDto> ProductsResponse =products.stream().map(product -> ProductMapper.MapToDto(product) ).toList();
        return  ProductsResponse;
    }

    public ProductDto getProductInfo(Long productID) {
        Product product = productRepository.findById(productID).orElseThrow(()->new NoSuchElementException("element with id "+productID+" Doesn't exist"));
        return ProductMapper.MapToDto(product);
    }


    @Transactional
    public String addProductToCart(Long productID, int quantity, Authentication authentication) {
        Customer customer = customerService.returnCustomer(authentication);
        Cart cart = customer.getCart();
        Product product = productRepository.findById(productID)
                .orElseThrow(()->new NoSuchElementException("element with id "+productID+" Doesn't exist"));
        CartProductsKey key = new CartProductsKey(cart.getId(), productID);
        CartProducts cartProducts = CartProducts.builder()
                .Id(key)
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .price(product.getPrice()*quantity)
                .build();
        cart.setTotalAmount(cart.getTotalAmount()+cartProducts.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice()+cartProducts.getPrice());
        cartRepository.save(cart);
        cartProductsRepository.save(cartProducts);
        return  "Product added";
    }
}
