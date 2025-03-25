package com.kestats.api.configs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kestats.api.services.AppUserDetailsService;
import com.kestats.api.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            System.out.println("JWT FILTEr");
        // extract Bearer token
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        System.out.println("GOT HERE 001");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("GOT HERE 009");
            token = authHeader.substring(7);
            System.out.println(token);
            username = jwtService.extractUsername(token);
        }

        System.out.println("GOT HERE 0");

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // create authentication object
            System.out.println("GOT HERE 1");
            UserDetails userDetails = context.getBean(AppUserDetailsService.class).loadUserByUsername(username);
            Boolean isValidToken =  jwtService.validateToken(token, userDetails.getUsername());
            System.out.println("isValidToken: "+ isValidToken);
            if (isValidToken) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("GOT HERE 2" +  userDetails.getUsername());
            }
        }
        filterChain.doFilter(request, response);
    }

}
