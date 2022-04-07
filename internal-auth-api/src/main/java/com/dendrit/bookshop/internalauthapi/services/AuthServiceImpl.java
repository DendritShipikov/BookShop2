package com.dendrit.bookshop.internalauthapi.services;

import com.dendrit.bookshop.internalauthapi.data.ProfileData;
import com.dendrit.bookshop.internalauthapi.entities.Profile;
import com.dendrit.bookshop.internalauthapi.exceptions.IncorrectPasswordException;
import com.dendrit.bookshop.internalauthapi.exceptions.ProfileNotFoundException;
import com.dendrit.bookshop.internalauthapi.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private ProfileRepository profileRepository;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    @Autowired
    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
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
    public String generateToken(String name, String password) throws IncorrectPasswordException {
        Profile profile = profileRepository.findByName(name).orElseThrow(() -> new ProfileNotFoundException("No such profile exists"));
        if (!passwordEncoder.matches(password, profile.getPassword())) throw new IncorrectPasswordException();
        return jwtService.generateToken(profile.getId());
    }

    @Override
    public ProfileData getProfileById(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
        ProfileData profileData = new ProfileData();
        profileData.setName(profile.getName());
        profileData.setId(profile.getId());
        profileData.setAuthorities(profile.getAuthorities());
        return profileData;
    }
}
