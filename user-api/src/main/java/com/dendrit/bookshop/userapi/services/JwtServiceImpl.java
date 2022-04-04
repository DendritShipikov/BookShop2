package com.dendrit.bookshop.userapi.services;

import com.dendrit.bookshop.userapi.exceptions.BadJwtException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Override
    public String generateToken(Long id) {
        Date now  = new Date();
        Date expiration = new Date(now.getTime() + 1000000);
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    @Override
    public Long getUserIdFromToken(String token) {
        try {
            String subject = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return Long.valueOf(subject);
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new BadJwtException("Bad JWT");
        }
    }

}
