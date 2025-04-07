package com.kestats.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.kestats.api.models.CommodityPrice;

@Service
public interface CommodityPriceRepository
                extends JpaRepository<CommodityPrice, UUID>, JpaSpecificationExecutor<CommodityPrice> {

}
