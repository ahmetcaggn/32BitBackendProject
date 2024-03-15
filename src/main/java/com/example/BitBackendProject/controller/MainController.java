package com.example.BitBackendProject.controller;

import com.example.BitBackendProject.dto.ProductDto;
import com.example.BitBackendProject.dto.ProductRequest;
import com.example.BitBackendProject.entity.Product;
import com.example.BitBackendProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private ProductService productService;

    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String asd() {
        return "initial project";
    }

    @PostMapping("/product")
    public ProductDto getProduct(@RequestBody ProductRequest productRequest) {
        try {
            return productService.getProduct(productRequest);
        } catch (Exception e) {
            throw e;
        }
    }
}
