package com.kestats.api.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CommodityPriceCustomRepository {
    List<PriceTrendDto> findPriceTrend(UUID commodityId, UUID categoryId, UUID marketId,LocalDate from, LocalDate to);
}
