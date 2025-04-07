package com.kestats.api.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kestats.api.utils.GeneratedId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "adminlevel2", indexes = {
        @Index(columnList = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminLevel2 {

    @Id
    @GeneratedId
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "adminlevel1id")
    @JsonIgnore
    private AdminLevel1 adminLevel1;

    @OneToMany(mappedBy = "adminLevel2")
   @JsonIgnore
    private List<Market> markets;

    @OneToMany(mappedBy = "adminLevel2")
   @JsonIgnore
    private List<CommodityPrice> commodityPrices;

    @Version
    private int version;
}
