package com.codinginterview.interview_platform.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codinginterview.interview_platform.auth.service.EmailService;
import com.codinginterview.interview_platform.auth.service.EmailValidatorService;
import com.codinginterview.interview_platform.auth.service.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;
    private final EmailValidatorService emailValidatorService;
    private final EmailService emailService;

    @Autowired
    public SignupController(UserService userService, EmailValidatorService emailValidatorService, EmailService emailService) {
        this.userService = userService;
        this.emailValidatorService = emailValidatorService;
        this.emailService = emailService;
    }

    /**
     * Handles GET requests to display the signup form.
     * Thymeleaf automatically handles CSRF token injection; no need to manually add to Model.
     */
    @GetMapping
    public String getSignupForm(Model model, CsrfToken csrfToken) {
        model.addAttribute("_csrf", csrfToken);
        return "signup";
    }

    /**
     * Handles POST requests for user registration.
     * Performs server-side validation and redirects or adds errors to the model.
     */
    @PostMapping("/register")
    public String registerUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model, // Used to add attributes for the current request (e.g., validation errors)
            RedirectAttributes redirectAttributes, // Used to add attributes for a redirect (e.g., success messages)
            CsrfToken csrfToken) { 
            
        
        model.addAttribute("_csrf", csrfToken);
        // Populate model with current values for retention
        model.addAttribute("name", name);
        model.addAttribute("email", email);

        // Flag to track if any validation errors occurred
        boolean hasErrors = findValidationErrors(name, email, password, confirmPassword, model);

        // If there are any validation errors, add the user's input back to the model and return to the signup page.
        if (hasErrors) {
            System.out.println("Validation errors encountered. Returning to signup form.");
            return "signup"; // Stay on the signup page to display errors
        }

        // 2. If validation passes, proceed with user registration
        try {
            userService.storeUnverifiedUser(name, email, password);
            return "redirect:/verification/pending"; // Redirect to the pending page

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            System.err.println("Signup error (IllegalArgumentException): " + e.getMessage());
            return "signup"; // Stay on signup page on error
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred during signup. Please try again."); // General error
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            System.err.println("Unexpected signup error: " + e.getMessage());
            e.printStackTrace();
            return "signup"; // Stay on signup page on error
        }
    }

    private boolean findValidationErrors(String name, String email, String password, String confirmPassword, Model model) {
        boolean hasErrors = false;

        // 1. Server-side validation checks
        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("errorName", "Name is required.");
            hasErrors = true;
        }

        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("errorEmail", "Email is required.");
            hasErrors = true;
        } else if (!emailValidatorService.isValidEmail(email)) {
            model.addAttribute("errorEmail", "Invalid email format.");
            hasErrors = true;
        }

        boolean hasPasswordErrors = false;

        if (password == null || password.isEmpty()) {
            model.addAttribute("errorPassword", "Password is required.");
            hasErrors = true;
            hasPasswordErrors = true;
        } else if (password.length() < 8) {
            model.addAttribute("errorPassword", "Password must be at least 8 characters long.");
            hasErrors = true;
            hasPasswordErrors = true;
        }
    
        // 2. Validate 'confirmPassword' field
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            model.addAttribute("errorConfirmPassword", "Confirm Password is required.");
            hasErrors = true;
            hasPasswordErrors = true;
        }
    
        if (!hasPasswordErrors && !password.equals(confirmPassword)) {
            model.addAttribute("errorConfirmPassword", "Passwords do not match.");
            hasErrors = true;
        }

        return hasErrors;
    }
}