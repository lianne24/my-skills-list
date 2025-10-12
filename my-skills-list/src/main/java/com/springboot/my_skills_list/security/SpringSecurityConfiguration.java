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
 * SpringSecurityConfiguration
 * ---------------------------
 * This class configures the Spring Security settings for the application.
 *
 * It defines:
 *  - How users are authenticated (here: in-memory)
 *  - Password encoding (using BCrypt)
 *  - Authorization rules for HTTP requests
 *  - Login form handling and basic web security features
 *
 * Annotated with @Configuration so Spring can detect and load it at startup.
 */
@Configuration
public class SpringSecurityConfiguration {

    // ===========================================================
    // 🔹 1. USER DETAILS MANAGEMENT
    // ===========================================================

    /**
     * Defines an in-memory user store with predefined users.
     *
     * In a production app, users would normally come from a database
     * or LDAP, but for testing and demos, an in-memory setup is quick and easy.
     *
     * @return an InMemoryUserDetailsManager containing predefined users
     */
    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {

        // Create two demo users with usernames and passwords
        UserDetails userDetails1 = createNewUser("Lia", "pass");
        UserDetails userDetails2 = createNewUser("Leo", "pass");

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    /**
     * Helper method to create a user with encoded password.
     * 
     * @param username name of the user
     * @param password plaintext password to encode
     * @return a fully configured UserDetails object
     */
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

    // ===========================================================
    // 🔹 2. PASSWORD ENCODING
    // ===========================================================

    /**
     * Defines a BCryptPasswordEncoder bean to securely hash passwords.
     *
     * BCrypt is a strong one-way hashing algorithm that automatically
     * adds a salt, making passwords resistant to brute-force attacks.
     *
     * @return PasswordEncoder instance (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ===========================================================
    // 🔹 3. HTTP SECURITY CONFIGURATION
    // ===========================================================

    /**
     * Configures how HTTP requests are secured.
     * 
     * - Requires authentication for all endpoints
     * - Enables form-based login (auto-generated login page)
     * - Disables CSRF for simplicity (needed if using H2 or testing)
     * - Allows use of frames (for H2 console access)
     *
     * @param http the HttpSecurity builder
     * @return a configured SecurityFilterChain
     * @throws Exception in case of configuration errors
     */
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
