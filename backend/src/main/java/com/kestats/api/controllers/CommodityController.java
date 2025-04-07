package com.kestats.api.controllers;

import java.util.Optional;
import java.util.UUID;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kestats.api.NotFoundException;
import com.kestats.api.models.Commodity;
import com.kestats.api.repository.CommodityRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/commodities")
public class CommodityController {
private final CommodityRepository commodityRepository;

    public CommodityController(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    @GetMapping("")
    public Page<Commodity> findAll(  @RequestParam int page, @RequestParam int size) {
         PageRequest pageRequest = PageRequest.of(page, size);
        return this.commodityRepository.findAll(pageRequest);
    }

    @GetMapping("/{id}")
    Commodity findById(@PathVariable UUID id) {
        Optional<Commodity> commodity = this.commodityRepository.findById(id);
        if (commodity.isEmpty()) {
            throw new NotFoundException();
        }
        return commodity.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create( @RequestBody Commodity commodity) {
        this.commodityRepository.save(commodity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{id}")
    void update(@PathVariable UUID id, @RequestBody Commodity commodity) {
        Optional<Commodity> existing = this.commodityRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException();
        }
        this.commodityRepository.save(commodity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id) {
        this.commodityRepository.delete(this.commodityRepository.findById(id).get());
    }

}
