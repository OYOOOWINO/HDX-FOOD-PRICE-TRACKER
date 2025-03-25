package com.kestats.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kestats.api.models.AppUser;
import com.kestats.api.repository.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private final AppUserRepository userRepository;

    AppUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading User By Username: " + username);
        AppUser user = this.userRepository.findByUsername(username);
        System.out.println("User: " + user);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserPrincipal(user);
    }

}

