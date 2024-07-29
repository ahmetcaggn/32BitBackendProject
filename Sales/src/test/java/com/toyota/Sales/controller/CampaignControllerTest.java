package com.toyota.Sales.controller;

import com.toyota.Sales.dto.CampaignDto;
import com.toyota.Sales.dto.CampaignRequest;
import com.toyota.Sales.service.CampaignService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CampaignControllerTest {

    private CampaignService campaignService;
    private CampaignController campaignController;

    @BeforeEach
    void setUp() {
        campaignService = Mockito.mock(CampaignService.class);
        campaignController = new CampaignController(campaignService);
    }

    @AfterEach
    void tearDown() {
    }

    //shouldReturnTrue_WhenEmployeeDeletedWithGivenIdThatIsValid

    @DisplayName("getAllCampaigns() method should return CampaignDto list when campaigns exist")
    @Test
    void shouldReturnCampaignDtoList_WhenGetAllCampaignsMethodCalled() {
        // given
        List<CampaignDto> expected = List.of(new CampaignDto(1L, "campaign", 10f, null));
        // when
        Mockito.when(campaignService.getAllCampaigns()).thenReturn(expected);
        List<CampaignDto> result = campaignController.getAllCampaigns();
        // then
        assertArrayEquals(expected.toArray(), result.toArray());
        Mockito.verify(campaignService).getAllCampaigns();
    }

    @DisplayName("getCampaignById() method should return CampaignDto when campaign exist")
    @Test
    void shouldReturnCampaignDto_WhenCampaignExistWithGivenId() {
        // given
        Long id = 1L;
        CampaignDto expected = new CampaignDto(id, "campaign", 10f, null);
        // when
        Mockito.when(campaignService.getCampaignById(id)).thenReturn(expected);
        CampaignDto result = campaignController.getCampaignById(id);
        // then
        assertEquals(expected, result);
        Mockito.verify(campaignService).getCampaignById(id);
    }

    @DisplayName("createCampaign() method should return CampaignDto when campaign created")
    @Test
    void shouldReturnCampaignDto_WhenCampaignCreatedWithGivenCampaignRequestDto() {
        // given
        CampaignRequest campaignRequest = new CampaignRequest("campaign", 10f, null);
        CampaignDto expected = new CampaignDto(1L, "campaign", 10f, null);
        // when
        Mockito.when(campaignService.createCampaign(campaignRequest)).thenReturn(expected);
        CampaignDto result = campaignController.createCampaign(campaignRequest);
        // then
        assertEquals(expected, result);
        Mockito.verify(campaignService).createCampaign(campaignRequest);
    }

}