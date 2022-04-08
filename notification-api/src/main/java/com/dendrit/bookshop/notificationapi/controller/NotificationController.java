package com.dendrit.bookshop.notificationapi.controller;

import com.dendrit.bookshop.notificationapi.data.Message;
import com.dendrit.bookshop.notificationapi.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    private NotificationService notificationService;

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notification")
    public void send(@RequestBody Message message) {
        //notificationService.sendMessage(message);
        LOGGER.info("Sending message '" + message.getText() + "' to '" + message.getUserMail() + "'");
    }

}
