package com.springboot.my_skills_list.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SpringSecurityConfiguration - configures the Spring Security settings for the application.
 *
 * It defines:
 *  - How users are authenticated (here: in-memory)
 *  - Password encoding (using BCrypt)
 *  - Authorization rules for HTTP requests
 *  - Login form handling and basic web security features
 *
 */

@Configuration
public class SpringSecurityConfiguration {

    // User Details Management - Defines an in-memory user store with predefined users.
    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {

        // Create two demo users with usernames and passwords
        UserDetails userDetails1 = createNewUser("Lia", "pass");
        UserDetails userDetails2 = createNewUser("Leo", "pass");

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    // Helper method to create a user with encoded password.
    private UserDetails createNewUser(String username, String password) {

        // PasswordEncoder lambda to encrypt plain text passwords
        Function<String, String> passwordEncoder =
                input -> passwordEncoder().encode(input);

        // Build a new user with the provided username and password
        // Assign roles USER and ADMIN
        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();

        return userDetails;
    }

    // Password Encoding - Defines a BCryptPasswordEncoder bean to securely hash passwords.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // HTTP Security Configuration - Configures how HTTP requests are secured.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Require authentication for every request
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        // Enable Spring Security’s default login form
        http.formLogin(withDefaults());

        // Disable CSRF (useful for non-browser clients or H2 console)
        http.csrf().disable();

        // Allow frames (needed for embedded H2 database console)
        http.headers().frameOptions().disable();

        // Build and return the configured security filter chain
        return http.build();
    }
}
