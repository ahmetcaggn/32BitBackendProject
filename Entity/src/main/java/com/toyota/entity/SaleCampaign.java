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
@Entity
public class SaleCampaign {
    @Id
    @SequenceGenerator(name = "saleCampaign_generator",allocationSize = 1)
    @GeneratedValue(generator = "saleCampaign_generator",strategy = GenerationType.SEQUENCE)
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
