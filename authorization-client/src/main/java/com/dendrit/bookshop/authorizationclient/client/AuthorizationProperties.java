package com.dendrit.bookshop.authorizationclient.client;

import com.dendrit.bookshop.authorizationclient.security.AuthorizationData;
import com.dendrit.bookshop.common.absrtact.RestProperties;

public interface AuthorizationProperties extends RestProperties {

    Class<? extends AuthorizationData> getAuthorizationDataType();

}
