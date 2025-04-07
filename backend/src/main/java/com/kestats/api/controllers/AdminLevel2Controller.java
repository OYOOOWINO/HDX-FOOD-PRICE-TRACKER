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
import com.kestats.api.models.AdminLevel2;
import com.kestats.api.repository.AdminLevel2Repository;

@RestController
@CrossOrigin
@RequestMapping("/api/adminlevel2")
public class AdminLevel2Controller {
    private final AdminLevel2Repository adminRepository;

    public AdminLevel2Controller(AdminLevel2Repository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping("")
    List<AdminLevel2> findAll() {
        return adminRepository.findAll();
    }

    @GetMapping("/{id}")
    AdminLevel2 findById(@PathVariable UUID id) {
        Optional<AdminLevel2> admin = this.adminRepository.findById(id);
        if (admin.isEmpty()) {
            throw new NotFoundException();
        }
        return admin.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody AdminLevel2 admin) {
        this.adminRepository.save(admin);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("{id}")
    void update(@PathVariable UUID id, @RequestBody AdminLevel2 admin1) {
        Optional<AdminLevel2> admin = this.adminRepository.findById(id);
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
