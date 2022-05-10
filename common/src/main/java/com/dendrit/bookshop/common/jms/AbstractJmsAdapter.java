package com.dendrit.bookshop.common.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;

public abstract class AbstractJmsAdapter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected abstract JmsTemplate getJmsTemplate();

    public void send(String destinationName, Object object) {
        try {
            String message = objectMapper.writeValueAsString(object);
            getJmsTemplate().convertAndSend(destinationName, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception when converting to JSON", e);
        }
    }

}
