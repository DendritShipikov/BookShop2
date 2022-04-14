package com.dendrit.bookshop.authenticationclient.client;

import com.dendrit.bookshop.common.absrtact.RestProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("internal-auth-api")
public class AuthenticationClientRestProperties implements RestProperties {

    private String baseAddress;

    @Override
    public String getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        this.baseAddress = baseAddress;
    }
}
