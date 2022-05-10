package com.dendrit.bookshop.jmsnotificationclient.client;

import com.dendrit.bookshop.common.jms.AbstractJmsAdapter;
import com.dendrit.bookshop.jmsnotificationclient.data.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsNotificationClient extends AbstractJmsAdapter {

    @Value("${notification-api.key}")
    private String destinationName;

    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    protected JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void sendNotificationRequest(NotificationRequest notificationRequest) {
        super.send(destinationName, notificationRequest);
    }

}
