package com.dendrit.bookshop.authenticationclient.model;

public class TokenRequest {
    private final String name;

    private final String password;

    public TokenRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
