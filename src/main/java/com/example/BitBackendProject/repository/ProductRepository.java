package com.example.BitBackendProject.repository;

import com.example.BitBackendProject.dto.ProductDto;
import com.example.BitBackendProject.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public class ProductRepository {
    private final EntityManager entityManager;

    @Autowired
    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Product getProductById(Long id) {
        try {
            Product product = entityManager.find(Product.class, id);
            if (!product.getIsDeleted()) {
                return product;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Product> getAllProducts() {
        try {
            List<Product> productList = entityManager.createQuery("SELECT p FROM Product AS p WHERE isDeleted = false", Product.class).getResultList();
            return productList;
        } catch (NoResultException e) {
            return null;
        }
    }
}