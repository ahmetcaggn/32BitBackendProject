package com.toyota.Sales.dto;

import com.toyota.Sales.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long id;
    private Long productCode;
    private String name;
    private Float price;
    private Float tax;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.productCode = product.getProductCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.tax = product.getTax();
    }
}
