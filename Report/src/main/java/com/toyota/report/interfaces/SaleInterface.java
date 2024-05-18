package com.toyota.report.interfaces;

import com.toyota.report.dto.CampaignDto;
import com.toyota.report.dto.SaleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "SALES", path = "/sales")
public interface SaleInterface {

    //Get Requests

    @GetMapping("/{id}")
    ResponseEntity<SaleDto> getSaleById(@PathVariable("id") Long id);

    @GetMapping("/{saleId}/campaigns")
    List<CampaignDto> getAllRelevantCampaignsBySaleId(@PathVariable("saleId") Long id);

    @GetMapping("")
    ResponseEntity<Page<SaleDto>> getAllSaleByPagination(@RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer rows,
                                                                @RequestParam(defaultValue = "id") String sort,
                                                                @RequestParam(defaultValue = "") Long filterProductId,
                                                                @RequestParam(defaultValue = "") Long filterCampaignId,
                                                                @RequestParam(defaultValue = "0") Float minPrice,
                                                                @RequestParam(defaultValue = "") Float maxPrice,
                                                                @RequestParam(defaultValue = "DESC") String sortDirection
    );
}
