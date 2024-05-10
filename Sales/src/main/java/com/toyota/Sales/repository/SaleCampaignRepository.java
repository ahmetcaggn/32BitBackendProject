package com.toyota.Sales.repository;

import com.toyota.Sales.entity.SaleCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleCampaignRepository extends JpaRepository<SaleCampaign,Long> {
}
