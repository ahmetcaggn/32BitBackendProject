package com.example.BitBackendProject.service;

import com.example.BitBackendProject.dto.ProductDto;
import com.example.BitBackendProject.dto.ProductRequest;
import com.example.BitBackendProject.entity.Product;
import com.example.BitBackendProject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ProductDto getProductById(Long id) {
        try {
            Product fetchedProduct = productRepository.getProductById(id);

            ProductDto productDto = new ProductDto();

            productDto.setId(fetchedProduct.getId());
            productDto.setProductCode(fetchedProduct.getProductCode());
            productDto.setName(fetchedProduct.getName());
            productDto.setPrice(fetchedProduct.getPrice());
            productDto.setTax(fetchedProduct.getTax());
            return productDto;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.getAllProducts();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setProductCode(product.getProductCode());
            productDto.setTax(product.getTax());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

}
