package com.kestats.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kestats.api.UserExistsException;
import com.kestats.api.models.AppUser;
import com.kestats.api.repository.AppUserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AppUserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private final AppUserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public AppUserService(AppUserRepository userRepository, JwtService jwtService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public void registerUser(AppUser user) {
        // check if user exists
        AppUser existingUser = this.userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new UserExistsException();
        }
        AppUser newUser = new AppUser(user.getUsername(), encoder.encode(user.getPassword()));
        this.userRepository.save(newUser);
    }

    public String verifyUser(AppUser user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return this.jwtService.generateToken(user.getUsername());

        }
        throw new RuntimeException("Authentication Failed");

    }
}
