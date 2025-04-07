package com.kestats.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kestats.api.models.AdminLevel2;

@Repository
public interface AdminLevel2Repository extends JpaRepository<AdminLevel2, UUID> {

}
