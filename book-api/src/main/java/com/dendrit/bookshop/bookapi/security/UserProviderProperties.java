package com.dendrit.bookshop.bookapi.security;

import com.dendrit.bookshop.authorizationclient.security.JwtAuthenticationProviderProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
public class UserProviderProperties implements JwtAuthenticationProviderProperties {

    private String publicKey;

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
