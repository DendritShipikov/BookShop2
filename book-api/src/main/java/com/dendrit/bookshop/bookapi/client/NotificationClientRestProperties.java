package com.dendrit.bookshop.bookapi.client;

import com.dendrit.bookshop.common.absrtact.RestProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationClientRestProperties implements RestProperties {

    @Value("${notificationapi.address}")
    private String baseNotificationApiAddress;

    @Override
    public String getBaseAddress() {
        return baseNotificationApiAddress;
    }
}
