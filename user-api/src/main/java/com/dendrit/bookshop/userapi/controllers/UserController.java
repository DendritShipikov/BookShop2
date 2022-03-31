package com.dendrit.bookshop.userapi.controllers;

import com.dendrit.bookshop.common.data.UserData;
import com.dendrit.bookshop.userapi.data.UserLoginForm;
import com.dendrit.bookshop.userapi.data.UserRegistrationForm;
import com.dendrit.bookshop.userapi.exceptions.IncorrectPasswordException;
import com.dendrit.bookshop.userapi.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return user"),
            @ApiResponse(code = 404, message = "User with such username does not exist")
    })
    @GetMapping("/{id}")
    public UserData getById(@Parameter(description = "Id of user to be selected") @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User registration"),
            @ApiResponse(code = 422, message = "User with such username is already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<UserData> register(
            @Parameter(description = "Registration form") @RequestBody UserRegistrationForm registrationForm) {
        UserData userData = userService.registration(registrationForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(userData);
    }

    @Operation(summary = "Get token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return page of books"),
            @ApiResponse(code = 401, message = "Incorrect password"),
            @ApiResponse(code = 404, message = "User with such username does not exist")
    })
    @PostMapping("/token")
    public ResponseEntity<String> generateToken(
            @Parameter(description = "Username-password pair")@RequestBody UserLoginForm loginForm) throws IncorrectPasswordException {
        String token = userService.generateToken(loginForm);
        return ResponseEntity.ok().body(token);
    }

}
