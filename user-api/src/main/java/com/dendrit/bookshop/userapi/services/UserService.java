package com.dendrit.bookshop.userapi.services;

import com.dendrit.bookshop.common.data.UserData;
import com.dendrit.bookshop.common.data.UserLoginForm;
import com.dendrit.bookshop.common.data.UserRegistrationForm;
import com.dendrit.bookshop.userapi.exceptions.IncorrectPasswordException;

public interface UserService {

    UserData registration(UserRegistrationForm registrationForm);

    String generateToken(UserLoginForm loginForm) throws IncorrectPasswordException;

    UserData getUserById(Long id);

}
