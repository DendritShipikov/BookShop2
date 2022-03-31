package com.dendrit.bookshop.common.jwt.util;

import com.dendrit.bookshop.common.jwt.exceptions.BadJwtException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "3dug_970gf";

    public static String generateToken(Long id) {
        Date now  = new Date();
        Date expiration = new Date(now.getTime() + 1000000);
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static Long getUserIdFromToken(String token) {
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