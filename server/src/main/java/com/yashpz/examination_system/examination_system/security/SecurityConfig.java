package com.yashpz.examination_system.examination_system.security;

import com.yashpz.examination_system.examination_system.constants.Roles;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserPrinciple userDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(UserPrinciple userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> request
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/exam/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/categories/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("//mcq-options/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/questions/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/colleges/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/colleges/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/colleges/**").hasRole(Roles.ADMIN.name())
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
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
