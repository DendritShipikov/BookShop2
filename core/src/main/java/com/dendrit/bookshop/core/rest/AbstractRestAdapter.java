package com.dendrit.bookshop.core.rest;

import com.dendrit.bookshop.core.rest.data.RestRequest;
import com.dendrit.bookshop.core.rest.data.RestResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

public abstract class AbstractRestAdapter {

    private RestTemplate restTemplate;

    @PostConstruct
    public void injectRestTemplate() throws Exception {
        if (getRestProperties().isEnableHttps()) {
            SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(getRestProperties().getTrustStore().getURL(), getRestProperties().getTrustStorePassword().toCharArray())
                    .build();
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
            HttpClient httpClient = HttpClients
                    .custom()
                    .setSSLSocketFactory(connectionSocketFactory)
                    .build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            this.restTemplate = new RestTemplate(requestFactory);
        } else {
            this.restTemplate = new RestTemplate();
        }
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

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
