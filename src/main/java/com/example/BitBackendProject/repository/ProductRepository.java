package com.example.BitBackendProject.repository;

import com.example.BitBackendProject.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final EntityManager entityManager;

    @Autowired
    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Product getProductById(Long id) {
        try {
            String query = "SELECT p FROM Product AS p WHERE p.id = :id";
            Product product = entityManager.createQuery(query, Product.class).setParameter("id", id).getSingleResult();
            return product;
        } catch (NoResultException e) {
            return null;
        }
    }
}
