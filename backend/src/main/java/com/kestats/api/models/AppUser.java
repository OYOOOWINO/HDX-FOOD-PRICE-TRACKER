package com.kestats.api.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.kestats.api.utils.GeneratedId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
   @GeneratedId
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    String name;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now(); // Set during creation
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(); // Update before every update operation
    }
}
