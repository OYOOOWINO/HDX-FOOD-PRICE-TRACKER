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
import com.kestats.api.models.Market;
import com.kestats.api.repository.MarketRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/markets")
public class MarketController {

    private final MarketRepository marketRepository;

    public MarketController(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    @GetMapping("")
    Page<Market> findAll(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return this.marketRepository.findAll(pageRequest);
    }

    @GetMapping("/{id}")
    Market findById(@PathVariable UUID id) {
        Optional<Market> Market = this.marketRepository.findById(id);
        if (Market.isEmpty()) {
            throw new NotFoundException();
        }
        return Market.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Market market) {
        this.marketRepository.save(market);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{id}")
    void update(@PathVariable UUID id, @RequestBody Market market) {
        Optional<Market> existing = this.marketRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException();
        }
        this.marketRepository.save(market);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id) {
        this.marketRepository.delete(this.marketRepository.findById(id).get());
    }
}
