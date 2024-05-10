package com.toyota.Sales.interfaces;

import com.toyota.Sales.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient("Product")
public interface ProductInterface {
    @GetMapping("/product/{id}")
    ProductDto getProductById(@PathVariable(value = "id") Long id);

    @GetMapping("/product")
    Set<ProductDto> getAllProducts();
}
