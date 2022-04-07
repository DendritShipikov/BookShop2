package com.dendrit.bookshop.userapi.services;

import com.dendrit.bookshop.userapi.exceptions.BadJwtException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.key}")
    private String key;

    @Override
    public String generateToken(Long id) {
        Date now  = new Date();
        Date expiration = new Date(now.getTime() + 1000000);
        byte[] bytes = Base64.getDecoder().decode(key);
        KeyFactory keyFactory = null;
        PrivateKey privateKey = null;
        try {
            keyFactory = KeyFactory.getInstance("EC");
            privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.ES256, privateKey)
                .compact();
    }

    @Override
    public Long getUserIdFromToken(String token) {
        try {
            byte[] bytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
            String subject = Jwts.parser()
                    .setSigningKey(privateKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return Long.valueOf(subject);
        } catch (MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException |
                IllegalArgumentException |
                SignatureException |
                NoSuchAlgorithmException |
                InvalidKeySpecException ex) {
            throw new BadJwtException("Bad JWT", ex);
        }
    }

}
