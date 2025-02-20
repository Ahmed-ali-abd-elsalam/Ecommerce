package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Models.Product;
import com.example.Ecommerce.Models.Supplier;
import com.example.Ecommerce.Repository.SupplierRepository;
import com.example.Ecommerce.Service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/Suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private  final SupplierService supplierService;

    @GetMapping
    private Supplier getSupplierInfo(Authentication authentication){
        return supplierService.getSupplierInfo(authentication);
    }

    @PutMapping
    private  String  editSupplierInfo(Authentication authentication, @RequestBody Supplier supplier){
        return supplierService.editSupplierInfo(authentication,supplier);
    }

    @PostMapping(path ="/Products/newProduct")
    private Product addNewProduct(Authentication authentication, @RequestBody Product product){
        return  supplierService.addNewProduct(authentication,product);
    }

    @PutMapping(path = "/Products/{productID}")
    private String editProductInfo(Authentication authentication ,@PathVariable Long productID,@RequestBody Product product ){
        return supplierService.editProductInfo(authentication,productID,product);
    }

}
