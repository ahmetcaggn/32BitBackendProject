package com.toyota.Sales.repository;

import com.toyota.Sales.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleProductRepository extends JpaRepository<SaleProduct,Long> {
}
