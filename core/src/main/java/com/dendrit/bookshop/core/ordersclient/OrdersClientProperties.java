package com.dendrit.bookshop.core.ordersclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bookshop.orders-api.client")
public class OrdersClientProperties {

    private String ordersQueue;

    private String confirmsQueue;

    public String getOrdersQueue() {
        return ordersQueue;
    }

    public void setOrdersQueue(String ordersQueue) {
        this.ordersQueue = ordersQueue;
    }

    public String getConfirmsQueue() {
        return confirmsQueue;
    }

    public void setConfirmsQueue(String confirmsQueue) {
        this.confirmsQueue = confirmsQueue;
    }
}
