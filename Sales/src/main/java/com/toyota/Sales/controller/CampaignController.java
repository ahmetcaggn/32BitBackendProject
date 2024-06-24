package com.toyota.Sales.controller;

import com.toyota.Sales.dto.CampaignDto;
import com.toyota.Sales.dto.CampaignRequest;
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

    @GetMapping({"", "/"})
    public List<CampaignDto> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/{id}")
    public CampaignDto getCampaignById(@PathVariable("id") Long campaignId) {
        return campaignService.getCampaignById(campaignId);
    }

    @PostMapping({"", "/"})
    public CampaignDto createCampaign(@RequestBody CampaignRequest campaignRequest) {
        return campaignService.createCampaign(campaignRequest);
    }

}
