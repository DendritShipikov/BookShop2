package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.bookapi.data.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationClientImpl implements NotificationClient {

    @Value("${notificationapi.address}")
    private String baseNotificationApiAddress;

    private AuthenticationRestAdapter authenticationRestAdapter;

    @Autowired
    public void setAuthenticationRestAdapter(AuthenticationRestAdapter authenticationRestAdapter) {
        this.authenticationRestAdapter = authenticationRestAdapter;
    }

    @Override
    public void send(Message message) {
        RestRequest<Message> restRequest =
                new RestRequest<>(HttpMethod.POST.name(), baseNotificationApiAddress + "/bookshop/api/notification", new HashMap<>(), message);
        authenticationRestAdapter.execute(restRequest, Object.class);
    }

}
