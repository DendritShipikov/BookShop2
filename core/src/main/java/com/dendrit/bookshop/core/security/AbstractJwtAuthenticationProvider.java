package com.dendrit.bookshop.core.security;

import com.dendrit.bookshop.core.security.data.AuthorizationData;
import com.dendrit.bookshop.core.security.data.JwtAuthenticationToken;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public abstract class AbstractJwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJwtAuthenticationProvider.class);

    public abstract AuthorizationDataProvider getAuthorizationDataProvider();

    public abstract AuthenticationProperties getAuthenticationProperties();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        Long id;
        try {
            byte[] bytes = Base64.getDecoder().decode(getAuthenticationProperties().getPublicKey());
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(bytes));
            String subject = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            id = Long.parseLong(subject);
        } catch (MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException |
                IllegalArgumentException |
                SignatureException |
                NoSuchAlgorithmException |
                InvalidKeySpecException exception) {
            throw new BadCredentialsException("Bad JWT", exception);
        }
        AuthorizationData authorizationData = getAuthorizationDataProvider().getAuthorizationData(id);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(authorizationData, null, authorizationData.getAuthorities());
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
