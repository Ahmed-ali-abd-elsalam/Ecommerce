package com.example.Ecommerce.Controller;

import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.Service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
    public List<String> getProductsCategories(){
        return productService.getProductsCategories();
    }

//    return pages of products
    @GetMapping(path = "/Categories/{Category}")
    public List<ProductDto> getProductsByCategory(@PathVariable String Category, @RequestParam int pageNumber, @RequestParam int size){
        return productService.getProductsByCategory(Category,pageNumber,size);
    }

//    return pages of products
    @PostMapping(path = "/filtered")
    public List<ProductDto> getProductsByFilter(@RequestBody Map<String,List<String>> filters, @RequestParam String productName
            , @RequestParam int pageNumber, @RequestParam int size){
        return  productService.getProductsByFilter(filters,productName,pageNumber,size);
    }


    @GetMapping(path = "/{productID}")
    public ProductDto getProductInfo(@PathVariable Long productID){
        return productService.getProductInfo(productID);
    }
//    add to cart
    @PostMapping(path = "/{productID}/add")
    public String addToCart(@PathVariable Long productID,@RequestParam @Valid @Positive  int quantity, Authentication authentication){
        return productService.addProductToCart(productID,quantity,authentication);
    }
}
