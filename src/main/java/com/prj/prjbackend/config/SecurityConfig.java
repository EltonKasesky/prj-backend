package com.prj.prjbackend.config;

import com.prj.prjbackend.middleware.security.SecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        //Users
                        .requestMatchers(HttpMethod.GET, "/users/me").hasAnyRole("ADMIN", "AUTHOR", "COLLECTOR")
                        .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/me").hasAnyRole("ADMIN", "AUTHOR", "COLLECTOR")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("ADMIN", "AUTHOR", "COLLECTOR")
                        //Profiles
                        .requestMatchers(HttpMethod.GET, "/profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/profiles/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
