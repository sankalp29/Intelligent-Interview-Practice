package com.codinginterview.interview_platform.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codinginterview.interview_platform.auth.exceptions.EmailAlreadyExistsException;
import com.codinginterview.interview_platform.auth.model.User;
import com.codinginterview.interview_platform.auth.model.VerificationToken;
import com.codinginterview.interview_platform.auth.repository.UserRepository;
import com.codinginterview.interview_platform.auth.repository.VerificationTokenRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, 
                       EmailService emailService, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public void storeUnverifiedUser(String name, String email, String plainPassword) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        // Step 1 : Create and save the User
        User user = new User(name, email, passwordEncoder.encode(plainPassword));
        System.out.println("User created : " + user);
        userRepository.save(user);
        System.out.println("User saved");

        // Step 2 : Generate and save a Verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);

        // Step 3 : Construct the verification link
        String verificationLink = "http://localhost:8080/verification/confirm?token=" + token;

        // 4. Send the verification email
        emailService.sendVerificationEmail(email, name, verificationLink);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean confirmUserAccount(String token) {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            System.out.println("Verification token not found");
            return false;
        }

        VerificationToken verificationToken = optionalToken.get();
        User user = verificationToken.getUser();

        if (user.isVerified()) {
            System.out.println("User is already enabled");
            return true;
        }
        
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            System.out.println("Verification token expired for user: " + user.getEmail());
            verificationTokenRepository.delete(verificationToken);
            return false;
        }

        // The user is verified
        user.setVerified();
        userRepository.save(user);

        // Delete the token after successful verification
        verificationTokenRepository.delete(verificationToken);
        System.out.println("User account verified successfully: " + user.getEmail());
        return true;
    }
}
