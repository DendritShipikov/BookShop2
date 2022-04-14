package com.dendrit.bookshop.notificationclient.client;

import com.dendrit.bookshop.common.absrtact.TokenHolder;
import com.dendrit.bookshop.common.model.RestRequest;
import com.dendrit.bookshop.common.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TokenHolderImpl implements TokenHolder {

    private String token;

    private AuthenticationClient authenticationClient;

    private AuthProperties authProperties;

    @Autowired
    public void setAuthenticationClient(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @Autowired
    public void setAuthProperties(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    public String getToken() {
        if (token == null) return updateToken(null);
        return token;
    }

    @Override
    public synchronized String updateToken(String token) {
        if (!Objects.equals(this.token, token)) return this.token;
        Map<String, String> headers = new HashMap<>();
        TokenRequest tokenRequest = new TokenRequest(authProperties.getName(), authProperties.getPassword());
        RestRequest<TokenRequest> restRequest = new RestRequest<>(HttpMethod.POST.name(), "/token", headers, tokenRequest);
        RestResponse<String> restResponse = authenticationClient.execute(restRequest, String.class);
        if (restResponse.getStatusCode() >= 400) throw new RuntimeException("Error when updating token, status code = "
                + restResponse.getStatusCode());
        return this.token = restResponse.getBody();
    }

    private static class TokenRequest {

        private final String name;

        private final String password;

        public TokenRequest(String name, String password) {
            this.name = name;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }
    }

}
