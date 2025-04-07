package com.kestats.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kestats.api.NotFoundException;
import com.kestats.api.models.CommodityPrice;
import com.kestats.api.models.PriceTrendDto;
import com.kestats.api.repository.CommodityPriceRepository;
import com.kestats.api.services.CommodityPriceService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@CrossOrigin
@RequestMapping("/api/prices")
public class CommodityPriceController {
    private final CommodityPriceRepository commodityPriceRepository;
    private final CommodityPriceService commodityPriceService;

    public CommodityPriceController(CommodityPriceRepository commodityPriceRepository,
            CommodityPriceService commodityPriceService) {
        this.commodityPriceRepository = commodityPriceRepository;
        this.commodityPriceService = commodityPriceService;
    }

    @GetMapping("/trend")
    public ResponseEntity<List<PriceTrendDto>> getPriceTrend(
            @RequestParam(required = false) UUID commodity,
            @RequestParam(required = false) UUID category,
            @RequestParam(required = false) UUID market,
            @RequestParam(required = true) LocalDate from,
            @RequestParam(required = true) LocalDate to
            ) {
        List<PriceTrendDto> result = commodityPriceService.getPriceTrend(commodity, category, market,from,to);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter")
    public Page<CommodityPrice> filterCommodityPrices(
            @RequestParam(required = false) UUID category,
            @RequestParam(required = false) UUID market,
            @RequestParam(required = false) UUID commodity,
            @RequestParam(required = false) UUID admin1,
            @RequestParam(required = false) UUID admin2,
            @RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        var prices = commodityPriceService.getFilteredCommodityPrices(category, market, commodity, admin1, admin2,
                pageRequest);
        return prices;
    }

    @GetMapping("/{id}")
    CommodityPrice findById(@PathVariable UUID id) {
        Optional<CommodityPrice> commodity = this.commodityPriceRepository.findById(id);
        if (commodity.isEmpty()) {
            throw new NotFoundException();
        }
        return commodity.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody CommodityPrice commodityPrice) {
        this.commodityPriceRepository.save(commodityPrice);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{id}")
    void update(@PathVariable UUID id, @RequestBody CommodityPrice commodityPrice) {
        Optional<CommodityPrice> existing = this.commodityPriceRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException();
        }
        this.commodityPriceRepository.save(commodityPrice);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id) {
        this.commodityPriceRepository.delete(this.commodityPriceRepository.findById(id).get());
    }
}
