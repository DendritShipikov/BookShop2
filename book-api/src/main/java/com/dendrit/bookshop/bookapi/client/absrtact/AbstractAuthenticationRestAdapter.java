package com.dendrit.bookshop.bookapi.client.absrtact;

import com.dendrit.bookshop.bookapi.client.model.RestRequest;
import com.dendrit.bookshop.bookapi.client.model.RestResponse;
import com.dendrit.bookshop.bookapi.client.TokenHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

public abstract class AbstractAuthenticationRestAdapter extends AbstractRestAdapter {

    private TokenHolder tokenHolder;

    public AbstractAuthenticationRestAdapter(RestProperties restProperties) {
        super(restProperties);
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
        RestResponse<T> restResponse = super.execute(restRequest, responseType);
        if (restResponse.getStatusCode() != 401) return restResponse;
        token = tokenHolder.updateToken(token);
        restRequest.getHeaders().put(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return super.execute(restRequest, responseType);
    }

}
