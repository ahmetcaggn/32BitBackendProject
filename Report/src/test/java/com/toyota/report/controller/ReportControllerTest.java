package com.toyota.report.controller;

import com.toyota.report.dto.SaleDto;
import com.toyota.report.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ReportControllerTest {

    private ReportController reportController;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = Mockito.mock(ReportService.class);
        reportController = new ReportController(reportService);
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("createPdf() method ")
    @Test
    void createPdf() {
        //Given
        Long id = 1L;
        String expected = "pdf file is created successfully";
        //When
        Mockito.doNothing().when(reportService).generateReceipt(id);
        String actual = reportController.createPdf(id).getBody();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void getSale() {
        //Given
        Long id = 1L;
        SaleDto expected = new SaleDto(id, 100f, 10f, 10f, 80f, true, 50f, 30f, null, null, null);
        //When
        Mockito.when(reportService.getSaleById(id)).thenReturn(expected);
        SaleDto result = reportController.getSale(id).getBody();
        //Then
        assertEquals(expected, result);
        Mockito.verify(reportService).getSaleById(id);
    }
}