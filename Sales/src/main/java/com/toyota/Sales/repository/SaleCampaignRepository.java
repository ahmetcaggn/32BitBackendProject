package com.toyota.Sales.repository;

import com.toyota.Sales.entity.SaleCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SaleCampaignRepository extends JpaRepository<SaleCampaign, Long> {

    @Query("SELECT sc FROM SaleCampaign sc WHERE sc.campaign.id = :campaignId")
    Set<SaleCampaign> findAllByCampaignId(@Param("campaignId") Long campaignId);
}
