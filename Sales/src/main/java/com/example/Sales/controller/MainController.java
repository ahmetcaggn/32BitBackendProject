package com.example.Sales.controller;

import com.example.Sales.dto.CampaignDto;
import com.example.Sales.dto.CampaignRequest;
import com.example.Sales.dto.SaleDto;
import com.example.Sales.entity.Campaign;
import com.example.Sales.entity.Sale;
import com.example.Sales.service.CampaignService;
import com.example.Sales.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    SalesService salesService;
    CampaignService campaignService;

    @Autowired
    public MainController(SalesService salesService, CampaignService campaignService) {
        this.salesService = salesService;
        this.campaignService = campaignService;
    }

    //Get Requests

    @GetMapping("/sale")
    public List<SaleDto> getAllSales(){
        return salesService.getAllSales();
    }

    @GetMapping("/sale/{id}")
    public SaleDto getSaleById(@PathVariable("id") Long id){
        return salesService.getSaleById(id);
    }

    @GetMapping("/campaign")
    public List<CampaignDto> getAllCampaigns(){
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/saleCampaign/{saleId}")
    public List<CampaignDto> getAllReleventCampaignsBySaleId(@PathVariable("saleId")Long id){
        return salesService.getAllReleventCampaigns(id);
    }

    @GetMapping("/campaign/{id}")
    public CampaignDto getCampaignById(@PathVariable("id")Long campaignId){
        return campaignService.getCampaignById(campaignId);
    }

    //Post Requests

    @PostMapping("/sale")
    public SaleDto createSale(){
        return salesService.createSale();
    }

    @PostMapping("/sale/{sale_id}/{product_id}/{quantity}")
    public SaleDto addProductToSaleById(@PathVariable("sale_id")Long saleId,@PathVariable("product_id")Long productId,@PathVariable("quantity") Integer quantiy){
        return salesService.addSaleProduct(saleId,productId,quantiy);
    }

    @PostMapping("/applyCampaign/{saleId}/{campaignId}")
    public SaleDto applyCampaignToSale(@PathVariable("saleId")Long saleId,@PathVariable("campaignId")Long campaignID){
        return salesService.applyCampaignToSale(saleId,campaignID);
    }

    @PostMapping("/campaign")
    public CampaignDto createCampaign(@RequestBody CampaignRequest campaignRequest){
        return campaignService.createCampaign(campaignRequest);
    }

}
