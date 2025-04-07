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
import com.kestats.api.models.Category;
import com.kestats.api.repository.CategoryRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("")
    Page<Category> findAll(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return this.categoryRepository.findAll(pageRequest);
    }

    @GetMapping("/{id}")
    Category findById(@PathVariable UUID id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException();
        }
        return category.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Category category) {
        this.categoryRepository.save(category);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{id}")
    void update(@PathVariable UUID id, @RequestBody Category category) {
        Optional<Category> existing = this.categoryRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException();
        }
        this.categoryRepository.save(category);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id) {
        this.categoryRepository.delete(this.categoryRepository.findById(id).get());
    }

}
