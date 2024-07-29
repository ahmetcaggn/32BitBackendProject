package com.toyota.Sales.service;

import com.toyota.Sales.dto.CampaignDto;
import com.toyota.Sales.dto.CampaignRequest;
import com.toyota.Sales.dto.ProductDto;
import com.toyota.Sales.exception.CampaignNotFoundException;
import com.toyota.Sales.exception.ProductNotFoundException;
import com.toyota.Sales.interfaces.ProductInterface;
import com.toyota.Sales.repository.CampaignRepository;
import com.toyota.entity.Campaign;
import com.toyota.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.toyota.Sales.util.DtoEntityMapper.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CampaignServiceTest {

    private CampaignService campaignService;
    private CampaignRepository campaignRepository;
    private ProductInterface productInterface;


    @BeforeEach
    void setUp() {
        campaignRepository = Mockito.mock(CampaignRepository.class);
        productInterface = Mockito.mock(ProductInterface.class);
        campaignService = new CampaignService(campaignRepository, productInterface);
    }

    @Test
    void shouldReturnCampaignDtoWithGivenId_WhenCampaignExists() {
        // given
        Long id = 1L;
        Campaign campaign = new Campaign(id, "campaign", 0.10f, new HashSet<>(), new HashSet<>());
        CampaignDto expectedCampaignDto = new CampaignDto(campaign);
        // when
        Mockito.when(campaignRepository.findById(id)).thenReturn(Optional.of(campaign));
        CampaignDto result = campaignService.getCampaignById(id);
        // then
        assertEquals(expectedCampaignDto, result);
        Mockito.verify(campaignRepository).findById(id);
    }

    @Test
    void shouldThrowCampaignNotFoundException_WhenCampaignDoesNotExist() {
        // given
        Long id = 1L;
        // when
        Mockito.when(campaignRepository.findById(id)).thenReturn(Optional.empty());
        // then
        assertThrows(CampaignNotFoundException.class, () -> campaignService.getCampaignById(id));
        Mockito.verify(campaignRepository).findById(id);
    }

    @Test
    void shouldReturnCampaignDtoSet_WhenCampaignsExist() {
        // given
        List<Campaign> campaignList = new ArrayList<>();
        campaignList.add(new Campaign(1L, "campaign1", 0.10f, new HashSet<>(), new HashSet<>()));
        campaignList.add(new Campaign(2L, "campaign2", 0.10f, new HashSet<>(), new HashSet<>()));

        List<CampaignDto> expectedCampaignDtoList = new ArrayList<>();
        for (Campaign campaign : campaignList) {
            expectedCampaignDtoList.add(new CampaignDto(campaign));
        }

        // when
        Mockito.when(campaignRepository.findAll()).thenReturn(campaignList);
        List<CampaignDto> result = campaignService.getAllCampaigns();
        // then
        assertEquals(expectedCampaignDtoList,result);
        Mockito.verify(campaignRepository).findAll();
    }


    @Test
    void shouldCreateCampaign_WhenValidRequestIsGiven() {
        // given
        CampaignRequest campaignRequest = new CampaignRequest("Test Campaign",0.15f,Set.of(1L,2L));

        ProductDto productDto1 = new ProductDto(1L, 100001L,"Product 1", 100.0f,0.18f);
        ProductDto productDto2 = new ProductDto(2L, 100001L,"Product 1", 100.0f,0.18f);

        Campaign savedCampaign = new Campaign();
        savedCampaign.setName(campaignRequest.getName());
        savedCampaign.setDiscountRate(campaignRequest.getDiscountRate());

        Product product1 = mapDtoToProduct(productDto1);
        Product product2 = mapDtoToProduct(productDto2);
        Set<Product> includedProducts = new HashSet<>();
        includedProducts.add(product1);
        includedProducts.add(product2);
        savedCampaign.setIncludedProducts(includedProducts);
        CampaignDto expectedCampaignDto = mapCampaignToDto(savedCampaign);


        // when
        Mockito.when(campaignRepository.save(Mockito.any(Campaign.class))).thenReturn(savedCampaign);
        Mockito.when(productInterface.getProductById(1L)).thenReturn(productDto1);
        Mockito.when(productInterface.getProductById(2L)).thenReturn(productDto2);
        CampaignDto result = campaignService.createCampaign(campaignRequest);

        // then
        assertEquals(expectedCampaignDto, result);
        Mockito.verify(campaignRepository).save(Mockito.any(Campaign.class));
        Mockito.verify(productInterface).getProductById(1L);
        Mockito.verify(productInterface).getProductById(2L);
    }

    @Test
    void shouldThrowProductNotFoundException_WhenProductInCampaignRequestDoesNotExist() {
        // given
        CampaignRequest campaignRequest = new CampaignRequest();
        campaignRequest.setName("Test Campaign");
        campaignRequest.setDiscountRate(0.15f);
        campaignRequest.setProductList(Set.of(1L, 2L));

        Mockito.when(productInterface.getProductById(1L)).thenReturn(null); // Simulate product not found

        // when / then
        assertThrows(ProductNotFoundException.class, () -> campaignService.createCampaign(campaignRequest));
    }

    @AfterEach
    void tearDown() {
    }


}