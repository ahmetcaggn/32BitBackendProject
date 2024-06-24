package com.toyota.product.service;

import com.toyota.product.dto.ProductDto;
import com.toyota.product.entity.Product;
import com.toyota.product.exception.ProductNotFoundException;
import com.toyota.product.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
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
        log.info("Product with id {} fetched", id);
        return new ProductDto(product);
    }

    public Set<ProductDto> getAllProducts() {
        Set<Product> productList = productRepository.findAllByIsDeletedFalse();
        Set<ProductDto> productDtoSet = new HashSet<>();
        for (Product product : productList) {
            productDtoSet.add(new ProductDto(product));
        }
        log.info("{} products fetched", productDtoSet.size());
        return productDtoSet;
    }
}
