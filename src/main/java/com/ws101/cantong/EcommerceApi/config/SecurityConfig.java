package com.ws101.cantong.EcommerceApi.config;

import com.ws101.cantong.EcommerceApi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Password encoder bean for hashing passwords
     * Uses BCrypt - a strong and widely recommended password hashing algorithm
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication provider that uses our CustomUserDetailsService
     * and PasswordEncoder for authentication
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Authentication manager bean - required for authentication
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Security filter chain configuration
     * Defines which endpoints are public, protected, and security settings
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        // CSRF Token Repository - stores CSRF token in cookie for JavaScript access
        CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        
        // Handler for CSRF token
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName(null);
        
        http
            // ===== CSRF CONFIGURATION =====
            // Keep CSRF enabled for form submissions (crucial for session security)
            .csrf(csrf -> csrf
                .csrfTokenRepository(csrfTokenRepository)
                .csrfTokenRequestHandler(requestHandler)
            )
            
            // ===== AUTHORIZATION RULES =====
            .authorizeHttpRequests(authz -> authz
                // PUBLIC ENDPOINTS - No authentication required
                .requestMatchers("/", "/home", "/login", "/register").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()  // Register and login endpoints
                .requestMatchers("/api/v1/products").permitAll()  // GET products - public
                .requestMatchers("/api/v1/products/**").permitAll()  // View single product - public
                .requestMatchers("/h2-console/**").permitAll()  // H2 database console (remove in production)
                
                // PROTECTED ENDPOINTS - Authentication required
                .requestMatchers("/api/v1/orders/**").authenticated()  // Order endpoints require login
                .requestMatchers("/api/v1/cart/**").authenticated()    // Cart endpoints require login
                .requestMatchers("/api/v1/profile/**").authenticated() // Profile endpoints require login
                .requestMatchers("/api/v1/checkout/**").authenticated() // Checkout requires login
                
                // ADMIN ONLY ENDPOINTS - Will also use @PreAuthorize in controllers
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/products/delete/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/products/create/**").hasAnyRole("ADMIN", "SELLER")
                
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            
            // ===== FORM LOGIN CONFIGURATION =====
            .formLogin(form -> form
                .loginPage("/login")  // Custom login page
                .loginProcessingUrl("/api/v1/auth/login")  // URL to submit login form
                .defaultSuccessUrl("/api/v1/products", true)  // Redirect after successful login
                .failureUrl("/login?error=true")  // Redirect after failed login
                .permitAll()  // Allow everyone to access login page
            )
            
            // ===== LOGOUT CONFIGURATION =====
            .logout(logout -> logout
                .logoutUrl("/api/v1/auth/logout")  // URL to trigger logout
                .logoutSuccessUrl("/login?logout=true")  // Redirect after logout
                .invalidateHttpSession(true)  // Invalidate session
                .clearAuthentication(true)  // Clear authentication
                .deleteCookies("JSESSIONID")  // Delete session cookie
                .permitAll()
            )
            
            // ===== SESSION MANAGEMENT =====
            // Ensure sessions are created upon successful login
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Create session if needed
                .maximumSessions(1)  // Allow only one session per user
                .maxSessionsPreventsLogin(false)  // Don't prevent login, invalidate old session
                .expiredUrl("/login?expired=true")  // Redirect when session expires
            )
            
            // ===== REMEMBER ME (Optional - keeps user logged in) =====
            .rememberMe(remember -> remember
                .key("uniqueAndSecretKey")
                .tokenValiditySeconds(86400)  // 24 hours
                .userDetailsService(userDetailsService)
            );
        
        // Disable frame options for H2 console (remove in production)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        
        return http.build();
    }
}