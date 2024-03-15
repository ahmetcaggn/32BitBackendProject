package com.example.BitBackendProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_list")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "productCode")
    private Long productCode;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Float price;
}
