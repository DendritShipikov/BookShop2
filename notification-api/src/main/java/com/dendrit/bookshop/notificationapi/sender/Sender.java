package com.dendrit.bookshop.notificationapi.sender;

import com.dendrit.bookshop.notificationapi.data.OrderData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    private RabbitTemplate rabbitTemplate;

    @Value("${orders-api.key}")
    private String queueName;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${orders-api.queue}")
    public void send(OrderData orderData) {
        LOGGER.info("order id = '" + orderData.getId() + "' received");
        rabbitTemplate.convertAndSend(queueName, orderData.getId());
    }

}
