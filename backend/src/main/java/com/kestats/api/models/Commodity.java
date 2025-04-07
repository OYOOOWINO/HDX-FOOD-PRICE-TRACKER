package com.kestats.api.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kestats.api.utils.GeneratedId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "commodity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {

    @Id
   @GeneratedId
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    String unit;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    @JsonIgnore
    private Category category;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "commodity")
   @JsonIgnore
    private List<CommodityPrice> commodityPrices;

}
