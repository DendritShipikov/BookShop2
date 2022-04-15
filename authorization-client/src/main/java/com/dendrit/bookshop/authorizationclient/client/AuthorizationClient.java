package com.dendrit.bookshop.authorizationclient.client;

import com.dendrit.bookshop.authorizationclient.security.AuthorizationData;
import com.dendrit.bookshop.common.absrtact.AbstractRestAdapter;
import com.dendrit.bookshop.common.model.RestRequest;
import com.dendrit.bookshop.common.model.RestResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationClient extends AbstractRestAdapter<AuthorizationProperties> {

    public AuthorizationData getAuthorities(Long id) {
        Map<String, String> headers = new HashMap<>();
        RestRequest<Void> restRequest = new RestRequest<>("GET", "/" + id, headers, null);
        RestResponse<? extends AuthorizationData> restResponse = super.execute(restRequest, getRestProperties().getAuthorizationDataType());
        return restResponse.getBody();
    }

}
