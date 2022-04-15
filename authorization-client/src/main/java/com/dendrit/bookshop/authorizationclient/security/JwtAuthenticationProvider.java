package com.dendrit.bookshop.authorizationclient.security;

import com.dendrit.bookshop.authorizationclient.client.AuthorizationClient;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private AuthorizationClient authorizationClient;

    private JwtAuthenticationProviderProperties providerProperties;

    @Autowired
    public void setAuthorizationClient(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    @Autowired
    public void setProviderProperties(JwtAuthenticationProviderProperties providerProperties) {
        this.providerProperties = providerProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        Long id = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(providerProperties.getPublicKey());
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
        AuthorizationData authorizationData = authorizationClient.getAuthorities(id);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(authorizationData, null, authorizationData.getAuthorities());
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }

}
