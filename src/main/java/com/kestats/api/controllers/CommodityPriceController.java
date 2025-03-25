package com.kestats.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kestats.api.NotFoundException;
import com.kestats.api.models.CommodityPrice;
import com.kestats.api.repository.CommodityPriceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/prices")
public class CommodityPriceController {
    private final CommodityPriceRepository commodityPriceRepository;

    public CommodityPriceController(CommodityPriceRepository commodityPriceRepository) {
        this.commodityPriceRepository = commodityPriceRepository;
    }

    @GetMapping("")
    List<CommodityPrice> findAll() {
        System.out.println("GOT MEME");
        return this.commodityPriceRepository.findAll();
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
