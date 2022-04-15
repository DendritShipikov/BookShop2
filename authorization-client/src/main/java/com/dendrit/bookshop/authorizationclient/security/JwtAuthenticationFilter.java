package com.dendrit.bookshop.authorizationclient.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private static final String BEARER_PREFIX = "Bearer ";

    private AuthenticationManager authenticationManager;

    private AuthenticationEntryPoint authenticationEntryPoint = new JwtAuthenticationEntryPoint();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            LOGGER.debug("request has bearer authentication");
            String token = header.substring(BEARER_PREFIX.length());
            Authentication authentication = new JwtAuthenticationToken(null, token, null);
            try {
                authentication = authenticationManager.authenticate(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.debug("Set SecurityContextHolder to " + authentication);
            } catch (AuthenticationException exception) {
                SecurityContextHolder.clearContext();
                LOGGER.debug("Fail to process authentication request", exception);
                authenticationEntryPoint.commence(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
