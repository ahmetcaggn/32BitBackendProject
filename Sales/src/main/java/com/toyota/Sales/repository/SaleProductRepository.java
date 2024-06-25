package com.toyota.Sales.repository;

import com.toyota.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SaleProductRepository extends JpaRepository<SaleProduct, Long> {

    @Query("SELECT sp FROM SaleProduct sp WHERE sp.product.id = :productId")
    Set<SaleProduct> findAllByProductId(@Param("productId") Long productId);
}
