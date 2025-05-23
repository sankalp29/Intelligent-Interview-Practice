package com.codinginterview.interview_platform.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codinginterview.interview_platform.auth.service.UserService;

@Controller
@RequestMapping("/verification")
public class VerificationController {

    private final UserService userService;

    @Autowired
    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/pending")
    public String verificationPending() {
        return "verification_pending";
    }

    @GetMapping("/confirm")
    public String confirmAccount(
            @RequestParam("token") String token,
            Model model,
            RedirectAttributes redirectAttributes) {

        boolean isVerified = userService.confirmUserAccount(token);

        if (isVerified) {
            model.addAttribute("message", "Your account has been successfully verified!");
            model.addAttribute("success", true);
        } else {
            model.addAttribute("message", "Account verification failed or link expired. Please try registering again or request a new verification email.");
            model.addAttribute("success", false);
        }
        
        return "verification_status";
    }
}