package com.toyota.Sales.util;

import com.toyota.Sales.dto.CampaignDto;
import com.toyota.Sales.dto.ProductDto;
import com.toyota.entity.*;

public final class DtoEntityMapper {
    public static Product mapDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setTax(productDto.getTax());
        return product;
    }
    public static CampaignDto mapCampaignToDto(Campaign campaign) {
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setId(campaign.getId());
        campaignDto.setName(campaign.getName());
        campaignDto.setDiscountRate(campaign.getDiscountRate());
        for (Product product : campaign.getIncludedProducts()){
            campaignDto.getIncludedProducts().add(new ProductDto(product));
        }
        return campaignDto;
    }
}
