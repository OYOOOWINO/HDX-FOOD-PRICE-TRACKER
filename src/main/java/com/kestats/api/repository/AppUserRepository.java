package com.kestats.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kestats.api.models.AppUser;


public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}