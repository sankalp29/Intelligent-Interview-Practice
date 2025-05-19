package com.codinginterview.interview_platform.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/signup", "/signup/register", "/css/**", "/js/**", "/verification/pending").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .successHandler(new SimpleUrlAuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        org.springframework.security.core.Authentication authentication)
                            throws java.io.IOException, ServletException {
                        // Check if the request was for /signup/register
                        if (request.getRequestURI().equals("/signup/register")) {
                            // If it was, send a redirect to /verification/pending
                            response.sendRedirect("/verification/pending");
                        } else {
                            // Otherwise, use the default success handling (for actual login)
                            super.onAuthenticationSuccess(request, response, authentication);
                        }
                    }
                })
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}