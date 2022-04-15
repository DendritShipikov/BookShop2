package com.dendrit.bookshop.bookapi.security;

import com.dendrit.bookshop.authorizationclient.client.AuthorizationProperties;
import com.dendrit.bookshop.authorizationclient.security.AuthorizationData;
import com.dendrit.bookshop.bookapi.data.UserData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("user-api")
public class UserAuthorizationProperties implements AuthorizationProperties {

    private String baseAddress;

    @Override
    public String getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        this.baseAddress = baseAddress;
    }

    @Override
    public Class<? extends AuthorizationData> getAuthorizationDataType() {
        return UserData.class;
    }
}
