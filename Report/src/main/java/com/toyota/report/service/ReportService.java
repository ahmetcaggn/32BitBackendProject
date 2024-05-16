package com.toyota.report.service;

import com.toyota.report.dto.SaleDto;
import com.toyota.report.interfaces.SaleInterface;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class ReportService {

    private final SaleInterface saleInterface;

    public ReportService(SaleInterface saleInterface) {
        this.saleInterface = saleInterface;
    }

    public SaleDto getSaleById(Long id) {
        return saleInterface.getSaleById(id).getBody();
    }

    public Page<SaleDto> getAllSalesByPage(Integer page,Integer rows,String sort,Long filterProductId,Long filterCampaignId,Float minPrice,Float maxPrice,String sortDirection) {
        return saleInterface.getAllSaleByPagination(page, rows, sort, filterProductId, filterCampaignId, minPrice, maxPrice, sortDirection).getBody();
    }

}
