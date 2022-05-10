package com.dendrit.bookshop.ordersapi.receiver;

import com.dendrit.bookshop.ordersapi.data.OrderData;
import com.dendrit.bookshop.ordersapi.services.OrdersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class OrderReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiver.class);

    private OrdersService ordersService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @JmsListener(destination = "${book-api.queue}")
    public void saveOrder(TextMessage textMessage) throws JMSException, JsonProcessingException {
        OrderData orderData = objectMapper.readValue(textMessage.getText(), OrderData.class);
        LOGGER.info("receive order");
        ordersService.saveOrder(orderData);
    }

    @JmsListener(destination = "${notification-api.queue}")
    public void updateOrder(Long id) {
        ordersService.updateById(id);
    }

}
