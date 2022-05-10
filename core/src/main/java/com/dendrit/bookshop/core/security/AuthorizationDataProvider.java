package com.dendrit.bookshop.core.security;

import com.dendrit.bookshop.core.security.data.AuthorizationData;

public interface AuthorizationDataProvider {

    AuthorizationData getAuthorizationData(Long id);

}
