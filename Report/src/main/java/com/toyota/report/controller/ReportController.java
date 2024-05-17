package com.toyota.report.controller;

import com.toyota.report.dto.SaleDto;
import com.toyota.report.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/createPdf/{id}")
    public ResponseEntity<String> createPdf(@PathVariable("id") Long id) throws IOException {
        reportService.createEmptyPdfBySaleId(id);
        return ResponseEntity.ok("pdf file is created successfully");
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<SaleDto> getSale(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reportService.getSaleById(id));
    }

    @GetMapping("/sales")
    public ResponseEntity<Page<SaleDto>> salesReport(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer rows,
                                                     @RequestParam(defaultValue = "id") String sort,
                                                     @RequestParam(defaultValue = "") Long filterProductId,
                                                     @RequestParam(defaultValue = "") Long filterCampaignId,
                                                     @RequestParam(defaultValue = "0") Float minPrice,
                                                     @RequestParam(defaultValue = "") Float maxPrice,
                                                     @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(reportService.getAllSalesByPage(page, rows, sort, filterProductId, filterCampaignId, minPrice, maxPrice, sortDirection));
    }
 }
