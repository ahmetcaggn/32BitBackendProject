package com.toyota.report.service;

import com.toyota.report.dto.SaleDto;
import com.toyota.report.exception.SaleNotCompletedException;
import com.toyota.report.interfaces.SaleInterface;
import com.toyota.report.util.ReceiptGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class ReportService {

    private final SaleInterface saleInterface;
    private final ReceiptGenerator receiptGenerator;


    public ReportService(SaleInterface saleInterface, ReceiptGenerator receiptGenerator) {
        this.saleInterface = saleInterface;
        this.receiptGenerator = receiptGenerator;
    }

    public SaleDto getSaleById(Long id) {
        SaleDto saleDto = saleInterface.getSaleById(id).getBody();
        log.info("Sale with id {} fetched", id);
        return saleDto;
    }

    public Page<SaleDto> getAllSalesByPage(Integer page, Integer rows, String sort, Long filterProductId, Long filterCampaignId, Float minPrice, Float maxPrice, String sortDirection) {
        Page<SaleDto> saleDtoPage = saleInterface.getAllSaleByPagination(page, rows, sort, filterProductId, filterCampaignId, minPrice, maxPrice, sortDirection).getBody();
        log.info("{} sales fetched", saleDtoPage != null ? saleDtoPage.getTotalElements() : 0);
        return saleDtoPage;
    }

    public void generateReceipt(Long saleId) {
        SaleDto saleDto = saleInterface.getSaleById(saleId).getBody();
        if (saleDto!=null && !saleDto.getIsCompleted()) {
            throw new SaleNotCompletedException("Sale with id " + saleId + " is not completed. Please complete the sale first.");
        }
        receiptGenerator.generateReceipt(saleDto);
        log.info("Receipt generated for sale with id {}", saleId);
    }
}
