package com.dendrit.bookshop.common.absrtact;

import com.dendrit.bookshop.common.model.RestRequest;
import com.dendrit.bookshop.common.model.RestResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.net.URI;
import java.util.Map;

public abstract class AbstractRestAdapter<P extends RestProperties> {

    private RestTemplate restTemplate;

    private P restProperties;

    @Value("classpath:bookshop.p12")
    private Resource trustStore;

    @Autowired
    public void setRestProperties(P restProperties) {
        this.restProperties = restProperties;
    }

    public P getRestProperties() {
        return restProperties;
    }

    @PostConstruct
    public void injectRestTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), "qazwsx".toCharArray())
                .build();
        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients
                .custom()
                .setSSLSocketFactory(connectionSocketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate = new RestTemplate(requestFactory);
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
