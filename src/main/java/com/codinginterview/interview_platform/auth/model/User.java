package com.codinginterview.interview_platform.auth.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash("users")
public class User implements Serializable {
    private String username;
    private String email;
    private String provider;
    private LocalDateTime signupDate;
    private Integer testsAttended;
    private String jwtToken;
}
