package com.example.Sales.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "product_code", unique = true)
    private Long productCode;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Float price;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "tax")
    private Float tax;

    @JsonBackReference
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<SaleProduct> saleProducts = new HashSet<>();

    @ManyToMany(mappedBy = "includedProducts")
    @JsonBackReference
    private Set<Campaign> campaigns;
}
