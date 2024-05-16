package com.toyota.Sales.controller;

import com.toyota.Sales.dto.CampaignDto;
import com.toyota.Sales.dto.SaleDto;
import com.toyota.Sales.service.CampaignService;
import com.toyota.Sales.service.SalesService;
import com.toyota.Sales.dto.SaleProductDto;
import org.springframework.data.domain.Page;
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

    @GetMapping("/{id}")
    public ResponseEntity<SaleDto> getSaleById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(salesService.getSaleById(id));
    }

    @GetMapping("/{saleId}/campaigns")
    public List<CampaignDto> getAllRelevantCampaignsBySaleId(@PathVariable("saleId") Long id) {
        return salesService.getAllRelevantCampaigns(id);
    }

    @GetMapping("")
    public ResponseEntity<Page<SaleDto>> getAllSaleByPagination(@RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer rows,
                                                                @RequestParam(defaultValue = "id") String sort,
                                                                @RequestParam(defaultValue = "") Long filterProductId,
                                                                @RequestParam(defaultValue = "") Long filterCampaignId,
                                                                @RequestParam(defaultValue = "0") Float minPrice,
                                                                @RequestParam(defaultValue = "") Float maxPrice,
                                                                @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(salesService.getAllSalesByPagination(page, rows, sort, sortDirection, filterProductId, filterCampaignId, minPrice, maxPrice));
    }

    //Post Requests

    @PostMapping("")
    public SaleDto createSale() {
        return salesService.createSale();
    }

    @PostMapping("/{saleId}/products/{productId}")
    public SaleProductDto addProductToSale(@PathVariable("saleId") Long saleId, @PathVariable("productId") Long productId, @RequestParam(defaultValue = "1") Float quantity) {
        return salesService.addSaleProduct(saleId, productId, quantity);
    }

    @PostMapping("/{saleId}/campaigns/{campaignId}")
    public SaleDto applyCampaignToSale(@PathVariable("saleId") Long saleId, @PathVariable("campaignId") Long campaignID) {
        return salesService.applyCampaignToSale(saleId, campaignID);
    }

    //Put Requests

    @PutMapping("/saleProduct/{saleProductId}")
    public SaleProductDto updateQuantityOfSaleProduct(@PathVariable("saleProductId") Long saleProductId, @RequestParam(defaultValue = "1") Float quantity) {
        return salesService.updateSaleProduct(saleProductId, quantity);
    }

    //Delete Requests

    @DeleteMapping("/saleProduct/{saleProductId}")
    public ResponseEntity<String> deleteSaleProduct(@PathVariable("saleProductId") Long saleProductId) {
        salesService.deleteSaleProduct(saleProductId);
        return ResponseEntity.ok("SaleProduct deleted successfully with id: " + saleProductId);
    }

}
