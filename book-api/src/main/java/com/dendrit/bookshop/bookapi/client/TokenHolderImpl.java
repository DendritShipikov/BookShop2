package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.bookapi.client.absrtact.TokenHolder;
import com.dendrit.bookshop.bookapi.client.model.RestRequest;
import com.dendrit.bookshop.bookapi.client.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TokenHolderImpl implements TokenHolder {

    private String token;

    private AuthenticationClient authenticationClient;

    @Value("${internal.auth.name}")
    private String name;

    @Value("${internal.auth.password}")
    private String password;

    @Autowired
    public void setAuthenticationClient(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    public String getToken() {
        if (token == null) return updateToken(null);
        return token;
    }

    @Override
    public synchronized String updateToken(String token) {
        if (!Objects.equals(this.token, token)) return this.token;
        Map<String, String> headers = new HashMap<>();
        TokenRequest tokenRequest = new TokenRequest(name, password);
        RestRequest<TokenRequest> restRequest = new RestRequest<>("POST", "/token", headers, tokenRequest);
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
