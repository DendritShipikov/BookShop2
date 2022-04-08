package com.dendrit.bookshop.internalauthapi.services;

import com.dendrit.bookshop.internalauthapi.data.ProfileData;
import com.dendrit.bookshop.internalauthapi.exceptions.IncorrectPasswordException;

public interface AuthService {

    String generateToken(String name, String password) throws IncorrectPasswordException;

    ProfileData getProfileById(Long id);

    void registration(String name, String password);

}
