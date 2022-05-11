package com.dendrit.bookshop.notificationapi.listeners;

import com.dendrit.bookshop.core.notificationclient.data.NotificationRequest;
import com.dendrit.bookshop.notificationapi.services.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class NotificationListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private NotificationService notificationService;

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "bookshop.notification.requests")
    public void processNotificationRequest(TextMessage message) {
        try {
            NotificationRequest notificationRequest = objectMapper.readValue(message.getText(), NotificationRequest.class);
            notificationService.sendNotification(notificationRequest);
        } catch (JMSException | JsonProcessingException e) {
            throw new RuntimeException("Exception whn converting message from JSON", e);
        }
    }

}
