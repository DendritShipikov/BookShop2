package com.dendrit.bookshop.core.ordersclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bookshop.orders-api.client")
public class OrdersClientProperties {

    private String destinationName;

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }
}
