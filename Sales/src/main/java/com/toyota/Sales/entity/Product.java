package com.toyota.Sales.entity;

import com.toyota.Sales.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @SequenceGenerator(name = "product_generator", allocationSize = 1)
    @GeneratedValue(generator = "product_generator", strategy = GenerationType.SEQUENCE)
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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<SaleProduct> saleProducts = new HashSet<>();

    @ManyToMany(mappedBy = "includedProducts")
    @JsonBackReference
    private Set<Campaign> campaigns;

    public Product(ProductDto productDto){
        this.id = productDto.getId();
        this.productCode = productDto.getProductCode();
        this.name = productDto.getName();
        this.price = productDto.getPrice();
        this.isDeleted = false;
        this.tax = productDto.getTax();

    }
}
