package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.authenticationclient.client.AuthProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("internal.auth")
public class BookApiAuthProperties implements AuthProperties {

    private String name;

    private String password;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
