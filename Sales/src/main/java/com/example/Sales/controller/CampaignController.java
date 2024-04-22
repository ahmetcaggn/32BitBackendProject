package com.example.Sales.controller;

import com.example.Sales.dto.CampaignDto;
import com.example.Sales.dto.CampaignRequest;
import com.example.Sales.entity.Campaign;
import com.example.Sales.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping({"","/"})
    public List<CampaignDto> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/{id}")
    public CampaignDto getCampaignById(@PathVariable("id") Long campaignId) {
        return campaignService.getCampaignById(campaignId);
    }

    @PostMapping({"","/"})
    public Campaign createCampaign(@RequestBody CampaignRequest campaignRequest) {
        return campaignService.createCampaign(campaignRequest);
    }

}
