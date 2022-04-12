package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.common.absrtact.RestProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationClientRestProperties implements RestProperties {

    @Value("${internalauthapi.address}")
    private String baseInternalAuthApiAddress;

    @Override
    public String getBaseAddress() {
        return baseInternalAuthApiAddress;
    }
}
