package com.kestats.api.repository.pages;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.kestats.api.models.CommodityPrice;

@Repository
public interface PricesPagingRepository extends PagingAndSortingRepository<CommodityPrice, UUID> {

}
