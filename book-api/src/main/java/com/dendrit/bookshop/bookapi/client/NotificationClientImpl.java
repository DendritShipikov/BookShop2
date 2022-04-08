package com.dendrit.bookshop.bookapi.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dendrit.bookshop.bookapi.data.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;

@Component
public class NotificationClientImpl implements NotificationClient {

    @Value("${internalauthapi.address}")
    private String baseInternalAuthApiAddress;

    @Value("${notificationapi.address}")
    private String baseNotificationApiAddress;

    @Value("${internal.auth.name}")
    private String name;

    @Value("${internal.auth.password}")
    private String password;

    private RestTemplate restTemplate;

    private TokenHolder tokenHolder;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setTokenHolder(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    @Override
    public void send(Message message) {
        HttpHeaders headers = new HttpHeaders();
        String token = getValidToken();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<Message> httpEntity = new HttpEntity<>(message, headers);
        restTemplate.postForObject(baseNotificationApiAddress + "/bookshop/api/notification", httpEntity, Void.TYPE);
    }

    private String getValidToken() {
        String token = tokenHolder.getToken();
        if (token != null) {
            DecodedJWT decodedJWT = JWT.decode(token);
            if (decodedJWT.getExpiresAt().after(new Date()))  return token;
        }
        synchronized (tokenHolder) {
            if (Objects.equals(token, tokenHolder.getToken())) {
                TokenRequest tokenRequest = new TokenRequest();
                tokenRequest.setName(name);
                tokenRequest.setPassword(password);
                token = restTemplate.postForObject(
                        baseInternalAuthApiAddress + "/token",
                        tokenRequest,
                        String.class);
                tokenHolder.setToken(token);
            }
        }
        return token;
    }

    private static class TokenRequest {

        private String name;

        private  String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
