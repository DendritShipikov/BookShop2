package com.dendrit.bookshop.userapi.services;

import com.dendrit.bookshop.userapi.data.UserData;
import com.dendrit.bookshop.userapi.data.UserLoginForm;
import com.dendrit.bookshop.userapi.data.UserRegistrationForm;
import com.dendrit.bookshop.userapi.entities.User;
import com.dendrit.bookshop.userapi.exceptions.IncorrectPasswordException;
import com.dendrit.bookshop.userapi.exceptions.UserAlreadyExistException;
import com.dendrit.bookshop.userapi.exceptions.UserNotFoundException;
import com.dendrit.bookshop.userapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public UserData registration(UserRegistrationForm registrationForm) {
        userRepository.findByUsername(registrationForm.getUsername()).ifPresent(user -> {
                    throw new UserAlreadyExistException("User with username '" + registrationForm.getUsername() + "' already exist");
        });
        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        user.setRoles(registrationForm.getRoles());
        user = userRepository.save(user);
        return new UserData(user.getId(), user.getUsername(), user.getRoles());
    }

    @Override
    @Transactional
    public String generateToken(UserLoginForm loginForm) throws IncorrectPasswordException {
        User user = userRepository.findByUsername(loginForm.getUsername()).orElseThrow(() ->
                new UserNotFoundException("User with username '" + loginForm.getUsername() + "' not found"));
        if (!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password");
        }
        return jwtService.generateToken(user.getId());
    }

    @Override
    @Transactional
    public UserData getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + id + " not found"));
        return new UserData(user.getId(), user.getUsername(), user.getRoles());
    }
}
