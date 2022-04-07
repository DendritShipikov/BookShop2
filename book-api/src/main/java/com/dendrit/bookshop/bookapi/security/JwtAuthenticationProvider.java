package com.dendrit.bookshop.bookapi.security;

import com.dendrit.bookshop.bookapi.data.UserData;
import io.jsonwebtoken.*;
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

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Value("${userapi.address}")
    private String userApiBaseAddress;

    @Value("${jwt.key}")
    private String key;

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        Long id = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(bytes));
            String subject = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            id = Long.valueOf(subject);
            LOGGER.info("jwt is good");
        } catch (MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException |
                IllegalArgumentException |
                SignatureException |
                NoSuchAlgorithmException |
                InvalidKeySpecException exception) {
            throw new BadCredentialsException("Bad JWT", exception);
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
