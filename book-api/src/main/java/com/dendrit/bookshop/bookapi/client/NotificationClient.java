package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.bookapi.data.Message;

public interface NotificationClient {

    void send(Message message);

}
