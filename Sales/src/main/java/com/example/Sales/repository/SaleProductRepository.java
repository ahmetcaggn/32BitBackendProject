package com.example.Sales.repository;

import com.example.Sales.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct,Long> {
}
