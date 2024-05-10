package com.toyota.product.controller;

import com.toyota.product.dto.ProductDto;
import com.toyota.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
public class MainController {
    private final ProductService productService;

    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ProductDto getProductById(@PathVariable(value = "id") Long id){
        return productService.getProductById(id);
    }

    @GetMapping("/product")
    public Set<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }
}
