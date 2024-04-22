package com.example.Sales.service;

import com.example.Sales.dto.CampaignDto;
import com.example.Sales.dto.CampaignRequest;
import com.example.Sales.entity.Campaign;
import com.example.Sales.entity.Product;
import com.example.Sales.exception.CampaignNotFoundException;
import com.example.Sales.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CampaignService {
    CampaignRepository campaignRepository;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
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
        for (Long id : campaignRequest.getProductList()){
            products.add(new Product(id));
        }
        campaign.setIncludedProducts(products);
        return campaignRepository.save(campaign);
    }
}
