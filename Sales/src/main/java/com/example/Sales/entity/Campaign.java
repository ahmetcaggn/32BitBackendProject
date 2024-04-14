package com.example.Sales.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private float discountRate;


    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "Campaign_Product",
            joinColumns = @JoinColumn(name = "Campaign_Id"),
            inverseJoinColumns = @JoinColumn(name = "Product_Id")
    )
    Set<Product> includedProducts;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<SaleCampaign> saleCampaigns = new HashSet<>();
}

