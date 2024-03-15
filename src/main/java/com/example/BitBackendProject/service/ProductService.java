package com.example.BitBackendProject.service;

import com.example.BitBackendProject.dto.ProductDto;
import com.example.BitBackendProject.dto.ProductRequest;
import com.example.BitBackendProject.entity.Product;
import com.example.BitBackendProject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ProductDto getProduct(ProductRequest productRequest) {
        Product fetchedProduct = productRepository.getProductById(productRequest.getId());

        ProductDto productDto = new ProductDto();

        productDto.setId(fetchedProduct.getId());
        productDto.setProductCode(fetchedProduct.getProductCode());
        productDto.setName(fetchedProduct.getName());
        productDto.setPrice(fetchedProduct.getPrice());
        return productDto;
    }
}
