package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.bookapi.client.absrtact.AbstractRestAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationClient extends AbstractRestAdapter {

    @Autowired
    public AuthenticationClient(AuthenticationClientRestProperties restProperties) {
        super(restProperties);
    }

}
