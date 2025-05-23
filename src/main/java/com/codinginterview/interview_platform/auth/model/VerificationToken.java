package com.codinginterview.interview_platform.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="verification_tokens")
@Data
public class VerificationToken {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String token;
    
    @OneToOne(targetEntity=User.class, fetch=FetchType.EAGER)
    @JoinColumn(nullable=false, name="user_id")
    private User user;
    
    @Column(nullable=false)
    private LocalDateTime expiryDate;
    
    public VerificationToken() {
        this.expiryDate = LocalDateTime.now().plusHours(3);
    }

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusHours(3);
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
               "id=" + id +
               ", token='" + token + '\'' +
               ", userEmail='" + (user != null ? user.getEmail() : "null") + '\'' +
               ", expiryDate=" + expiryDate +
               '}';
    }
}
