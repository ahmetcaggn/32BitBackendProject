package com.example.Sales.service;

import com.example.Sales.dto.CampaignDto;
import com.example.Sales.dto.SaleDto;
import com.example.Sales.entity.*;
import com.example.Sales.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {

    ProductRepository productRepository;
    SaleRepository saleRepository;
    SaleProductRepository saleProductRepository;
    CampaignRepository campaignRepository;
    SaleCampaignRepository saleCampaignRepository;

    @Autowired
    public SalesService(ProductRepository productRepository, SaleRepository saleRepository, SaleProductRepository saleProductRepository, CampaignRepository campaignRepository, SaleCampaignRepository saleCampaignRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.saleProductRepository = saleProductRepository;
        this.campaignRepository = campaignRepository;
        this.saleCampaignRepository = saleCampaignRepository;
    }

    public SaleDto createSale(){
        Sale sale = new Sale();
        sale.setTotalAmount(0);
        sale.setTotalTax(0);
        sale.setDateTime(LocalDateTime.now());
        return new SaleDto(saleRepository.save(sale));
    }
    public List<SaleDto> getAllSales(){
        List<SaleDto> saleDtoList = new ArrayList<>();
        for (Sale sale : saleRepository.findAll()){
            saleDtoList.add(new SaleDto(sale));
        }
        return saleDtoList;
    }
    public SaleDto addSaleProduct(Long saleId, Long productId, Integer quantity) {

        Product product = productRepository.findByIsDeletedFalseAndId(productId);
        Sale sale = saleRepository.findById(saleId).get();

        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setSale(sale);
        saleProduct.setProduct(product);
        saleProduct.setQuantity(quantity);

        setSaleTotalAmount(sale, saleProduct);
        setSaleTotalTax(sale, saleProduct);


        return new SaleDto(saleProductRepository.save(saleProduct).getSale());
    }

    public SaleDto applyCampaignToSale(Long saleId, Long campaignId) {
        Sale sale = saleRepository.findById(saleId).get();
        Campaign campaign = campaignRepository.findById(campaignId).get();

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

    public List<CampaignDto> getAllReleventCampaigns(Long saleId) {
        Sale sale = saleRepository.findById(saleId).get();
        List<Campaign> campaigns = new ArrayList<>();
        List<CampaignDto> campaignDtoList = new ArrayList<>();
        for (SaleProduct saleProduct : sale.getSalesProducts()){
            for (Campaign campaign : saleProduct.getProduct().getCampaigns()){
                if (!campaigns.contains(campaign)) {
                    campaigns.add(campaign);
                }
            }
        }
        for (Campaign campaign : campaigns){
            campaignDtoList.add(new CampaignDto(campaign));
        }
        return campaignDtoList;
    }

    public float calculateDiscountAmount(List<Product> products, Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId).get();
        List<Product> discountableProducts = new ArrayList<>();
        Float discountAmount = 0f;
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
        return new SaleDto(saleRepository.findById(id).get());
    }

    public void setSaleTotalAmount(Sale sale) {
        float total = 0f;
        for (SaleProduct saleProduct : sale.getSalesProducts()) {
            total += saleProduct.getQuantity() * saleProduct.getProduct().getPrice();
        }
        sale.setTotalAmount(total);
    }

    public void setSaleTotalAmount(Sale sale, SaleProduct sP) {
        float total = 0f;
        for (SaleProduct saleProduct : sale.getSalesProducts()) {
            total += saleProduct.getQuantity() * saleProduct.getProduct().getPrice();
        }
        total += sP.getQuantity() * sP.getProduct().getPrice();
        sale.setTotalAmount(total);
    }

    public void setSaleTotalTax(Sale sale, SaleProduct sP) {
        float total = 0f;
        for (SaleProduct saleProduct : sale.getSalesProducts()) {
            total += saleProduct.getQuantity() * saleProduct.getProduct().getPrice() * saleProduct.getProduct().getTax() / 100;
        }
        total += sP.getQuantity() * sP.getProduct().getPrice() * sP.getProduct().getTax() / 100;
        sale.setTotalTax(total);
    }
}
