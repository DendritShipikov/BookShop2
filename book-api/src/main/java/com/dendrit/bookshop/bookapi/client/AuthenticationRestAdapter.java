package com.dendrit.bookshop.bookapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationRestAdapter extends AbstractRestAdapter {

    private SimpleRestAdapter simpleRestAdapter;

    private TokenHolder tokenHolder;

    @Autowired
    public void setSimpleRestAdapter(SimpleRestAdapter simpleRestAdapter) {
        this.simpleRestAdapter = simpleRestAdapter;
    }

    @Autowired
    public void setTokenHolder(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    @Override
    public <T, D> RestResponse<T> execute(RestRequest<D> restRequest, Class<T> responseType) {
        String token = tokenHolder.getToken();
        if (token == null) token = tokenHolder.updateToken(null);
        restRequest.getHeaders().put(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        RestResponse<T> restResponse = simpleRestAdapter.execute(restRequest, responseType);
        if (restResponse.getStatusCode() != 401) return restResponse;
        token = tokenHolder.updateToken(token);
        restRequest.getHeaders().put(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return simpleRestAdapter.execute(restRequest, responseType);
    }

}
