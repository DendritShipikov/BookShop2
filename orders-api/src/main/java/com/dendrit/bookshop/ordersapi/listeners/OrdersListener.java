package com.dendrit.bookshop.ordersapi.listeners;

import com.dendrit.bookshop.ordersapi.data.OrderData;
import com.dendrit.bookshop.ordersapi.services.OrdersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class OrdersListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @JmsListener(destination = "bookshop.orders")
    public void saveOrder(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            OrderData orderData = objectMapper.readValue(textMessage.getText(), OrderData.class);
            ordersService.saveOrder(orderData);
        } catch (JsonProcessingException | JMSException e) {
            throw new RuntimeException("Exception when converting message from JSON", e);
        }
    }

}
