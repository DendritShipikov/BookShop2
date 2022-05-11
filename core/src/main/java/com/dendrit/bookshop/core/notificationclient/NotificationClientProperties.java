package com.dendrit.bookshop.core.notificationclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bookshop.notification-api.client")
public class NotificationClientProperties {

    private String notificationQueue;

    public String getNotificationQueue() {
        return notificationQueue;
    }

    public void setNotificationQueue(String notificationQueue) {
        this.notificationQueue = notificationQueue;
    }
}
