package com.kestats.api.services;

import com.kestats.api.models.CommodityPrice;
import com.kestats.api.repository.CommodityPriceRepository;
import com.kestats.api.specifications.CommodityPriceSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommodityPriceService {
    private final CommodityPriceRepository commodityPriceRepository;

    public CommodityPriceService(CommodityPriceRepository commodityPriceRepository) {
        this.commodityPriceRepository = commodityPriceRepository;
    }

    public Page<CommodityPrice> getFilteredCommodityPrices(UUID categoryId, UUID marketId, UUID commodityId,
            UUID admin1Id, UUID admin2Id, Pageable pageable) {
        Specification<CommodityPrice> spec = CommodityPriceSpecification.filterBy(categoryId, marketId, commodityId,
                admin1Id, admin2Id);
        return commodityPriceRepository.findAll(spec, pageable);
    }
}
