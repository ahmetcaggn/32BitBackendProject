package com.example.Sales.service;

import com.example.Sales.dto.CampaignDto;
import com.example.Sales.dto.CampaignRequest;
import com.example.Sales.entity.Campaign;
import com.example.Sales.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {
    CampaignRepository campaignRepository;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public List<CampaignDto> getAllCampaigns(){
        List<CampaignDto> campaignDtoList = new ArrayList<>();
        for (Campaign campaign : campaignRepository.findAll()){
            campaignDtoList.add(new CampaignDto(campaign));
        }
        return campaignDtoList;
    }
    public CampaignDto getCampaignById(Long id){
        return new CampaignDto(campaignRepository.findById(id).get());
    }
    public CampaignDto createCampaign(CampaignRequest campaignRequest){
        Campaign campaign = new Campaign();
        campaign.setName(campaignRequest.getName());
        campaign.setDiscountRate(campaignRequest.getDiscountRate());
        return new CampaignDto(campaignRepository.save(campaign));
    }
}
