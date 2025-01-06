package com.tinqin.library.book.rest.config;


import com.tinqin.library.book.domain.clients.AuthClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${authentication.active}")
    private Boolean authenticationEnabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!authenticationEnabled) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestUrl = request.getRequestURI();
        String[] whitelistedEndpoints = Endpoints.WHITELISTED_ENDPOINTS;

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        boolean isWhitelisted = Arrays
                .stream(whitelistedEndpoints)
                .anyMatch(endpoint -> antPathMatcher.match(endpoint, requestUrl));


        if (isWhitelisted) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).orElse("");

        boolean isAdminRequired = false;
        if (request.getMethod().equals("POST")) {
            String[] adminEndpoints = Endpoints.ADMINENDPOINTSPOST;
            isAdminRequired = Arrays
                    .stream(adminEndpoints)
                    .anyMatch(endpoint -> antPathMatcher.match(endpoint, requestUrl));

        }
        String role = "";
        if (isAdminRequired) {role="admin";}


        try {
            authClient.verify(authHeader,role);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
