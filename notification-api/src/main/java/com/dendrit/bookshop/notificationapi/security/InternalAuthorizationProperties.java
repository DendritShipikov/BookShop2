package com.dendrit.bookshop.notificationapi.security;

import com.dendrit.bookshop.authorizationclient.client.AuthorizationProperties;
import com.dendrit.bookshop.authorizationclient.security.AuthorizationData;
import com.dendrit.bookshop.notificationapi.data.ProfileData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("internal-auth-api")
public class InternalAuthorizationProperties implements AuthorizationProperties {

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
        return ProfileData.class;
    }
}
