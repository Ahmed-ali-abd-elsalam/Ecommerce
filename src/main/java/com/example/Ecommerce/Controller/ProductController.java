package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Models.Product;
import com.example.Ecommerce.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/Products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(path = "/Categories")
    public List<String> getProductsByCategory(){
        return productService.getProductsCategories();
    }

//    return pages of products
    @GetMapping(path = "/Categories/{Category}")
    public List<Product> getProductsByCategory(@PathVariable String category,@RequestParam int pageNumber,@RequestParam int size){
        return productService.getProductsByCategory(category,pageNumber,size);
    }

//    return pages of products
//    make a sperate object for the filter
    @GetMapping(path = "/filtered")
    public List<Product> getProductsByFilter(@RequestBody Map<String,List<String>> filters, @RequestParam String productName
            , @RequestParam int pageNumber, @RequestParam int size){
        return  productService.getProductsByFilter(filters,productName,pageNumber,size);
    }


    @GetMapping(path = "/{productID}")
    public Product getProductInfo(@PathVariable Long productID){
        return productService.getProductInfo(productID);
    }
//    add to cart
    @PostMapping(path = "/{productID}/add")
    public String addToCart(@PathVariable Long productID, @RequestParam int quantity, Authentication authentication){
        return productService.addProductToCart(productID,quantity,authentication);
    }
}
