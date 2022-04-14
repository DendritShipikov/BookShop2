package com.dendrit.bookshop.authenticationclient.client;

import com.dendrit.bookshop.authenticationclient.model.TokenRequest;
import com.dendrit.bookshop.common.absrtact.AbstractRestAdapter;
import com.dendrit.bookshop.common.model.RestRequest;
import com.dendrit.bookshop.common.model.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationClient extends AbstractRestAdapter<AuthenticationClientRestProperties> {

    public String getToken(TokenRequest tokenRequest) {
        Map<String, String> headers = new HashMap<>();
        RestRequest<TokenRequest> restRequest = new RestRequest<>(HttpMethod.POST.name(), "/token", headers, tokenRequest);
        RestResponse<String> restResponse = super.execute(restRequest, String.class);
        if (restResponse.getStatusCode() >= 400) throw new RuntimeException("Error when updating token, status code = "
                + restResponse.getStatusCode());
        return restResponse.getBody();
    }

}
