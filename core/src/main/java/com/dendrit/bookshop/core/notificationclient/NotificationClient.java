package com.dendrit.bookshop.core.notificationclient;

import com.dendrit.bookshop.core.jms.AbstractJmsAdapter;
import com.dendrit.bookshop.core.notificationclient.data.NotificationRequest;
import org.springframework.jms.core.JmsTemplate;

public class NotificationClient extends AbstractJmsAdapter {

    private JmsTemplate jmsTemplate;

    private NotificationClientProperties notificationClientProperties;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setNotificationClientProperties(NotificationClientProperties notificationClientProperties) {
        this.notificationClientProperties = notificationClientProperties;
    }

    public void sendNotificationRequest(NotificationRequest notificationRequest) {
        super.send(notificationClientProperties.getNotificationQueue(), notificationRequest);
    }

}
