package com.codinginterview.interview_platform.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codinginterview.interview_platform.auth.model.User;


@Repository
public interface  UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);   
}