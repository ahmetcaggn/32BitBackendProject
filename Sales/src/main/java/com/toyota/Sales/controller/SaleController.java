package com.toyota.Sales.controller;

import com.toyota.Sales.dto.*;
import com.toyota.Sales.dto.CampaignDto;
import com.toyota.Sales.dto.SaleDto;
import com.toyota.Sales.service.CampaignService;
import com.toyota.Sales.service.SalesService;
import com.toyota.Sales.dto.SaleProductDto;
import com.toyota.Sales.dto.SaleProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    SalesService salesService;
    CampaignService campaignService;

    public SaleController(SalesService salesService, CampaignService campaignService) {
        this.salesService = salesService;
        this.campaignService = campaignService;
    }

    //Get Requests

    @GetMapping("")
    public List<SaleDto> getAllSales() {
        return salesService.getAllSales();
    }

    @GetMapping("/{id}")
    public SaleDto getSaleById(@PathVariable("id") Long id) {
        return salesService.getSaleById(id);
    }

    @GetMapping("/{saleId}/campaigns")
    public List<CampaignDto> getAllRelevantCampaignsBySaleId(@PathVariable("saleId") Long id) {
        return salesService.getAllRelevantCampaigns(id);
    }

    //Post Requests

    @PostMapping("")
    public SaleDto createSale() {
        return salesService.createSale();
    }

    @PostMapping("/{saleId}/products/{productId}")
    public SaleProductDto addProductToSale(@PathVariable("saleId") Long saleId, @PathVariable("productId") Long productId, @RequestBody SaleProductRequest saleProductRequest) {
        return salesService.addSaleProduct(saleId, productId, saleProductRequest);
    }

    @PostMapping("/{saleId}/campaigns/{campaignId}")
    public SaleDto applyCampaignToSale(@PathVariable("saleId") Long saleId, @PathVariable("campaignId") Long campaignID) {
        return salesService.applyCampaignToSale(saleId, campaignID);
    }

    //Put Requests

    @PutMapping("/saleProduct/{saleProductId}")
    public SaleProductDto updateQuantityOfSaleProduct(@PathVariable("saleProductId") Long saleProductId, @RequestBody SaleProductRequest saleProductRequest) {
        return salesService.updateSaleProduct(saleProductId, saleProductRequest);
    }

    //Delete Requests

    @DeleteMapping("/saleProduct/{saleProductId}")
    public ResponseEntity<String> deleteSaleProduct(@PathVariable("saleProductId") Long saleProductId) {
        salesService.deleteSaleProduct(saleProductId);
        return ResponseEntity.ok("SaleProduct deleted successfully with id: "+ saleProductId);
    }

}
