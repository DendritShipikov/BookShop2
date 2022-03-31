package com.dendrit.bookshop.userapi.controllers;

import com.dendrit.bookshop.common.data.UserData;
import com.dendrit.bookshop.common.data.UserLoginForm;
import com.dendrit.bookshop.common.data.UserRegistrationForm;
import com.dendrit.bookshop.userapi.exceptions.IncorrectPasswordException;
import com.dendrit.bookshop.userapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserData getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<UserData> register(@RequestBody UserRegistrationForm registrationForm) {
        UserData userData = userService.registration(registrationForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(userData);
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody UserLoginForm loginForm) throws IncorrectPasswordException {
        String token = userService.generateToken(loginForm);
        return ResponseEntity.ok().body(token);
    }

}
