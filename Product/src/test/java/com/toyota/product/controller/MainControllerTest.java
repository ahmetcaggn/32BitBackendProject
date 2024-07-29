package com.toyota.product.controller;

import com.toyota.product.dto.ProductDto;
import com.toyota.product.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    private MainController mainController;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = Mockito.mock(ProductService.class);
        mainController = new MainController(productService);
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("getProductById() method should return productDto when product exist with given id")
    @Test
    void shouldReturnProductDto_WhenProductExistWithGivenId() {
        // given
        Long id = 1L;
        ProductDto expected = new ProductDto(id, 100000L,"product", 10f, 10f);
        // when
        Mockito.when(productService.getProductById(id)).thenReturn(expected);
        ProductDto result = mainController.getProductById(id);
        // then
        assertEquals(expected, result);
        Mockito.verify(productService).getProductById(id);
    }

    @DisplayName("getAllProducts() method should return set of productDto")
    @Test
    void shouldReturnListOfProductDto_WhenProductsExist() {
        // given
        Long id = 1L;
        Set<ProductDto> expected = Set.of(new ProductDto(id, 100000L,"product", 10f, 10f));
        // when
        Mockito.when(productService.getAllProducts()).thenReturn(expected);
        Set<ProductDto> result = mainController.getAllProducts();
        // then
        assertEquals(expected, result);
        Mockito.verify(productService).getAllProducts();
    }

}