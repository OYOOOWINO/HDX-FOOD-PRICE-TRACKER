package com.kestats.api.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.kestats.api.models.CommodityPrice;
import com.kestats.api.models.CommodityPriceCustomRepository;
import com.kestats.api.models.PriceTrendDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class PriceTrendRepository implements CommodityPriceCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PriceTrendDto> findPriceTrend(UUID commodityId, UUID categoryId, UUID marketId, LocalDate from,
            LocalDate to) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<PriceTrendDto> query = criteriaBuilder.createQuery(PriceTrendDto.class);
        Root<CommodityPrice> root = query.from(CommodityPrice.class);
        System.out.println("CommodityId: " + commodityId);
        // SELECT Fields
        Expression<LocalDate> date = criteriaBuilder.function("DATE", LocalDate.class, root.get("createdAt"));
        Expression<Double> avgPrice = criteriaBuilder.avg(root.get("price"));
        Expression<Double> avgUsdPrice = criteriaBuilder.avg(root.get("usdprice"));

        // WHERE
        List<Predicate> filters = new ArrayList<>();
        filters.add(criteriaBuilder.between(root.get("createdAt"), from, to));
        if (commodityId != null) {
            filters.add(criteriaBuilder.equal(root.get("commodity").get("id"), commodityId));
        }
        if (marketId != null) {
            filters.add(criteriaBuilder.equal(root.get("market").get("id"), marketId));
        }
        query.select(criteriaBuilder.construct(
                PriceTrendDto.class,
                date,
                avgPrice,
                avgUsdPrice));
        query.where(filters.toArray(new Predicate[0]));

        query.groupBy(date);
        query.orderBy(criteriaBuilder.asc(date));

        // Run the query
        return em.createQuery(query).getResultList();
    }
}
