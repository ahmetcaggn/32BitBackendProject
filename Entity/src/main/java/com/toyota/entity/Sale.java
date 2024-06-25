package com.toyota.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Sale {
    @Id
    @SequenceGenerator(name = "sale_generator",allocationSize = 1)
    @GeneratedValue(generator = "sale_generator",strategy = GenerationType.SEQUENCE)
    private Long id;
    private float totalAmount;
    private float totalTax;
    private LocalDateTime dateTime;


    @JsonManagedReference
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private Set<SaleProduct> salesProducts = new HashSet<>();


    @JsonManagedReference
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private Set<SaleCampaign> saleCampaigns = new HashSet<>();
}
