package com.kestats.api.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.locationtech.jts.geom.Point;

import com.kestats.api.utils.GeneratedId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "market", indexes = {
        @Index(columnList = "name")
})
public class Market {

    @Id
   @GeneratedId
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "adminlevel1id")
    private AdminLevel1 adminLevel1;

    @ManyToOne
    @JoinColumn(name = "adminlevel2id")
    private AdminLevel2 adminLevel2;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    private Point location;

    @OneToMany(mappedBy = "market")
    private List<CommodityPrice> commodityPrices;

    private Float lat;

    private Float lng;

}
