package com.toyota.Sales.repository;

import com.toyota.Sales.entity.Sale;
import com.toyota.Sales.entity.SaleCampaign;
import com.toyota.Sales.entity.SaleProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findAllByTotalAmountBetween(Float minPrice, Float maxPrice, Pageable pageable);
    Page<Sale> findBySalesProductsInAndTotalAmountBetween(Set<SaleProduct> products, Float minPrice, Float maxPrice, Pageable pageable);
    Page<Sale> findBySaleCampaignsInAndTotalAmountBetween(Set<SaleCampaign> campaigns, Float minPrice, Float maxPrice, Pageable pageable);

    @Query("SELECT s FROM Sale s " +
            "JOIN SaleCampaign sc ON s.id = sc.sale.id " +
            "JOIN SaleProduct sp ON s.id = sp.sale.id " +
            "WHERE sc.campaign.id = :campaignId AND sp.product.id = :productId AND s.totalAmount >= :minValue AND s.totalAmount <= :maxValue")
    Page<Sale> findByCampaignIdAndProductIdAndTotalAmountBetween(@Param("campaignId") Long campaignId,
                                                                 @Param("productId") Long productId,
                                                                 @Param("minValue") Float minPrice,
                                                                 @Param("maxValue") Float maxPrice,
                                                                 Pageable pageable);

}
