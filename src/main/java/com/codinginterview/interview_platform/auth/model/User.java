package com.codinginterview.interview_platform.auth.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Table(name="users")
@Entity
@Data
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Auto-incrementing primary key
    @Setter(AccessLevel.NONE) // The id will not have a setter function
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password; // This will store the hashed password

    @Column(name="registered_on", nullable=false)
    private LocalDateTime registeredOn;

    @Column(nullable=false)
    private boolean verified;

    public User(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.password = passwordHash;
        this.verified = false;
        this.registeredOn = LocalDateTime.now();
    }

    public void setVerified() {
        this.verified = true;
    }
}
