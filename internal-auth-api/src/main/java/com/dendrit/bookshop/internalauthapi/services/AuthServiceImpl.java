package com.dendrit.bookshop.internalauthapi.services;

import com.dendrit.bookshop.internalauthapi.data.ProfileData;
import com.dendrit.bookshop.internalauthapi.entities.Profile;
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
    public String generateToken(String name, String password) {
        Profile profile = profileRepository.findByName(name).orElseThrow(() -> new RuntimeException("No such profile exists"));
        if (!passwordEncoder.matches(password, profile.getPassword())) throw new RuntimeException("Incorrect password");
        return jwtService.generateToken(profile.getId());
    }

    @Override
    public ProfileData getProfileById(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        ProfileData profileData = new ProfileData();
        profileData.setName(profile.getName());
        profileData.setId(profile.getId());
        return profileData;
    }
}
