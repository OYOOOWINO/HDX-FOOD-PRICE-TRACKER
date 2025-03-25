package com.kestats.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kestats.api.models.CommodityPrice;

public interface CommodityPriceRepository extends JpaRepository<CommodityPrice,UUID> {

}
