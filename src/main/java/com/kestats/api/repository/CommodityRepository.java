package com.kestats.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kestats.api.models.Commodity;

public interface CommodityRepository extends JpaRepository<Commodity,UUID> {
}