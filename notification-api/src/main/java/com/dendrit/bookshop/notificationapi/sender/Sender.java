package com.dendrit.bookshop.notificationapi.sender;

import com.dendrit.bookshop.notificationapi.data.NotificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    private JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${orders-api.key}")
    private String queueName;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "${orders-api.queue}")
    public void send(TextMessage textMessage) throws JMSException, JsonProcessingException {
        NotificationRequest notificationRequest = objectMapper.readValue(textMessage.getText(), NotificationRequest.class);
        LOGGER.info("order id = '" + notificationRequest.getId() + "' received");
        jmsTemplate.convertAndSend(queueName, notificationRequest.getId());
    }

}
