package com.dendrit.bookshop.userapi.tests;

import com.dendrit.bookshop.userapi.data.Role;
import com.dendrit.bookshop.userapi.data.UserData;
import com.dendrit.bookshop.userapi.data.UserLoginForm;
import com.dendrit.bookshop.userapi.data.UserRegistrationForm;
import com.dendrit.bookshop.userapi.entities.User;
import com.dendrit.bookshop.userapi.exceptions.IncorrectPasswordException;
import com.dendrit.bookshop.userapi.exceptions.UserAlreadyExistException;
import com.dendrit.bookshop.userapi.exceptions.UserNotFoundException;
import com.dendrit.bookshop.userapi.repositories.UserRepository;
import com.dendrit.bookshop.userapi.services.JwtService;
import com.dendrit.bookshop.userapi.services.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

public class UserServiceImplTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);

    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    JwtService jwtService = Mockito.mock(JwtService.class);

    UserServiceImpl userService;

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl();
        userService.setUserRepository(userRepository);
        userService.setPasswordEncoder(passwordEncoder);
        userService.setJwtService(jwtService);
    }

    @Test
    public void getUserByIdTest() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user1()));
        UserData userData = userService.getUserById(1L);
        Assertions.assertEquals(userData.getId(), 1L);
        Assertions.assertEquals(userData.getUsername(), "user1");
        Assertions.assertEquals(userData.getRoles(), Set.of(Role.USER));
        Mockito.verify(userRepository).findById(1L);
    }

    @Test
    public void getUserByIdTest_IfNotFound() {
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(3L));
        Mockito.verify(userRepository).findById(3L);
    }

    @Test
    public void registrationTest_IfExist() {
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user1()));
        Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.registration(new UserRegistrationForm("user1", "1", Set.of(Role.USER))));
        Mockito.verify(userRepository).findByUsername("user1");
    }

    @Test
    public void registrationTest() {
        Mockito.when(userRepository.findByUsername("user2")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user2());
        Mockito.when(passwordEncoder.encode("2")).thenReturn("2e");
        UserData userData = userService.registration(new UserRegistrationForm("user2", "2", Set.of(Role.USER)));
        Assertions.assertEquals(userData.getId(), 2L);
        Assertions.assertEquals(userData.getUsername(), "user2");
        Assertions.assertEquals(userData.getRoles(), Set.of(Role.USER));
        Mockito.verify(userRepository).findByUsername("user2");
        Mockito.verify(passwordEncoder).encode("2");
    }

    @Test
    public void generateTokenTest() throws IncorrectPasswordException {
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user1()));
        Mockito.when(passwordEncoder.matches("1", "1e")).thenReturn(true);
        Mockito.when(jwtService.generateToken(1L)).thenReturn("tok");
        Assertions.assertEquals(userService.generateToken(new UserLoginForm("user1", "1")), "tok");
        Mockito.verify(userRepository).findByUsername("user1");
        Mockito.verify(passwordEncoder).matches("1", "1e");
        Mockito.verify(jwtService).generateToken(1L);
    }

    @Test
    public void generateTokenTest_IfIncorrectPassword() {
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user1()));
        Mockito.when(passwordEncoder.matches("2", "1e")).thenReturn(false);
        Assertions.assertThrows(IncorrectPasswordException.class, () -> userService.generateToken(new UserLoginForm("user1", "2")));
        Mockito.verify(userRepository).findByUsername("user1");
        Mockito.verify(passwordEncoder).matches("2", "1e");
    }

    @Test
    public void generateTokenTest_IfNotFound() {
        Mockito.when(userRepository.findByUsername("user3")).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.generateToken(new UserLoginForm("user3", "3")));
        Mockito.verify(userRepository).findByUsername("user3");
    }

    private User user1() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("1e");
        user.setRoles(Set.of(Role.USER));
        return user;
    }

    private User user2() {
        User user = new User();
        user.setId(2L);
        user.setUsername("user2");
        user.setPassword("2e");
        user.setRoles(Set.of(Role.USER));
        return user;
    }

}
