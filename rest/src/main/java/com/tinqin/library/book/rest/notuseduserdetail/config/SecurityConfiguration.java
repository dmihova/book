package com.tinqin.library.book.rest.notuseduserdetail.config;

import com.tinqin.library.book.rest.config.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.tinqin.library.book.rest.config.WhitelistedEndpoints.WHITELISTED_ENDPOINTS;

//@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return   http
                .authorizeHttpRequests(customizer -> customizer
                                      .requestMatchers(WHITELISTED_ENDPOINTS).permitAll()
                                     .anyRequest().authenticated())
                        .csrf(AbstractHttpConfigurer::disable)
                        .cors(AbstractHttpConfigurer::disable)
                .build();

    }
}
