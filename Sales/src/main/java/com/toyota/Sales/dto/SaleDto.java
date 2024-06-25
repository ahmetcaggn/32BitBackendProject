package com.toyota.Sales.dto;

import com.toyota.entity.Sale;
import com.toyota.entity.SaleCampaign;
import com.toyota.entity.SaleProduct;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SaleDto {
    private Long id;
    private float totalAmount;
    private float totalTax;
    private float discountAmount;
    private float lastPrice;
    private LocalDateTime localDateTime;
    private Set<SaleProductDto> saleProducts = new HashSet<>();
    private Set<CampaignDto> saleCampaigns = new HashSet<>();

    public SaleDto(Sale sale) {
        this.id = sale.getId();
        this.totalAmount = sale.getTotalAmount();
        this.totalTax = sale.getTotalTax();
        this.localDateTime = sale.getDateTime();
        for (SaleProduct saleProduct : sale.getSalesProducts()){
            this.saleProducts.add(new SaleProductDto(saleProduct));
        }
        for (SaleCampaign saleCampaign : sale.getSaleCampaigns()){
            this.saleCampaigns.add(new CampaignDto(saleCampaign.getCampaign()));
            this.discountAmount+=saleCampaign.getDiscountAmount();
        }
        this.lastPrice = this.totalAmount - this.discountAmount;
    }

}
