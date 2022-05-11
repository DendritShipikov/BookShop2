package com.dendrit.bookshop.notificationapi.services;

import com.dendrit.bookshop.core.notificationclient.data.NotificationRequest;

public interface NotificationService {

    void sendNotification(NotificationRequest notificationRequest);

}
