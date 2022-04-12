package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.bookapi.data.Message;
import com.dendrit.bookshop.common.absrtact.AbstractAuthenticationRestAdapter;
import com.dendrit.bookshop.common.model.RestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class NotificationClient extends AbstractAuthenticationRestAdapter {

    @Autowired
    public NotificationClient(NotificationClientRestProperties restProperties) {
        super(restProperties);
    }

    public void send(Message message) {
        RestRequest<Message> restRequest =
                new RestRequest<>(HttpMethod.POST.name(),  "/bookshop/api/notification", new HashMap<>(), message);
        super.execute(restRequest, Object.class);
    }

}
