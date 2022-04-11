package com.dendrit.bookshop.bookapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TokenHolder {

    private String token;

    private SimpleRestAdapter simpleRestAdapter;

    @Value("${internal.auth.name}")
    private String name;

    @Value("${internal.auth.password}")
    private String password;

    @Value("${internalauthapi.address}")
    private String baseInternalAuthApiAddress;

    @Autowired
    public void setSimpleRestAdapter(SimpleRestAdapter simpleRestAdapter) {
        this.simpleRestAdapter = simpleRestAdapter;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public synchronized String updateToken(String token) {
        if (!Objects.equals(this.token, token)) return this.token;
        Map<String, String> headers = new HashMap<>();
        TokenRequest tokenRequest = new TokenRequest(name, password);
        RestRequest<TokenRequest> restRequest = new RestRequest<>("POST", baseInternalAuthApiAddress + "/token", headers, tokenRequest);
        RestResponse<String> restResponse = simpleRestAdapter.execute(restRequest, String.class);
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
