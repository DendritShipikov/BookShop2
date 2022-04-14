package com.dendrit.bookshop.common.absrtact;

import com.dendrit.bookshop.common.model.RestRequest;
import com.dendrit.bookshop.common.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

public abstract class AbstractAuthenticationRestAdapter<P extends RestProperties> extends AbstractRestAdapter<P> {

    private TokenHolder tokenHolder;

    @Autowired
    public void setTokenHolder(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    @Override
    public <T, D> RestResponse<T> execute(RestRequest<D> restRequest, Class<T> responseType) {
        String token = tokenHolder.getToken();
        restRequest.getHeaders().put(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        RestResponse<T> restResponse = super.execute(restRequest, responseType);
        if (restResponse.getStatusCode() != 401) return restResponse;
        token = tokenHolder.updateToken(token);
        restRequest.getHeaders().put(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return super.execute(restRequest, responseType);
    }

}
