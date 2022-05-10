package com.dendrit.bookshop.core.ordersclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableConfigurationProperties(OrdersClientProperties.class)
public class OrdersClientConfiguration {

    private final OrdersClientProperties ordersClientProperties;

    @Autowired
    public OrdersClientConfiguration(OrdersClientProperties ordersClientProperties) {
        this.ordersClientProperties = ordersClientProperties;
    }

    @Bean
    public OrdersClient ordersClient(JmsTemplate jmsTemplate) {
        OrdersClient ordersClient = new OrdersClient();
        ordersClient.setOrdersClientProperties(ordersClientProperties);
        ordersClient.setJmsTemplate(jmsTemplate);
        return ordersClient;
    }
}
