package com.dendrit.bookshop.userapi.services;

public interface JwtService {

    String generateToken(Long id);

    Long getUserIdFromToken(String token);
    
}