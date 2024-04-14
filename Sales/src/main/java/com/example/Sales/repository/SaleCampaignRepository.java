package com.example.Sales.repository;

import com.example.Sales.entity.SaleCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleCampaignRepository extends JpaRepository<SaleCampaign,Long> {
}
