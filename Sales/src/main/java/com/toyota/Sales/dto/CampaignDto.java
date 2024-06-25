package com.toyota.Sales.dto;

import com.toyota.entity.Campaign;
import com.toyota.entity.Product;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CampaignDto {
    private Long id;
    private String name;
    private float discountRate;
    private Set<ProductDto> includedProducts = new HashSet<>();

    public CampaignDto(Campaign campaign){
        this.id = campaign.getId();
        this.name = campaign.getName();
        this.discountRate = campaign.getDiscountRate();
        for (Product product : campaign.getIncludedProducts()){
            this.includedProducts.add(new ProductDto(product));
        }
    }
}
