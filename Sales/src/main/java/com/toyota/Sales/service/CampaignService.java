package com.toyota.Sales.service;

import com.toyota.Sales.dto.CampaignDto;
import com.toyota.Sales.dto.CampaignRequest;
import com.toyota.Sales.dto.ProductDto;
import com.toyota.Sales.entity.Campaign;
import com.toyota.Sales.entity.Product;
import com.toyota.Sales.exception.CampaignNotFoundException;
import com.toyota.Sales.exception.ProductNotFoundException;
import com.toyota.Sales.interfaces.ProductInterface;
import com.toyota.Sales.repository.CampaignRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Log4j2
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final ProductInterface productInterface;

    public CampaignService(CampaignRepository campaignRepository, ProductInterface productInterface) {
        this.campaignRepository = campaignRepository;
        this.productInterface = productInterface;
    }

    public List<CampaignDto> getAllCampaigns() {
        List<CampaignDto> campaignDtoList = new ArrayList<>();
        for (Campaign campaign : campaignRepository.findAll()) {
            campaignDtoList.add(new CampaignDto(campaign));
        }
        log.info("{} campaigns fetched", campaignDtoList.size());
        return campaignDtoList;
    }

    public CampaignDto getCampaignById(Long id) {
        CampaignDto campaignDto = new CampaignDto(campaignRepository.findById(id).orElseThrow(
                () -> new CampaignNotFoundException("There is no campaign with id: " + id)
        ));
        log.info("Campaign with id {} fetched", id);
        return campaignDto;
    }

    public CampaignDto createCampaign(CampaignRequest campaignRequest) {
        Campaign campaign = new Campaign();
        campaign.setName(campaignRequest.getName());
        campaign.setDiscountRate(campaignRequest.getDiscountRate());
        Set<Product> products = new HashSet<>();

        for (Long id : campaignRequest.getProductList()) {
            ProductDto productDto = productInterface.getProductById(id);

            if (productDto != null) {
                products.add(new Product(productDto));
            } else {
                throw new ProductNotFoundException("There is no product with id: " + id);
            }
        }
        campaign.setIncludedProducts(products);
        log.info("Campaign with id {} created", campaign.getId());
        return new CampaignDto(campaignRepository.save(campaign));
    }
}
