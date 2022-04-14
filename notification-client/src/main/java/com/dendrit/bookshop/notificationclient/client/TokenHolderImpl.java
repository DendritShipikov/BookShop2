package com.dendrit.bookshop.notificationclient.client;

import com.dendrit.bookshop.common.absrtact.TokenHolder;
import com.dendrit.bookshop.notificationclient.model.TokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TokenHolderImpl implements TokenHolder {

    private String token;

    private AuthenticationClient authenticationClient;

    private AuthProperties authProperties;

    @Autowired
    public void setAuthenticationClient(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @Autowired
    public void setAuthProperties(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    public String getToken() {
        if (token == null) return updateToken(null);
        return token;
    }

    @Override
    public synchronized String updateToken(String token) {
        if (!Objects.equals(this.token, token)) return this.token;
        TokenRequest tokenRequest = new TokenRequest(authProperties.getName(), authProperties.getPassword());
        this.token = authenticationClient.getToken(tokenRequest);
        return this.token;
    }

}
