package com.example.Sales.service;

import com.example.Sales.dto.CampaignDto;
import com.example.Sales.dto.CampaignRequest;
import com.example.Sales.dto.ProductDto;
import com.example.Sales.entity.Campaign;
import com.example.Sales.entity.Product;
import com.example.Sales.exception.CampaignNotFoundException;
import com.example.Sales.exception.ProductNotFoundException;
import com.example.Sales.interfaces.ProductInterface;
import com.example.Sales.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
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
        return campaignDtoList;
    }

    public CampaignDto getCampaignById(Long id) {
        return new CampaignDto(campaignRepository.findById(id).orElseThrow(
                () -> new CampaignNotFoundException("There is no campaign with id: " + id)
        ));
    }

    public Campaign createCampaign(CampaignRequest campaignRequest) {
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
        return campaignRepository.save(campaign);
    }
}
