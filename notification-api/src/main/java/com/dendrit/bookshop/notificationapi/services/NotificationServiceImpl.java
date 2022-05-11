package com.dendrit.bookshop.notificationapi.services;

import com.dendrit.bookshop.core.notificationclient.data.NotificationRequest;
import com.dendrit.bookshop.core.ordersclient.OrdersClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private OrdersClient ordersClient;

    @Autowired
    public void setOrdersClient(OrdersClient ordersClient) {
        this.ordersClient = ordersClient;
    }

    @Override
    public void sendNotification(NotificationRequest notificationRequest) {
        LOGGER.info("Notification '" + notificationRequest.getText() + "' to '" + notificationRequest.getUsername() + "'");
        ordersClient.sendConfirm(notificationRequest.getId());
    }
}
