package com.codinginterview.interview_platform.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codinginterview.interview_platform.auth.exceptions.EmailAlreadyExistsException;
import com.codinginterview.interview_platform.auth.model.User;
import com.codinginterview.interview_platform.auth.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void storeUnverifiedUser(String name, String email, String plainPassword) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User(name, email, passwordEncoder.encode(plainPassword));
        System.out.println("User created : " + user);
        userRepository.save(user);
        System.out.println("User saved");
    }
}
