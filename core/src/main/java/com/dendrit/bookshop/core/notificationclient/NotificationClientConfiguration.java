package com.dendrit.bookshop.core.notificationclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableConfigurationProperties(NotificationClientProperties.class)
public class NotificationClientConfiguration {

    private final NotificationClientProperties notificationClientProperties;

    @Autowired
    public NotificationClientConfiguration(NotificationClientProperties notificationClientProperties) {
        this.notificationClientProperties = notificationClientProperties;
    }

    @Bean
    public NotificationClient notificationClient(JmsTemplate jmsTemplate) {
        NotificationClient notificationClient = new NotificationClient();
        notificationClient.setNotificationClientProperties(notificationClientProperties);
        notificationClient.setJmsTemplate(jmsTemplate);
        return notificationClient;
    }

}
