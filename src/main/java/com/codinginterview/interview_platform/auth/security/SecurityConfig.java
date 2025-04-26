package com.codinginterview.interview_platform.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/", "/login**", "/static/**").permitAll()
                            .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                                  .loginPage("/login")); // This enables OAuth2 login
        return http.build();
    }
}