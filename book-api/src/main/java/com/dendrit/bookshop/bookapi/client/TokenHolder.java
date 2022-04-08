package com.dendrit.bookshop.bookapi.client;

import org.springframework.stereotype.Component;

@Component
public class TokenHolder {

    private String token;

    public void setToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
