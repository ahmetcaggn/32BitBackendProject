package com.toyota.report.service;

import com.toyota.report.dto.SaleDto;
import com.toyota.report.interfaces.SaleInterface;
import com.toyota.report.util.ReceiptGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    private SaleInterface saleInterface;
    private ReceiptGenerator receiptGenerator;
    private ReportService reportService;


    @BeforeEach
    void setUp() {
        saleInterface = Mockito.mock(SaleInterface.class);
        receiptGenerator = Mockito.mock(ReceiptGenerator.class);
        reportService = new ReportService(saleInterface, receiptGenerator);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSaleById() {
        //Given
        Long id = 1L;
        SaleDto expected = new SaleDto(id, 100f, 10f, 10f, 80f, true, 50f, 30f, null, null, null);
        //When
        Mockito.when(saleInterface.getSaleById(id)).thenReturn(ResponseEntity.ok(expected));
        SaleDto result = reportService.getSaleById(id);
        //Then
        assertEquals(expected, result);
        Mockito.verify(saleInterface).getSaleById(id);
    }

    @Test
    void generateReceipt() {
        //Given
        Long saleId = 1L;
        SaleDto saleDto = new SaleDto(saleId, 100f, 10f, 10f, 80f, true, 50f, 30f, null, null, null);
        //When
        Mockito.when(saleInterface.getSaleById(saleId)).thenReturn(ResponseEntity.ok(saleDto));
        Mockito.doNothing().when(receiptGenerator).generateReceipt(saleDto);
        reportService.generateReceipt(saleId);
        //Then
        Mockito.verify(saleInterface).getSaleById(saleId);
        Mockito.verify(receiptGenerator).generateReceipt(saleDto);
    }
}