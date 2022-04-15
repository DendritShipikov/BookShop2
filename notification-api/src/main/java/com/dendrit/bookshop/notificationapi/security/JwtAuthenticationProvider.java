package com.dendrit.bookshop.notificationapi.security;

import com.dendrit.bookshop.authorizationclient.client.AuthorizationClient;
import com.dendrit.bookshop.notificationapi.data.ProfileData;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Value("${jwt.key}")
    private String key;

    private AuthorizationClient authorizationClient;

    @Autowired
    public void setAuthorizationClient(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
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
        ProfileData profileData = authorizationClient.getAuthorities(id, ProfileData.class);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(profileData, null, profileData.getAuthorities());
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }

}
