package com.example.Sales.repository;

import com.example.Sales.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByIsDeletedFalseAndId(Long id);
    List<Product> findAllByIsDeletedFalse();
}
