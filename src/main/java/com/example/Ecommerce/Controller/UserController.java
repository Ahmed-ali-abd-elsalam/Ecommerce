package com.example.Ecommerce.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/Users")
public class UserController {


    @GetMapping
    public String Temp(){
        return "just Checking";
    }
}
