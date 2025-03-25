package com.kestats.api.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kestats.api.NotFoundException;
import com.kestats.api.models.AppUser;
import com.kestats.api.repository.AppUserRepository;
import com.kestats.api.services.AppUserService;

@RestController
@CrossOrigin
@RequestMapping("")
public class AppUserController {
    private final AppUserRepository userRepository;

    private final AppUserService userService;
    public AppUserController(AppUserRepository userRepository,AppUserService userService) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    void register(@RequestBody AppUser user) {
        this.userService.registerUser(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    String login(@RequestBody AppUser user) {
        return this.userService.verifyUser(user);
    }

    @GetMapping("/api/users/{id}")
    AppUser getUser(@RequestParam UUID id) {
        Optional<AppUser> user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        return user.get();
    }

    @PostMapping("/api/users/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    void update(@RequestBody AppUser user, @RequestParam UUID id) {
        Optional<AppUser> userData = this.userRepository.findById(id);
        if (userData.isEmpty()) {
            throw new NotFoundException();
        }
        this.userRepository.save(user);
    }
}
