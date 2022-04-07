package com.dendrit.bookshop.internalauthapi.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.key}")
    private String key;

    @Override
    public String generateToken(Long id) {
        try {
            Date now = new Date();
            Date expiration = new Date(now.getTime() + 1000000);
            byte[] bytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
            return Jwts.builder()
                    .setSubject(id.toString())
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .signWith(SignatureAlgorithm.ES256, privateKey)
                    .compact();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Exception when generating JWT token", e);
        }
    }

}
