package com.dendrit.bookshop.notificationapi.services;

import com.dendrit.bookshop.notificationapi.data.Message;

public interface NotificationService {

    void sendMessage(Message message);

}
