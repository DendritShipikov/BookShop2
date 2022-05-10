package com.dendrit.bookshop.core.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;

public abstract class AbstractJmsAdapter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public abstract JmsTemplate getJmsTemplate();

    public void send(String destinationName, Object object) {
        getJmsTemplate().send(destinationName, session -> {
            String text = null;
            try {
                text = objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new MessageConversionException("Exception when converting to JSON", e);
            }
            return session.createTextMessage(text);
        });
    }

}
