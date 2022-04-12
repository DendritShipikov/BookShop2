package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.bookapi.client.absrtact.AbstractAuthenticationRestAdapter;
import com.dendrit.bookshop.bookapi.client.model.RestRequest;
import com.dendrit.bookshop.bookapi.data.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class NotificationClient extends AbstractAuthenticationRestAdapter {

    @Value("${notificationapi.address}")
    private String baseNotificationApiAddress;

    public void send(Message message) {
        RestRequest<Message> restRequest =
                new RestRequest<>(HttpMethod.POST.name(), baseNotificationApiAddress + "/bookshop/api/notification", new HashMap<>(), message);
        super.execute(restRequest, Object.class);
    }

}
