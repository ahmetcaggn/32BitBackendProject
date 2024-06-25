package com.toyota.Sales.dto;

import com.toyota.entity.SaleProduct;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SaleProductDto {
    private Long id;
    private Float quantity;
    private ProductDto product;
    private Long saleId;

    public SaleProductDto(SaleProduct saleProduct){
        this.id = saleProduct.getId();
        this.quantity = saleProduct.getQuantity();
        this.product = new ProductDto(saleProduct.getProduct());
        this.saleId = saleProduct.getSale().getId();
    }
}
