package com.kestats.api.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.kestats.api.models.CommodityPrice;

import jakarta.persistence.criteria.Predicate;

public class CommodityPriceSpecification {

    public static Specification<CommodityPrice> filterBy(
            UUID categoryId, UUID marketId, UUID commodityId, UUID admin1Id, UUID admin2Id) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            System.out.println(categoryId
            );
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (marketId != null) {
                predicates.add(cb.equal(root.get("market").get("id"), marketId));
            }
            if (commodityId != null) {
                predicates.add(cb.equal(root.get("commodity").get("id"), commodityId));
            }
            if (admin1Id != null) {
                predicates.add(cb.equal(root.get("adminLevel1").get("id"), admin1Id));
            }
            if (admin2Id != null) {
                predicates.add(cb.equal(root.get("adminLevel2").get("id"), admin2Id));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    };
}
