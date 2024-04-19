package com.example.product.controller;

import com.example.product.dto.ProductDto;
import com.example.product.service.ProductService;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MainController {
    private final ProductService productService;

    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ProductDto getProductById(@PathVariable(value = "id") Long id) throws NoResultException{
        return productService.getProductById(id);
    }

    @GetMapping("/product")
    public List<ProductDto> getAllProducts() throws NoResultException{
        return productService.getAllProducts();
    }

}
