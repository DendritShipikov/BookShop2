package com.dendrit.bookshop.internalauthapi.tests;

import com.dendrit.bookshop.internalauthapi.data.ProfileData;
import com.dendrit.bookshop.internalauthapi.entities.Authority;
import com.dendrit.bookshop.internalauthapi.entities.Profile;
import com.dendrit.bookshop.internalauthapi.exceptions.IncorrectPasswordException;
import com.dendrit.bookshop.internalauthapi.exceptions.ProfileAlreadyExistException;
import com.dendrit.bookshop.internalauthapi.exceptions.ProfileNotFoundException;
import com.dendrit.bookshop.internalauthapi.repositories.ProfileRepository;
import com.dendrit.bookshop.internalauthapi.services.AuthServiceImpl;
import com.dendrit.bookshop.internalauthapi.services.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

public class AuthServiceTest {

    ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);

    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    JwtService jwtService = Mockito.mock(JwtService.class);

    AuthServiceImpl authService;

    @BeforeEach
    public void setup() {
        authService = new AuthServiceImpl();
        authService.setJwtService(jwtService);
        authService.setPasswordEncoder(passwordEncoder);
        authService.setProfileRepository(profileRepository);
    }

    @Test
    public void generateTokenTest_IfNotFound() {
        Mockito.when(profileRepository.findByName("name")).thenReturn(Optional.empty());
        Assertions.assertThrows(ProfileNotFoundException.class, () -> authService.generateToken("name", "password"));
    }

    @Test
    public void generateTokenTest_IfIncorrectPassword() {
        Profile profile = new Profile();
        profile.setPassword("1");
        profile.setName("name");
        Mockito.when(profileRepository.findByName("name")).thenReturn(Optional.of(profile));
        Mockito.when(passwordEncoder.matches("password", "1")).thenReturn(false);
        Assertions.assertThrows(IncorrectPasswordException.class, () -> authService.generateToken("name", "password"));
    }

    @Test
    public void generateTokenTest() throws IncorrectPasswordException {
        Profile profile = new Profile();
        profile.setPassword("1");
        profile.setName("name");
        profile.setId(1L);
        Mockito.when(profileRepository.findByName("name")).thenReturn(Optional.of(profile));
        Mockito.when(passwordEncoder.matches("password", "1")).thenReturn(true);
        authService.generateToken("name", "password");
        Mockito.verify(jwtService).generateToken(1L);
    }

    @Test
    public void getProfileByIdTest_IfNotFound() {
        Mockito.when(profileRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ProfileNotFoundException.class, () -> authService.getProfileById(1L));
    }

    @Test
    public void getProfileByIdTest() {
        Profile profile = new Profile();
        profile.setName("name");
        profile.setId(1L);
        profile.setPassword("1");
        profile.setAuthorities(Set.of(Authority.NOTIFICATION_AUTHORITY));
        Mockito.when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        ProfileData profileData = new ProfileData();
        profileData.setAuthorities(Set.of(Authority.NOTIFICATION_AUTHORITY));
        profileData.setId(1L);
        profileData.setName("name");
        Assertions.assertEquals(profileData, authService.getProfileById(1L));
    }

    @Test
    public void registrationTest_IfExists() {
        Profile profile = new Profile();
        profile.setPassword("1");
        Mockito.when(profileRepository.findByName("name")).thenReturn(Optional.of(profile));
        Assertions.assertThrows(ProfileAlreadyExistException.class, () -> authService.registration("name", "password"));
    }

    @ParameterizedTest
    @CsvSource({
            "name1, password2, 1",
            "name2, password2, 2"
    })
    public void registrationTest(String name, String rawPassword, String encodedPassword) {
        Mockito.when(profileRepository.findByName(name)).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        authService.registration(name, rawPassword);
        Profile profile = new Profile();
        profile.setName(name);
        profile.setPassword(encodedPassword);
        profile.setAuthorities(Set.of());
        Mockito.verify(profileRepository).save(profile);
        Mockito.verify(passwordEncoder).encode(rawPassword);
    }

}
