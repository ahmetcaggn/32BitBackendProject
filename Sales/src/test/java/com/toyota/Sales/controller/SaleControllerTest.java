package com.toyota.Sales.controller;

import com.toyota.Sales.dto.*;
import com.toyota.Sales.service.CampaignService;
import com.toyota.Sales.service.SalesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleControllerTest {

    private SalesService salesService;
    private CampaignService campaignService;
    private SaleController saleController;

    @BeforeEach
    void setUp() {
        salesService = Mockito.mock(SalesService.class);
        campaignService = Mockito.mock(CampaignService.class);
        saleController = new SaleController(salesService, campaignService);
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("getSaleById() method should return SaleDto when sale exist")
    @Test
    void shouldReturnSaleDto_WhenSaleExistWithGivenId() {
        // given
        Long id = 1L;
        SaleDto expected = new SaleDto(id, 100f, 10f, 10f, 80f, true, 50f, 30f, null, null, null);
        // when
        Mockito.when(salesService.getSaleById(id)).thenReturn(expected);
        SaleDto result = saleController.getSaleById(id).getBody();
        // then
        assertEquals(expected, result);
        Mockito.verify(salesService).getSaleById(id);

    }

    @DisplayName("getAllRelevantCampaignsBySaleId() method should return List<CampaignDto> when campaigns exist")
    @Test
    void shouldReturnListOfCampaignDto_WhenCampaignExistWithGivenSaleId() {
        // given
        Long saleId = 1L;
        List<CampaignDto> expected = List.of(new CampaignDto(1L, "campaign", 10f, null));
        // when
        Mockito.when(salesService.getAllRelevantCampaigns(saleId)).thenReturn(expected);
        List<CampaignDto> result = saleController.getAllRelevantCampaignsBySaleId(saleId);
        // then
        assertEquals(expected, result);
        Mockito.verify(salesService).getAllRelevantCampaigns(saleId);
    }

    @DisplayName("createSale() method should return SaleDto when sale created")
    @Test
    void shouldReturnSaleDto_WhenSaleCreated() {
        // given
        SaleDto expected = new SaleDto(1L, 100f, 10f, 10f, 80f, true, 50f, 30f, null, null, null);
        // when
        Mockito.when(salesService.createSale()).thenReturn(expected);
        SaleDto result = saleController.createSale();
        // then
        assertEquals(expected, result);
        Mockito.verify(salesService).createSale();
    }

    @DisplayName("addProductToSale() method should return SaleProductDto when product added to sale")
    @Test
    void shouldReturnSaleProductDto_WhenProductAddedToSaleWithGivenParameters() {
        // given
        Long saleId = 1L;
        Long productId = 1L;
        Float quantity = 1f;

        ProductDto productDto = new ProductDto(productId, 100000L, "product", 10f, 2f);
        SaleProductDto expected = new SaleProductDto(1L, quantity, productDto, saleId);

        // when
        Mockito.when(salesService.addSaleProduct(saleId, productId, quantity)).thenReturn(expected);
        SaleProductDto result = saleController.addProductToSale(saleId, productId, quantity);
        // then
        assertEquals(expected, result);
        Mockito.verify(salesService).addSaleProduct(saleId, productId, quantity);
    }

    @DisplayName("completeSale() method should return SaleDto when sale completed")
    @Test
    void shouldReturnSaleDto_WhenSaleCompletedWithGivenPaymentDto() {
        // given
        Long saleId = 1L;
        float cashAmount = 50f;
        float creditCardAmount = 30f;
        SaleDto expected = new SaleDto(saleId, 100f, 10f, 10f, 80f, true, 50f, 30f, null, null, null);
        PaymentDto paymentDto = new PaymentDto(cashAmount, creditCardAmount);
        // when
        Mockito.when(salesService.completeSale(saleId, paymentDto)).thenReturn(expected);
        SaleDto result = saleController.completeSale(saleId, paymentDto);
        // then
        assertEquals(expected, result);
        Mockito.verify(salesService).completeSale(saleId, paymentDto);
    }

    @DisplayName("applyCampaignToSale() method should return SaleDto when campaign applied to sale")
    @Test
    void shouldReturnSaleDto_WhenCampaignAppliedToSaleWithGivenParameters() {
        // given
        Long saleId = 1L;
        Long campaignId = 1L;
        SaleDto expected = new SaleDto(1L, 100f, 10f, 10f, 80f, true, 50f, 30f, null, null, null);
        // when
        Mockito.when(salesService.applyCampaignToSale(saleId, campaignId)).thenReturn(expected);
        SaleDto result = saleController.applyCampaignToSale(saleId, campaignId);
        // then
        assertEquals(expected, result);
        Mockito.verify(salesService).applyCampaignToSale(saleId, campaignId);
        
    }

    @DisplayName("updateQuantityOfSaleProduct() method should return SaleProductDto when quantity updated")
    @Test
    void shouldReturnSaleProductDto_WhenSaleProductUpdatedWithGivenParameters() {
        // given
        Long saleProductId = 1L;
        Float quantity = 1f;
        SaleProductDto expected = new SaleProductDto(1L, quantity, null, null);
        // when
        Mockito.when(salesService.updateSaleProduct(saleProductId, quantity)).thenReturn(expected);
        SaleProductDto result = saleController.updateQuantityOfSaleProduct(saleProductId, quantity);
        // then
        assertEquals(expected, result);
        Mockito.verify(salesService).updateSaleProduct(saleProductId, quantity);
    }

    @DisplayName("deleteSaleProduct() method should return SaleProductDto when saleProduct deleted")
    @Test
    void shouldReturnString_WhenSaleProductDeletedWithGivenId() {
        // given
        Long saleProductId = 1L;
        String expected = "SaleProduct deleted successfully with id: " + saleProductId;
        // when
        String result = saleController.deleteSaleProduct(saleProductId).getBody();

        // then
        assertEquals(expected, result);
        Mockito.verify(salesService).deleteSaleProduct(saleProductId);
    }

}