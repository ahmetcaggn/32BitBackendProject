package com.example.Sales.dto;

import com.example.Sales.entity.SaleProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleProductDto {
    private Long id;
    private Float quantity;
    private Long productId;
    private Long saleId;

    public SaleProductDto(SaleProduct saleProduct){
        this.id = saleProduct.getId();
        this.quantity = saleProduct.getQuantity();
        this.productId = saleProduct.getProduct().getId();
        this.saleId = saleProduct.getSale().getId();
    }
}
