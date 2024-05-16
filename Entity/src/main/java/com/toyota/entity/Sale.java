package com.toyota.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Sale {
    @Id
    @SequenceGenerator(name = "sale_generator",allocationSize = 1)
    @GeneratedValue(generator = "sale_generator",strategy = GenerationType.SEQUENCE)
    protected Long id;
    protected float totalAmount;
    protected float totalTax;
    protected LocalDateTime dateTime;


    @JsonManagedReference
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    protected Set<SaleProduct> salesProducts = new HashSet<>();


    @JsonManagedReference
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    protected Set<SaleCampaign> saleCampaigns = new HashSet<>();
}
