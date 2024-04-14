package com.example.Sales.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SaleCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float discountAmount;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
}
