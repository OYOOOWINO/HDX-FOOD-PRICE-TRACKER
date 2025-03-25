package com.kestats.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kestats.api.NotFoundException;
import com.kestats.api.models.AdminLevel1;
import com.kestats.api.repository.AdminLevel1Repository;

@RestController
@CrossOrigin
@RequestMapping("/api/adminlevel1")
public class AdminLevel1Controller {
    private final AdminLevel1Repository adminRepository;

    public AdminLevel1Controller(AdminLevel1Repository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping("")
    List<AdminLevel1> findAll() {
        return adminRepository.findAll();
    }

    @GetMapping("/{id}")
    AdminLevel1 findById(@PathVariable UUID id) {
        Optional<AdminLevel1> admin = this.adminRepository.findById(id);
        if (admin.isEmpty()) {
            throw new NotFoundException();
        }
        return admin.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody AdminLevel1 admin) {
        this.adminRepository.save(admin);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("{id}")
    void update(@PathVariable UUID id, @RequestBody AdminLevel1 admin1) {
        Optional<AdminLevel1> admin = this.adminRepository.findById(id);
        if (admin.isEmpty()) {
            throw new NotFoundException();
        }
        this.adminRepository.save(admin1);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id) {
        this.adminRepository.delete(this.adminRepository.findById(id).get());
    }

}
