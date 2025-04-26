package com.codinginterview.interview_platform.auth.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.codinginterview.interview_platform.auth.model.User;

@Repository
public class UserRepository {
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    
    public void saveUser(User user) {
        String key = user.getEmail().toLowerCase();
        redisTemplate.opsForValue().set(key, user);
    }

    public User getUserByEmail(String email) {
        return redisTemplate.opsForValue().get(email.toLowerCase());
    }
    
    public void deleteToken(String email) {
        User user = getUserByEmail(email);
        if (user != null) {
            user.setJwtToken(null);
            saveUser(user);
        }
    }

    public void deleteUser(User user) {
        redisTemplate.delete(user.getEmail().toLowerCase());
    }
}