package com.toyota.Sales.controller;

import com.toyota.Sales.dto.CampaignDto;
import com.toyota.Sales.dto.CampaignRequest;
import com.toyota.Sales.exception.CampaignNotFoundException;
import com.toyota.Sales.service.CampaignService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
@Log4j2
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    //Get Requests

    @GetMapping({"", "/"})
    public List<CampaignDto> getAllCampaigns() {
        List<CampaignDto> campaigns;
        try {
            campaigns = campaignService.getAllCampaigns();
            log.info("{} campaigns fetched", campaigns.size());
        } catch (Exception e) {
            log.error("Error occurred while fetching campaigns", e);
            throw new CampaignNotFoundException("Error occurred while fetching campaigns");
        }
        return campaigns;
    }

    @GetMapping("/{id}")
    public CampaignDto getCampaignById(@PathVariable("id") Long campaignId) {
        CampaignDto campaign;
        try {
            campaign = campaignService.getCampaignById(campaignId);
            log.info("Campaign with id {} fetched", campaignId);
        } catch (Exception e) {
            log.error("Error occurred while fetching campaign with id {}", campaignId, e);
            throw new CampaignNotFoundException("Error occurred while fetching campaign with id " + campaignId);
        }
        return campaign;
    }

    //Post Requests

    @PostMapping({"", "/"})
    public CampaignDto createCampaign(@RequestBody CampaignRequest campaignRequest) {
        CampaignDto campaign;
        try {
            campaign = campaignService.createCampaign(campaignRequest);
            log.info("Campaign with id {} created", campaign.getId());
        }catch (Exception e){
            log.error("Error occurred while creating campaign", e);
            throw new RuntimeException("Error occurred while creating campaign");
        }
        return campaign;
    }

}
