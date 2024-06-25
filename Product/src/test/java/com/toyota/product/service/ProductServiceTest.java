package com.toyota.product.service;

import com.toyota.product.dto.ProductDto;
import com.toyota.product.entity.Product;
import com.toyota.product.exception.ProductNotFoundException;
import com.toyota.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }


    @Test
    void shouldReturnProductDtoWithGivenId_WhenProductExists() {
        // given
        Long id = 1L;
        Product product = new Product(id, 123L, "product", 100.0f, false, 0.18f);
        ProductDto expectedProductDto = new ProductDto(product);
        // when
        Mockito.when(productRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(product));
        ProductDto result = productService.getProductById(id);
        assertEquals(expectedProductDto, result);
        // then
        Mockito.verify(productRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void shouldThrowProductNotFoundException_WhenProductDoesNotExist() {
        // given
        Long id = 1L;
        // when
        Mockito.when(productRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(id));
        // then
        Mockito.verify(productRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void shouldReturnProductDtoSet_WhenProductsExist() {
        // given
        Set<Product> productList = new HashSet<>();
        productList.add(new Product(1L, 100001L, "product1", 100.0f, false, 0.18f));
        productList.add(new Product(2L, 100002L, "product2", 200.0f, false, 0.18f));

        Set<ProductDto> expectedProductDtos = new HashSet<>();
        for (Product product : productList) {
            expectedProductDtos.add(new ProductDto(product));
        }

        // when
        Mockito.when(productRepository.findAllByIsDeletedFalse()).thenReturn(productList);
        Set<ProductDto> result = productService.getAllProducts();

        // then
        assertEquals(expectedProductDtos, result);
        Mockito.verify(productRepository).findAllByIsDeletedFalse();
    }


    @AfterEach
    void tearDown() {
    }

}