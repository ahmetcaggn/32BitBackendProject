package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProductService {

    public ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new ProductNotFoundException("There is no product with id: " + id)
        );
        return new ProductDto(product);
    }

    public Set<ProductDto> getAllProducts() {
        Set<Product> productList = productRepository.findAllByIsDeletedFalse();
        Set<ProductDto> productDtoSet = new HashSet<>();
        for (Product product : productList) {
            productDtoSet.add(new ProductDto(product));
        }
        return productDtoSet;
    }
}
