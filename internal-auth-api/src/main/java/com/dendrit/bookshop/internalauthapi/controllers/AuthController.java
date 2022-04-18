package com.dendrit.bookshop.internalauthapi.controllers;

import com.dendrit.bookshop.common.audit.aspects.CalculateTime;
import com.dendrit.bookshop.internalauthapi.data.ProfileData;
import com.dendrit.bookshop.internalauthapi.data.TokenRequest;
import com.dendrit.bookshop.internalauthapi.exceptions.IncorrectPasswordException;
import com.dendrit.bookshop.internalauthapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    @CalculateTime(name = "POST /token")
    public String generateToken(@RequestBody TokenRequest tokenRequest) throws IncorrectPasswordException {
        return authService.generateToken(tokenRequest.getName(), tokenRequest.getPassword());
    }

    @GetMapping("/profile/{id}")
    @CalculateTime(name = "GET /profile/{id}")
    public ProfileData getProfileById(@PathVariable Long id) {
        return authService.getProfileById(id);
    }

    @PostMapping("/profile")
    @CalculateTime(name = "POST /profile")
    public void registration(@RequestBody TokenRequest tokenRequest) {
        authService.registration(tokenRequest.getName(), tokenRequest.getPassword());
    }

}
