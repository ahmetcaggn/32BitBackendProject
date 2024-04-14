package com.example.Sales.dto;

import com.example.Sales.entity.Sale;
import com.example.Sales.entity.SaleCampaign;
import com.example.Sales.entity.SaleProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleDto {
    private Long id;
    private float totalAmount;
    private float totalTax;
    private float discountAmount;
    private float lastPrice;
    private LocalDateTime localDateTime;
    private Set<Long> saleProducts = new HashSet<>();
    private Set<Long> saleCampaigns = new HashSet<>();

    public SaleDto(Sale sale) {
        this.id = sale.getId();
        this.totalAmount = sale.getTotalAmount();
        this.totalTax = sale.getTotalTax();
        this.localDateTime = sale.getDateTime();
        for (SaleProduct saleProduct : sale.getSalesProducts()){
            this.saleProducts.add(saleProduct.getId());
        }
        for (SaleCampaign saleCampaign : sale.getSaleCampaigns()){
            this.saleCampaigns.add(saleCampaign.getId());
            this.discountAmount+=saleCampaign.getDiscountAmount();
        }
        this.lastPrice = this.totalAmount - this.discountAmount;
    }

}
