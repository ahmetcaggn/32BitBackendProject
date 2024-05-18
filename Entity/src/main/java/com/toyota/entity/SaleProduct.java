package com.toyota.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sale_product")
@Entity
public class SaleProduct {
    @Id
    @SequenceGenerator(name = "saleProduct_generator",allocationSize = 1)
    @GeneratedValue(generator = "saleProduct_generator",strategy = GenerationType.SEQUENCE)
    private Long id;
    private Float quantity;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

}
