package com.dendrit.bookshop.ordersapi.security;

import com.dendrit.bookshop.authorizationclient.client.AuthorizationProperties;
import com.dendrit.bookshop.authorizationclient.security.AuthorizationData;
import com.dendrit.bookshop.ordersapi.data.UserData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("user-api")
public class UserAuthorizationProperties implements AuthorizationProperties {

    private String baseAddress;

    private Resource trustStore;

    private String trustStorePassword;

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

    @Override
    public Resource getTrustStore() {
        return trustStore;
    }

    public void setTrustStore(Resource trustStore) {
        this.trustStore = trustStore;
    }

    @Override
    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }
}
