package com.example.Ecommerce.Controller;

import com.example.Ecommerce.DTOS.ProductDto;
import com.example.Ecommerce.DTOS.ProductRequestDto;
import com.example.Ecommerce.DTOS.SupplierRequestDto;
import com.example.Ecommerce.DTOS.SupplierResponseDto;
import com.example.Ecommerce.Service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/Suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private  final SupplierService supplierService;

    @GetMapping
    private SupplierResponseDto getSupplierInfo(Authentication authentication){
        return supplierService.getSupplierInfo(authentication);
    }

    @PutMapping
    private  String  editSupplierInfo(Authentication authentication, @RequestBody SupplierRequestDto supplier){
        return supplierService.editSupplierInfo(authentication,supplier);
    }

    @PostMapping(path ="/Products/newProduct")
    private ProductDto addNewProduct(Authentication authentication, @RequestBody ProductRequestDto productRequestDto){
        return  supplierService.addNewProduct(authentication,productRequestDto);
    }

    @PutMapping(path = "/Products/{productID}")
    private String editProductInfo(Authentication authentication ,@PathVariable Long productID,@RequestBody ProductRequestDto productRequestDto ){
        return supplierService.editProductInfo(authentication,productID,productRequestDto);
    }

}
