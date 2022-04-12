package com.dendrit.bookshop.bookapi.client.absrtact;

import com.dendrit.bookshop.bookapi.client.model.RestRequest;
import com.dendrit.bookshop.bookapi.client.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

public abstract class AbstractRestAdapter {

    private RestTemplate restTemplate;

    private final RestProperties restProperties;

    public AbstractRestAdapter(RestProperties restProperties) {
        this.restProperties = restProperties;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T, D> RestResponse<T> execute(RestRequest<D> restRequest, Class<T> responseType) {
        try {
            if (restProperties != null) {
                restRequest.setUrl(restProperties.getBaseAddress() + restRequest.getUrl());
            }
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
