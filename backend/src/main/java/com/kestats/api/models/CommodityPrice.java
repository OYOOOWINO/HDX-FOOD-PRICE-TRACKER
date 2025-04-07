package com.kestats.api.models;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kestats.api.utils.GeneratedId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityPrice {
    @Id
   @GeneratedId
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDate createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDate updatedAt;

    String unit;
    Float price;
    Float usdprice;
    String pricetype;
    String priceflag;
    String commodity_name;
    String category_name;
    String market_name;
    @ManyToOne
    @JoinColumn(name = "adminlevel1id")
    @JsonIgnore
    private AdminLevel1 adminLevel1;

    @ManyToOne
    @JoinColumn(name = "adminlevel2id")
    @JsonIgnore
    private AdminLevel2 adminLevel2;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name = "commodityid")
    @JsonIgnore
    private Commodity commodity;

    @ManyToOne
    @JoinColumn(name = "marketid")
    @JsonIgnore
    private Market market;

}
