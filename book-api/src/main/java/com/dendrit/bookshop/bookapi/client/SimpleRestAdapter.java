package com.dendrit.bookshop.bookapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Component
public class SimpleRestAdapter extends AbstractRestAdapter {

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T, D> RestResponse<T> execute(RestRequest<D> restRequest, Class<T> responseType) {
        try {
            URI url = URI.create(restRequest.getUrl());
            HttpHeaders headers = new HttpHeaders();
            for (Map.Entry<String, String> entry : restRequest.getHeaders().entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
            RequestEntity<D> requestEntity = new RequestEntity<>(restRequest.getBody(), headers, HttpMethod.valueOf(restRequest.getMethod()), url);
            ResponseEntity<T> responseEntity = restTemplate.exchange(requestEntity, responseType);
            return new RestResponse<>(responseEntity.getStatusCode().value(), requestEntity.getHeaders().toSingleValueMap(), responseEntity.getBody());
        } catch (HttpStatusCodeException exception) {
            return new RestResponse<>(exception.getRawStatusCode(), null, null);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error when sending request", e);
        }
    }

}
