package com.dendrit.bookshop.core.usersclient;

import com.dendrit.bookshop.core.rest.RestProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bookshop.user-api.client")
public class UsersClientProperties implements RestProperties {

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
