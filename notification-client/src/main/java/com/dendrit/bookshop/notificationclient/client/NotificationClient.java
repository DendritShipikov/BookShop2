package com.dendrit.bookshop.notificationclient.client;

import com.dendrit.bookshop.common.absrtact.AbstractAuthenticationRestAdapter;
import com.dendrit.bookshop.common.model.RestRequest;
import com.dendrit.bookshop.notificationclient.model.Message;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class NotificationClient extends AbstractAuthenticationRestAdapter<NotificationClientRestProperties> {

    public void send(Message message) {
        RestRequest<Message> restRequest =
                new RestRequest<>(HttpMethod.POST.name(),  "/notification", new HashMap<>(), message);
        super.execute(restRequest, Object.class);
    }

}
