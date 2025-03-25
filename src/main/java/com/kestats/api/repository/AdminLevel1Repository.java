package com.kestats.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kestats.api.models.AdminLevel1;

@Repository
public interface AdminLevel1Repository extends JpaRepository<AdminLevel1, UUID> {

}
