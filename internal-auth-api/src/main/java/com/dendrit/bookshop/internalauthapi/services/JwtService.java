package com.dendrit.bookshop.internalauthapi.services;

public interface JwtService {

    String generateToken(Long id);

}
