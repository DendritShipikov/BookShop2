package com.dendrit.bookshop.core.usersclient.security;

import com.dendrit.bookshop.core.security.AbstractJwtAuthenticationProvider;
import com.dendrit.bookshop.core.security.AuthorizationDataProvider;
import com.dendrit.bookshop.core.security.AuthenticationProperties;
import com.dendrit.bookshop.core.usersclient.UsersClient;

public class UsersAuthenticationProvider extends AbstractJwtAuthenticationProvider {

    private UsersClient usersClient;

    private UsersAuthenticationProperties usersAuthenticationProperties;

    @Override
    public AuthorizationDataProvider getAuthorizationDataProvider() {
        return usersClient;
    }

    public void setUsersClient(UsersClient usersClient) {
        this.usersClient = usersClient;
    }

    @Override
    public AuthenticationProperties getAuthenticationProperties() {
        return usersAuthenticationProperties;
    }

    public void setUsersAuthenticationProperties(UsersAuthenticationProperties usersAuthenticationProperties) {
        this.usersAuthenticationProperties = usersAuthenticationProperties;
    }
}
