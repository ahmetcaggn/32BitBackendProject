package com.toyota.Sales.dto;

import com.toyota.entity.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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
