package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.bookapi.client.absrtact.AbstractRestAdapter;
import com.dendrit.bookshop.bookapi.client.model.RestRequest;
import com.dendrit.bookshop.bookapi.client.model.RestResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationClient extends AbstractRestAdapter {

    @Value("${internalauthapi.address}")
    private String baseInternalAuthApiAddress;

    @Override
    public <T, D> RestResponse<T> execute(RestRequest<D> restRequest, Class<T> responseType) {
        restRequest.setUrl(baseInternalAuthApiAddress + restRequest.getUrl());
        return super.execute(restRequest, responseType);
    }
}
