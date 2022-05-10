package com.dendrit.bookshop.core.usersclient.security;

import com.dendrit.bookshop.core.security.AuthenticationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bookshop.user-api.authentication")
public class UsersAuthenticationProperties implements AuthenticationProperties {

    private String publicKey;

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
