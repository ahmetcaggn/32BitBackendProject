package com.example.Sales.dto;

import com.example.Sales.entity.Campaign;
import com.example.Sales.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CampaignDto {
    private Long id;
    private String name;
    private float discountRate;
    private Set<Long> includedProducts = new HashSet<>();

    public CampaignDto(Campaign campaign){
        this.id = campaign.getId();
        this.name = campaign.getName();
        this.discountRate = campaign.getDiscountRate();
        for (Product product : campaign.getIncludedProducts()){
            this.includedProducts.add(product.getId());
        }
    }
}
