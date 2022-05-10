package com.dendrit.bookshop.core.rest;

import com.dendrit.bookshop.core.rest.data.RestRequest;
import com.dendrit.bookshop.core.rest.data.RestResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

public abstract class AbstractRestAdapter {

    protected abstract RestTemplate getRestTemplate();

    protected abstract RestProperties getRestProperties();

    public <T, D> RestResponse<T> execute(RestRequest<D> restRequest, Class<T> responseType) {
        try {
            URI url = URI.create(this.getRestProperties().getBaseAddress() + restRequest.getUrl());
            HttpHeaders headers = new HttpHeaders();
            for (Map.Entry<String, String> header : restRequest.getHeaders().entrySet()) {
                headers.add(header.getKey(), header.getValue());
            }
            RequestEntity<D> requestEntity = new RequestEntity<>(restRequest.getBody(), headers, HttpMethod.valueOf(restRequest.getMethod()), url);
            ResponseEntity<T> responseEntity = getRestTemplate().exchange(requestEntity, responseType);
            return new RestResponse<>(responseEntity.getStatusCode().value(), requestEntity.getHeaders().toSingleValueMap(), responseEntity.getBody());
        }
        catch (HttpStatusCodeException exception) {
            return new RestResponse<>(exception.getRawStatusCode(), null, null);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Error when sending request", e);
        }
    }

}
