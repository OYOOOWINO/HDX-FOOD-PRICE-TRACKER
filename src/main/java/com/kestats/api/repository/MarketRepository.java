package com.kestats.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kestats.api.models.Market;

public interface MarketRepository extends JpaRepository<Market,UUID> {
}
