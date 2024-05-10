package com.toyota.Sales.controller;

import com.toyota.Sales.dto.CampaignDto;
import com.toyota.Sales.dto.CampaignRequest;
import com.toyota.Sales.entity.Campaign;
import com.toyota.Sales.service.CampaignService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    //Get Requests

    @GetMapping({"","/"})
    public List<CampaignDto> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/{id}")
    public CampaignDto getCampaignById(@PathVariable("id") Long campaignId) {
        return campaignService.getCampaignById(campaignId);
    }

    //Post Requests

    @PostMapping({"","/"})
    public Campaign createCampaign(@RequestBody CampaignRequest campaignRequest) {
        return campaignService.createCampaign(campaignRequest);
    }

}
