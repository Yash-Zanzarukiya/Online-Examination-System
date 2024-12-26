package com.yashpz.examination_system.examination_system.security;

import com.yashpz.examination_system.examination_system.constants.Roles;
import com.yashpz.examination_system.examination_system.filters.SessionValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final SessionValidationFilter sessionValidationFilter;

    public SecurityConfig(JwtFilter jwtFilter, SessionValidationFilter sessionValidationFilter) {
        this.jwtFilter = jwtFilter;
        this.sessionValidationFilter = sessionValidationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/ws/exam/**","/exam-attempt/make-attempt").permitAll()
                        .requestMatchers("/categories/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/questions/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/mcq-questions/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/mcq-options/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/programming-questions/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/exam/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/exam-questions/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/schedule-exam/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH,"/schedule-exam/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/schedule-exam/**").hasRole(Roles.ADMIN.name())
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(sessionValidationFilter, JwtFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
