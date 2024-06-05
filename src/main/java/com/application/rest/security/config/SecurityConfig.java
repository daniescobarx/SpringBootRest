package com.application.rest.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // Admin
                    http.requestMatchers(HttpMethod.GET, "/api/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");

                    // Maker
                    http.requestMatchers(HttpMethod.GET, "/api/product/**").hasRole("MAKER");
                    http.requestMatchers(HttpMethod.POST, "/api/product/**").hasRole("MAKER");
                    http.requestMatchers(HttpMethod.PUT, "/api/product/**").hasRole("MAKER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/product/**").hasRole("MAKER");

                    // Customer 
                    http.requestMatchers(HttpMethod.GET, "/api/customer/find/**").hasRole("CUSTOMER");
                    http.requestMatchers(HttpMethod.PUT, "/api/customer/update/**").hasRole("CUSTOMER");
                    http.requestMatchers(HttpMethod.POST, "/api/order/**").hasRole("CUSTOMER");
                    http.requestMatchers(HttpMethod.GET, "/api/order/**").hasRole("CUSTOMER");
                        })
                .build();
    }
}
