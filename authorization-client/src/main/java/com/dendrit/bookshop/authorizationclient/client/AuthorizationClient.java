package com.dendrit.bookshop.authorizationclient.client;

import com.dendrit.bookshop.common.absrtact.AbstractRestAdapter;
import com.dendrit.bookshop.common.model.RestRequest;
import com.dendrit.bookshop.common.model.RestResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationClient extends AbstractRestAdapter<AuthorizationProperties> {

    public <T> T getAuthorities(Long id, Class<T> responseType) {
        Map<String, String> headers = new HashMap<>();
        RestRequest<Void> restRequest = new RestRequest<>("GET", getRestProperties().getBaseAddress() + "/" + id, headers, null);
        RestResponse<T> restResponse = super.execute(restRequest, responseType);
        return restResponse.getBody();
    }

}
