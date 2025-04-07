package com.kestats.api.services;

import com.kestats.api.models.CommodityPrice;
import com.kestats.api.models.CommodityPriceCustomRepository;
import com.kestats.api.models.PriceTrendDto;
import com.kestats.api.repository.CommodityPriceRepository;
import com.kestats.api.specifications.CommodityPriceSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CommodityPriceService {
    private final CommodityPriceRepository commodityPriceRepository;
    private final CommodityPriceCustomRepository customRepo;

    public CommodityPriceService(CommodityPriceRepository commodityPriceRepository,
            CommodityPriceCustomRepository customRepo) {
        this.commodityPriceRepository = commodityPriceRepository;
        this.customRepo = customRepo;
    }

    public Page<CommodityPrice> getFilteredCommodityPrices(UUID categoryId, UUID marketId, UUID commodityId,
            UUID admin1Id, UUID admin2Id, Pageable pageable) {
        Specification<CommodityPrice> spec = CommodityPriceSpecification.filterBy(categoryId, marketId, commodityId,
                admin1Id, admin2Id);
        return commodityPriceRepository.findAll(spec, pageable);
    }

    public List<PriceTrendDto> getPriceTrend(UUID commodityId, UUID categoryId, UUID marketId, LocalDate from, LocalDate to) {
        return customRepo.findPriceTrend(commodityId, categoryId, marketId, from ,to);
    }
}
