package com.kestats.api.models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceTrendDto {
    private LocalDate date;
    private Double avgPriceKES;
    private Double avgPriceUSD;

    public PriceTrendDto(LocalDate date, Double avgPriceKES, Double avgPriceUSD) {
        this.date = date;
        this.avgPriceKES = avgPriceKES;
        this.avgPriceUSD = avgPriceUSD;
    }

}