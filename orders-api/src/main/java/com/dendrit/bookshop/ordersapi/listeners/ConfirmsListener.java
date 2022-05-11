package com.dendrit.bookshop.ordersapi.listeners;

import com.dendrit.bookshop.ordersapi.services.OrdersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class ConfirmsListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @JmsListener(destination = "bookshop.notification.confirms")
    public void acceptConfirm(TextMessage message) {
        try {
            Long id = objectMapper.readValue(message.getText(), Long.TYPE);
            ordersService.updateById(id);
        } catch (JsonProcessingException | JMSException e) {
            throw new RuntimeException("Exception while converting message from JSON", e);
        }
    }

}
