package com.dendrit.bookshop.internalauthapi.services;

import com.dendrit.bookshop.internalauthapi.data.ProfileData;

public interface AuthService {

    String generateToken(String name, String password);

    ProfileData getProfileById(Long id);

}
