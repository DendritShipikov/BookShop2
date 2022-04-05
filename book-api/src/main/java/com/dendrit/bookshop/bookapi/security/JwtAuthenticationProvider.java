package com.dendrit.bookshop.bookapi.security;

import com.dendrit.bookshop.bookapi.data.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Value("${userapi.address}")
    private String userApiBaseAddress;

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        Long id;
        try {
            id = restTemplate.getForObject(userApiBaseAddress + "/bookshop/api/users/validate?token=" + token, Long.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) throw new BadCredentialsException("Bad JWT");
            throw exception;
        }
        UserData userData = restTemplate.getForObject(userApiBaseAddress + "/bookshop/api/users/{id}", UserData.class, id);
        LOGGER.info("userData = {id = " + userData.getId() + ", username = '" + userData.getUsername() + "', roles = "
                + userData.getRoles().stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toSet()) + "}");
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userData, null, userData.getRoles());
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }

}
