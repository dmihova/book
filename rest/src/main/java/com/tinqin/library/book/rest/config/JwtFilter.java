package com.tinqin.library.book.rest.config;


import com.tinqin.library.book.domain.clients.AuthClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final AuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUrl = request.getRequestURI();
        String[] whitelistedEndpoints = WhitelistedEndpoints.WHITELISTED_ENDPOINTS;

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        boolean isWhitelisted = Arrays
                .stream(whitelistedEndpoints)
                .anyMatch(endpoint -> antPathMatcher.match(endpoint, requestUrl));

        if (isWhitelisted) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String authHeader = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).orElse("");
            authClient.validate(authHeader);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
