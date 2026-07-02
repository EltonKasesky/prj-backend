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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        //H2
                        .requestMatchers("/h2-console/**").permitAll()
                        //Swagger
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        //Login
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        //Statistics
                        .requestMatchers(HttpMethod.GET, "/statistics/**").permitAll()
                        //Users
                        .requestMatchers(HttpMethod.GET, "/users/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/me").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/me").authenticated()
                        //Profiles
                        .requestMatchers(HttpMethod.GET, "/profiles/users/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/profiles/**").hasRole("ADMIN")
                        //Admin
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
                        //Album
                        .requestMatchers(HttpMethod.GET, "/album/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/album").hasRole("AUTHOR")
                        //Stickers (catálogo gerenciado exclusivamente pelo Autor)
                        .requestMatchers(HttpMethod.GET, "/stickers/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/stickers").hasRole("AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "/stickers/**").hasRole("AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "/stickers/**").hasRole("AUTHOR")
                        //Others
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
