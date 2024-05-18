package com.toyota.report.service;

import com.toyota.report.dto.SaleDto;
import com.toyota.report.dto.SaleProductDto;
import com.toyota.report.interfaces.SaleInterface;
import com.toyota.report.util.ReceiptGenerator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
public class ReportService {

    private final SaleInterface saleInterface;
    private final ReceiptGenerator receiptGenerator;


    public ReportService(SaleInterface saleInterface, ReceiptGenerator receiptGenerator) {
        this.saleInterface = saleInterface;
        this.receiptGenerator = receiptGenerator;
    }

    public SaleDto getSaleById(Long id) {
        return saleInterface.getSaleById(id).getBody();
    }

    public Page<SaleDto> getAllSalesByPage(Integer page, Integer rows, String sort, Long filterProductId, Long filterCampaignId, Float minPrice, Float maxPrice, String sortDirection) {
        return saleInterface.getAllSaleByPagination(page, rows, sort, filterProductId, filterCampaignId, minPrice, maxPrice, sortDirection).getBody();
    }
    public void generateReceipt(Long saleId) throws IOException {
        receiptGenerator.generateReceipt(saleId);
    }
}
