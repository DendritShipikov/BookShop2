package com.dendrit.bookshop.bookapi.security;

import com.dendrit.bookshop.common.data.UserData;
import com.dendrit.bookshop.common.jwt.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Value("${userapi.address}")
    private String USERAPI;

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        Long id = JwtUtil.getUserIdFromToken(token);
        LOGGER.debug("authenticate user with id = " + id);
        UserData userData = restTemplate.getForObject(USERAPI + "/bookshop/api/users/{id}", UserData.class, id);
        LOGGER.debug("userData = {id = " + userData.getId() + ", username = '" + userData.getUsername() + "', roles = " + userData.getRoles().toString() + "}");
        Set<SimpleGrantedAuthority> authorities =
                userData.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userData, null, authorities);
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }

}
