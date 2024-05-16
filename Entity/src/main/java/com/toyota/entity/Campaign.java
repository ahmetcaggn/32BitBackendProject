package com.toyota.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Campaign {
    @Id
    @SequenceGenerator(name = "campaign_generator",allocationSize = 1)
    @GeneratedValue(generator = "campaign_generator",strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private float discountRate;


    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "Campaign_Product",
            joinColumns = @JoinColumn(name = "Campaign_Id"),
            inverseJoinColumns = @JoinColumn(name = "Product_Id")
    )
    private Set<Product> includedProducts;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<SaleCampaign> saleCampaigns = new HashSet<>();
}

