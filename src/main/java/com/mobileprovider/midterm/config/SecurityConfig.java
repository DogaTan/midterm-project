package com.mobileprovider.midterm.config;

import com.mobileprovider.midterm.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // JWT gerektirmeyen açık endpointler
                .requestMatchers(
                    "/api/v1/auth/**",              // login
                    "/api/v1/subscribers",           // abone oluşturma (POST)
                    "/api/v1/subscribers/**",        // abone sorgulama (GET)
                    "/api/v1/bill/pay",              // fatura ödeme (POST)
                    "/api/v1/bill/history",          // fatura geçmişi (GET)
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/api-docs/**"
                ).permitAll()

                // JWT gerektiren korumalı endpointler
                .requestMatchers(
                    "/api/v1/usage/**",              // kullanım ekleme (POST)
                    "/api/v1/bill/calculate",        // fatura hesaplama (GET)
                    "/api/v1/bill/detailed"          // detaylı fatura listeleme (GET)
                ).authenticated()

                // Diğer tüm istekler için de JWT gereksin
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
