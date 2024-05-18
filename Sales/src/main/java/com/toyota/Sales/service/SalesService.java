package com.toyota.Sales.service;

import com.toyota.Sales.dto.*;
import com.toyota.Sales.entity.*;
import com.toyota.Sales.interfaces.ProductInterface;
import com.toyota.Sales.exception.CampaignNotFoundException;
import com.toyota.Sales.exception.SaleNotFoundException;
import com.toyota.Sales.exception.SaleProductNotFoundException;
import com.toyota.Sales.repository.CampaignRepository;
import com.toyota.Sales.repository.SaleCampaignRepository;
import com.toyota.Sales.repository.SaleProductRepository;
import com.toyota.Sales.repository.SaleRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.toyota.Sales.util.SaleUtility.setSaleTotalAmount;
import static com.toyota.Sales.util.SaleUtility.setSaleTotalTax;

@Service
public class SalesService {

    private final ProductInterface productInterface;
    private final SaleRepository saleRepository;
    private final SaleProductRepository saleProductRepository;
    private final CampaignRepository campaignRepository;
    private final SaleCampaignRepository saleCampaignRepository;


    public SalesService(SaleRepository saleRepository, SaleProductRepository saleProductRepository, CampaignRepository campaignRepository, SaleCampaignRepository saleCampaignRepository, ProductInterface productInterface) {
        this.saleRepository = saleRepository;
        this.saleProductRepository = saleProductRepository;
        this.campaignRepository = campaignRepository;
        this.saleCampaignRepository = saleCampaignRepository;
        this.productInterface = productInterface;
    }

    public SaleDto createSale() {
        Sale sale = new Sale();
        sale.setTotalAmount(0);
        sale.setTotalTax(0);
        sale.setDateTime(LocalDateTime.now());
        return new SaleDto(saleRepository.save(sale));
    }

    public Page<SaleDto> getAllSalesByPagination(Integer page, Integer row, String sort, String sortDirectionRequest, Long filterProductId, Long filterCampaignId, Float minPrice, Float maxPrice) {
        Sort.Direction sortDirection = (sortDirectionRequest.equalsIgnoreCase("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(page, row, Sort.by(sortDirection, sort));

        if (maxPrice == null || maxPrice < 0) maxPrice = Float.MAX_VALUE;
        if (minPrice == null || minPrice < 0) minPrice = 0f;

        if (filterProductId != null && filterCampaignId != null) {
            Page<Sale> sales = saleRepository.findByCampaignIdAndProductIdAndTotalAmountBetween(filterCampaignId, filterProductId, minPrice, maxPrice, pageRequest);
            return sales.map(SaleDto::new);
        }
        if (filterProductId != null) {
            Set<SaleProduct> saleProducts = saleProductRepository.findAllByProductId(filterProductId);
            Page<Sale> sales = saleRepository.findBySalesProductsInAndTotalAmountBetween(saleProducts, minPrice, maxPrice, pageRequest);
            return sales.map(SaleDto::new);
        }
        if (filterCampaignId != null) {
            Page<Sale> sales = saleRepository.findBySaleCampaignsInAndTotalAmountBetween(saleCampaignRepository.findAllByCampaignId(filterCampaignId), minPrice, maxPrice, pageRequest);
            return sales.map(SaleDto::new);
        }

        Page<Sale> sales = saleRepository.findAllByTotalAmountBetween(minPrice, maxPrice, pageRequest);
        return sales.map(SaleDto::new);
    }

    public SaleProductDto addSaleProduct(Long saleId, Long productId, Float quantity) {
        if (quantity == null || quantity <= 0) quantity = 1f;
        Sale sale = saleRepository.findById(saleId).orElseThrow(
                () -> new SaleNotFoundException("There is no sale with id: " + saleId)
        );

        ProductDto productDto = productInterface.getProductById(productId);
        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setProduct(new Product(productDto));
        saleProduct.setQuantity(quantity);
        setSaleTotalAmount(sale, saleProduct);
        setSaleTotalTax(sale, saleProduct);
        saleProduct.setSale(sale);

        return new SaleProductDto(saleProductRepository.save(saleProduct));
    }

    public SaleProductDto updateSaleProduct(Long saleProductId, Float quantity) {
        if (quantity == null || quantity <= 0) quantity = 1f;
        SaleProduct saleProduct = saleProductRepository.findById(saleProductId).orElseThrow(
                () -> new SaleProductNotFoundException("There is no saleProduct with id: " + saleProductId)
        );
        saleProduct.setQuantity(quantity);
        setSaleTotalAmount(saleProduct.getSale());
        setSaleTotalTax(saleProduct.getSale());
        return new SaleProductDto(saleProductRepository.save(saleProduct));
    }

    public void deleteSaleProduct(Long saleProductId) {
        SaleProduct saleProduct = saleProductRepository.findById(saleProductId).orElseThrow(
                () -> new SaleProductNotFoundException("There is no saleProduct with id: " + saleProductId + " to delete")
        );
        saleProductRepository.delete(saleProduct);
        setSaleTotalTax(saleProduct.getSale());
        setSaleTotalAmount(saleProduct.getSale());
        saleRepository.save(saleProduct.getSale());
    }

    public SaleDto applyCampaignToSale(Long saleId, Long campaignId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(
                () -> new SaleNotFoundException("There is no sale with id: " + saleId)
        );
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(
                () -> new CampaignNotFoundException("There is no campaign with id: " + campaignId)
        );

        float discountAmount = calculateDiscountAmount(getProductListInASale(sale), campaignId);

        SaleCampaign saleCampaign = new SaleCampaign();
        saleCampaign.setSale(sale);
        saleCampaign.setCampaign(campaign);
        saleCampaign.setDiscountAmount(discountAmount);
        saleCampaignRepository.save(saleCampaign);

        SaleDto saleDto = new SaleDto(sale);
        saleDto.setDiscountAmount(discountAmount);
        saleDto.setLastPrice(saleDto.getTotalAmount() - saleDto.getDiscountAmount());
        return saleDto;
    }

    public List<CampaignDto> getAllRelevantCampaigns(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(
                () -> new SaleNotFoundException("There is no sale with id: " + saleId)
        );
        List<Campaign> campaigns = new ArrayList<>();
        List<CampaignDto> campaignDtoList = new ArrayList<>();
        for (SaleProduct saleProduct : sale.getSalesProducts()) {
            for (Campaign campaign : saleProduct.getProduct().getCampaigns()) {
                if (!campaigns.contains(campaign)) {
                    campaigns.add(campaign);
                }
            }
        }
        for (Campaign campaign : campaigns) {
            campaignDtoList.add(new CampaignDto(campaign));
        }
        return campaignDtoList;
    }

    public float calculateDiscountAmount(List<Product> products, Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(
                () -> new CampaignNotFoundException("There is no campaign with id: " + campaignId)
        );
        List<Product> discountableProducts = new ArrayList<>();
        float discountAmount = 0f;
        for (Product product : campaign.getIncludedProducts()) {
            if (products.contains(product)) {
                discountableProducts.add(product);
            }
        }
        for (Product product : discountableProducts) {
            discountAmount += product.getPrice() * campaign.getDiscountRate() / 100;
        }
        return discountAmount;
    }

    public List<Product> getProductListInASale(Sale sale) {
        List<Product> products = new ArrayList<>();
        for (SaleProduct saleProduct : sale.getSalesProducts()) {
            products.add(saleProduct.getProduct());
        }
        return products;
    }

    public SaleDto getSaleById(Long id) {
        return new SaleDto(saleRepository.findById(id).orElseThrow(
                () -> new SaleNotFoundException("There is no sale with id: " + id)
        ));
    }
}
