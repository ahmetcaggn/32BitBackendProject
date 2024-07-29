package com.toyota.Sales.service;

import com.toyota.Sales.dto.ProductDto;
import com.toyota.Sales.dto.SaleDto;
import com.toyota.Sales.dto.SaleProductDto;
import com.toyota.Sales.exception.CampaignNotFoundException;
import com.toyota.Sales.exception.SaleNotFoundException;
import com.toyota.Sales.exception.SaleProductNotFoundException;
import com.toyota.Sales.interfaces.ProductInterface;
import com.toyota.Sales.repository.CampaignRepository;
import com.toyota.Sales.repository.SaleCampaignRepository;
import com.toyota.Sales.repository.SaleProductRepository;
import com.toyota.Sales.repository.SaleRepository;
import com.toyota.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.toyota.Sales.util.DtoEntityMapper.*;

class SalesServiceTest {
    private ProductInterface productInterface;
    private SaleRepository saleRepository;
    private SaleProductRepository saleProductRepository;
    private CampaignRepository campaignRepository;
    private SaleCampaignRepository saleCampaignRepository;
    private SalesService salesService;


    @BeforeEach
    void setUp() {
        productInterface = Mockito.mock(ProductInterface.class);
        saleRepository = Mockito.mock(SaleRepository.class);
        saleProductRepository = Mockito.mock(SaleProductRepository.class);
        campaignRepository = Mockito.mock(CampaignRepository.class);
        saleCampaignRepository = Mockito.mock(SaleCampaignRepository.class);
        salesService = new SalesService(productInterface, saleRepository, saleProductRepository, campaignRepository, saleCampaignRepository);

    }

    @DisplayName("should return sale dto when createSale() method calls with no parameter")
    @Test
    void shouldReturnSaleDto_WhenSaleIsCreated() {
        // given
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setTotalAmount(100);
        sale.setTotalTax(15);
        sale.setDateTime(null);
        SaleDto expected = new SaleDto(sale);

        //when
        Mockito.when(saleRepository.save(Mockito.any(Sale.class))).thenReturn(sale);
        SaleDto result = salesService.createSale();

        //then
        assertEquals(expected, result);
        Mockito.verify(saleRepository).save(Mockito.any(Sale.class));
    }

    @DisplayName("should return SaleProductDto when addSaleProduct() method calls with parameters and quantity is not null and greater than zero")
    @Test
    void shouldReturnSaleProductDtoWithGivenParameters_WhenQuantityIsNotNullAndGreaterThanZero() {
        Long saleId = 1L;
        Long productId = 1L;
        Float quantity = 5f;
        //given
        Sale sale = new Sale(saleId, 100f, 10f,false,100f,0f,100f,LocalDateTime.now(), new HashSet<>(), new HashSet<>());
        ProductDto productDto = new ProductDto(1L, 100001L, "productDto", 100f, 0.18f);

        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setId(1L);
        saleProduct.setQuantity(quantity);
        saleProduct.setProduct(mapDtoToProduct(productDto));
        saleProduct.setSale(sale);
        SaleProductDto expected = new SaleProductDto(saleProduct);

        //when
        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));
        Mockito.when(productInterface.getProductById(productId)).thenReturn(productDto);
        Mockito.when(saleProductRepository.save(Mockito.any(SaleProduct.class))).thenReturn(saleProduct);
        SaleProductDto result = salesService.addSaleProduct(saleId, productId, quantity);

        //then
        assertEquals(expected, result);
        Mockito.verify(saleRepository).findById(saleId);
        Mockito.verify(productInterface).getProductById(productId);
        Mockito.verify(saleProductRepository).save(Mockito.any(SaleProduct.class));

    }

    @DisplayName("should return SaleProductDto when addSaleProduct() method calls with quantity is null")
    @Test
    void shouldReturnSaleProductDtoWithGivenParameters_WhenQuantityIsNull() {
        Long saleId = 1L;
        Long productId = 1L;
        Float quantity = null;
        //given
        Sale sale = new Sale(saleId, 100f, 10f,false,100f,0f,100f,LocalDateTime.now(), new HashSet<>(), new HashSet<>());
        ProductDto productDto = new ProductDto(1L, 100001L, "productDto", 100f, 0.18f);

        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setId(1L);
        saleProduct.setQuantity(quantity);
        saleProduct.setProduct(mapDtoToProduct(productDto));
        saleProduct.setSale(sale);
        SaleProductDto expected = new SaleProductDto(saleProduct);

        //when
        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));
        Mockito.when(productInterface.getProductById(productId)).thenReturn(productDto);
        Mockito.when(saleProductRepository.save(Mockito.any(SaleProduct.class))).thenReturn(saleProduct);
        SaleProductDto result = salesService.addSaleProduct(saleId, productId, quantity);

        //then
        assertEquals(expected, result);
        Mockito.verify(saleRepository).findById(saleId);
        Mockito.verify(productInterface).getProductById(productId);
        Mockito.verify(saleProductRepository).save(Mockito.any(SaleProduct.class));

    }

    @DisplayName("should return SaleProductDto when addSaleProduct() method calls with quantity equals or less than zero")
    @Test
    void shouldReturnSaleProductDtoWithGivenParameters_WhenQuantityIsEqualsOrLessThanZero() {
        Long saleId = 1L;
        Long productId = 1L;
        Float quantity = -5f;
        //given
        Sale sale = new Sale(saleId, 100f, 10f,false,100f,0f,100f,LocalDateTime.now(), new HashSet<>(), new HashSet<>());
        ProductDto productDto = new ProductDto(1L, 100001L, "productDto", 100f, 0.18f);

        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setId(1L);
        saleProduct.setQuantity(quantity);
        saleProduct.setProduct(mapDtoToProduct(productDto));
        saleProduct.setSale(sale);
        SaleProductDto expected = new SaleProductDto(saleProduct);

        //when
        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));
        Mockito.when(productInterface.getProductById(productId)).thenReturn(productDto);
        Mockito.when(saleProductRepository.save(Mockito.any(SaleProduct.class))).thenReturn(saleProduct);
        SaleProductDto result = salesService.addSaleProduct(saleId, productId, quantity);

        //then
        assertEquals(expected, result);
        Mockito.verify(saleRepository).findById(saleId);
        Mockito.verify(productInterface).getProductById(productId);
        Mockito.verify(saleProductRepository).save(Mockito.any(SaleProduct.class));

    }

    @DisplayName("Should throw SaleNotFoundException when sale does not found with given saleId")
    @Test
    void shouldThrowSaleNotFoundException_WhenSaleNotFoundWithGivenSaleId() {
        //given
        Long saleId = 1L;
        Long productId = 1L;
        Float quantity = 10f;

        //when
        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.empty());

        //then
        assertThrows(SaleNotFoundException.class, () -> salesService.addSaleProduct(saleId, productId, quantity));
        Mockito.verify(saleRepository).findById(saleId);

    }

    @DisplayName("should return sale dto with given saleId when getSaleById() method calls with existing saleId")
    @Test
    void shouldReturnSaleDtoWithSaleId_whenSaleExists() {
        //given
        Long saleId = 1L;
        Sale sale = new Sale(saleId, 100f, 10f,false,100f,0f,100f,LocalDateTime.now(), new HashSet<>(), new HashSet<>());
        SaleDto expected = new SaleDto(sale);
        //when
        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));
        SaleDto result = salesService.getSaleById(saleId);
        //then
        assertEquals(expected, result);
        Mockito.verify(saleRepository).findById(saleId);
    }

    @DisplayName("Should throw SaleNotFoundException when sale does not found with given saleId")
    @Test
    void shouldThrowSaleNotFoundException_WhenSaleDoesNotExistWithGivenId() {
        //given
        //when
        Mockito.when(saleRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertThrows(SaleNotFoundException.class, () -> salesService.getSaleById(1L));
        Mockito.verify(saleRepository).findById(1L);
    }

    @DisplayName("updateSaleProduct() method should return SaleProductDto when given saleProductId is valid and quantity is greater than zero")
    @Test
    public void shouldReturnSaleProductDto_WhenGivenSaleProductIdIsValidAndQuantityGreaterThanZero() {
        // given
        Long saleProductId = 1L;
        Float originalQuantity = 5f;

        Float updatedQuantityParameter = 10f;
        SaleProduct saleProduct = new SaleProduct(saleProductId, originalQuantity, new Product(), new Sale());
        SaleProduct updatedSaleProduct = new SaleProduct(saleProductId, updatedQuantityParameter, new Product(), new Sale());
        SaleProductDto expected = new SaleProductDto(updatedSaleProduct);
        //when
        Mockito.when(saleProductRepository.findById(saleProductId)).thenReturn(Optional.of(saleProduct));
        Mockito.when(saleProductRepository.save(Mockito.any(SaleProduct.class))).thenReturn(updatedSaleProduct);
        SaleProductDto result = salesService.updateSaleProduct(saleProductId, updatedQuantityParameter);
        //then
        assertEquals(expected, result);
        Mockito.verify(saleProductRepository).findById(saleProductId);
        Mockito.verify(saleProductRepository).save(Mockito.any(SaleProduct.class));
    }

    @DisplayName("updateSaleProduct() method should return SaleProductDto with default quantity when given saleProductId is valid and quantity is null")
    @Test
    public void shouldReturnSaleProductDto_WhenGivenSaleProductIdIsValidAndQuantityIsNull() {
        // given
        Long saleProductId = 1L;
        Float originalQuantity = 5f;
        Float defaultQuantity = 1F;
        Float updatedQuantityParameter = null;

        SaleProduct saleProduct = new SaleProduct(saleProductId, originalQuantity, new Product(), new Sale());
        SaleProduct updatedSaleProduct = new SaleProduct(saleProductId, defaultQuantity, new Product(), new Sale());
        SaleProductDto expected = new SaleProductDto(updatedSaleProduct);
        //when
        Mockito.when(saleProductRepository.findById(saleProductId)).thenReturn(Optional.of(saleProduct));
        Mockito.when(saleProductRepository.save(Mockito.any(SaleProduct.class))).thenReturn(updatedSaleProduct);
        SaleProductDto result = salesService.updateSaleProduct(saleProductId, updatedQuantityParameter);
        //then
        assertEquals(expected, result);
        Mockito.verify(saleProductRepository).findById(saleProductId);
        Mockito.verify(saleProductRepository).save(Mockito.any(SaleProduct.class));
    }

    @DisplayName("updateSaleProduct() method should return SaleProductDto with default quantity when given saleProductId is valid and quantity is less than zero")
    @Test
    public void shouldReturnSaleProductDto_WhenGivenSaleProductIdIsValidAndQuantityEqualsOrLessThanZero() {
        // given
        Long saleProductId = 1L;
        Float originalQuantity = 5f;
        Float defaultQuantity = 1F;
        Float updatedQuantityParameter = -5f;
        SaleProduct saleProduct = new SaleProduct(saleProductId, originalQuantity, new Product(), new Sale());
        SaleProduct updatedSaleProduct = new SaleProduct(saleProductId, defaultQuantity, new Product(), new Sale());
        SaleProductDto expected = new SaleProductDto(updatedSaleProduct);
        //when
        Mockito.when(saleProductRepository.findById(saleProductId)).thenReturn(Optional.of(saleProduct));
        Mockito.when(saleProductRepository.save(Mockito.any(SaleProduct.class))).thenReturn(updatedSaleProduct);
        SaleProductDto result = salesService.updateSaleProduct(saleProductId, updatedQuantityParameter);
        //then
        assertEquals(expected, result);
        Mockito.verify(saleProductRepository).findById(saleProductId);
        Mockito.verify(saleProductRepository).save(Mockito.any(SaleProduct.class));
    }

    @DisplayName("updateSaleProduct() method should throw SaleProductNotFoundException when given saleProductId is not valid")
    @Test
    public void shouldThrowSaleProductNotFoundException_WhenGivenSaleProductIdIsNotValid() {
        // given
        Long saleProductId = 1L;
        //when
        Mockito.when(saleProductRepository.findById(saleProductId)).thenReturn(Optional.empty());
        //then
        assertThrows(SaleProductNotFoundException.class, () -> salesService.updateSaleProduct(saleProductId, 5f));
        Mockito.verify(saleProductRepository).findById(saleProductId);
    }

    @DisplayName("calculateDiscountAmount() method should return discount amount when given ProductList and campaignId are valid and All products are in the campaign")
    @Test
    public void shouldReturnDiscountAmount_WhenGivenProductListAndCampaignIdAreValidAndAllProductsAreInTheCampaign() {
        // given
        Long campaignId = 1L;
        Float expectedDiscountAmount = 20f;

        Set<Product> products = new HashSet<>();
        products.add(new Product(1L, 100001L, "product1", 100f, false, 10f, new HashSet<>(), new HashSet<>()));
        products.add(new Product(2L, 100002L, "product2", 100f, false, 10f, new HashSet<>(), new HashSet<>()));

        Campaign campaign = new Campaign(1L, "campaign", 10f, products, new HashSet<>());
        //when
        Mockito.when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));
        Float result = salesService.calculateDiscountAmount(products.stream().toList(), campaignId);
        //then
        assertEquals(expectedDiscountAmount, result);
        Mockito.verify(campaignRepository).findById(campaignId);
    }

    @DisplayName("calculateDiscountAmount() method should return discount amount when given ProductList and campaignId are valid and All products are not in the campaign")
    @Test
    public void shouldReturnDiscountAmount_WhenGivenProductListAndCampaignIdAreValidAndAllProductsAreNotInTheCampaign() {
        // given
        Long campaignId = 1L;
        Float expectedDiscountAmount = 10f;
        Product product1 = new Product(1L, 100001L, "product1", 100f, false, 10f, new HashSet<>(), new HashSet<>());
        Product product2 = new Product(2L, 100002L, "product2", 100f, false, 10f, new HashSet<>(), new HashSet<>());

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Set<Product> campaignContainProducts = new HashSet<>();
        campaignContainProducts.add(product1);

        Campaign campaign = new Campaign(1L, "campaign", 10f, campaignContainProducts, new HashSet<>());
        //when
        Mockito.when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));
        Float result = salesService.calculateDiscountAmount(products, campaignId);
        //then
        assertEquals(expectedDiscountAmount, result);
        Mockito.verify(campaignRepository).findById(campaignId);
    }

    @DisplayName("calculateDiscountAmount() method should throw CampaignNotFoundException when given campaignId is not valid")
    @Test
    public void shouldThrowCampaignNotFoundException_WhenCampaignDoesNotExistWithGivenCampaignId() {
        //given
        Long campaignId = 1L;
        //when
        Mockito.when(campaignRepository.findById(campaignId)).thenReturn(Optional.empty());
        //then
        assertThrows(CampaignNotFoundException.class, () -> salesService.calculateDiscountAmount(new ArrayList<>(), campaignId));
    }

    @DisplayName("applyCampaignToSale() method should return SaleDto with given saleId and campaignId when sale and campaign exist")
    @Test
    public void shouldReturnSaleDtoWithGivenParameter_WhenSaleAndCampaignExists() {
        // given
        Long saleId = 1L;
        Long campaignId = 1L;
        Sale sale = new Sale(saleId, 100f, 10f,false,100f,0f,100f,LocalDateTime.now(), new HashSet<>(), new HashSet<>());
        Campaign campaign = new Campaign(1L, "campaign", 10f, new HashSet<>(), new HashSet<>());
        SaleCampaign saleCampaign = new SaleCampaign(1L, 10f, sale, campaign);
        SaleDto expected = new SaleDto(sale);
        //when
        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));
        Mockito.when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));
        Mockito.when(saleCampaignRepository.save(Mockito.any(SaleCampaign.class))).thenReturn(saleCampaign);
        SaleDto result = salesService.applyCampaignToSale(saleId, campaignId);
        //then
        assertEquals(expected, result);
        Mockito.verify(saleRepository).findById(saleId);
        Mockito.verify(campaignRepository, Mockito.times(2)).findById(campaignId);
        Mockito.verify(saleCampaignRepository).save(Mockito.any(SaleCampaign.class));
    }

    @DisplayName("applyCampaignToSale() method should return SaleDto with given saleId and campaignId when sale does not exist and campaign exist")
    @Test
    public void shouldReturnSaleDtoWithGivenParameter_WhenSaleDoesNotExistAndCampaignExist() {
        // given
        Long saleId = 1L;
        Long campaignId = 1L;
        //when
        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.empty());
        //then
        assertThrows(SaleNotFoundException.class, () -> salesService.applyCampaignToSale(saleId, campaignId));
        Mockito.verify(saleRepository).findById(saleId);

    }

    @DisplayName("applyCampaignToSale() method should return SaleDto with given saleId and campaignId when sale exist and campaign does not exist")
    @Test
    public void shouldReturnSaleDtoWithGivenParameter_WhenSaleExistAndCampaignDoesNotExist() {
        // given
        Long saleId = 1L;
        Long campaignId = 1L;
        Sale sale = new Sale(saleId, 100f, 10f,false,100f,0f,100f,LocalDateTime.now(), new HashSet<>(), new HashSet<>());
        //when
        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));
        Mockito.when(campaignRepository.findById(campaignId)).thenReturn(Optional.empty());
        //then
        assertThrows(CampaignNotFoundException.class, () -> salesService.applyCampaignToSale(saleId, campaignId));
        Mockito.verify(campaignRepository).findById(saleId);

    }

    @AfterEach
    void tearDown() {
    }
}