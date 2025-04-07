package com.kestats.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kestats.api.models.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}