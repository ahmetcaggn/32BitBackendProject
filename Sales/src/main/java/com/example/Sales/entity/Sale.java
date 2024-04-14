package com.example.Sales.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.netty.channel.ChannelMeters;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
