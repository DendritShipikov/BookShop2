package com.dendrit.bookshop.jmsordersclient.client;

import com.dendrit.bookshop.common.jms.AbstractJmsAdapter;
import com.dendrit.bookshop.jmsordersclient.data.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsOrdersClient extends AbstractJmsAdapter {

    @Value("${orders-api.key}")
    private String destinationName;

    private JmsTemplate jmsTemplate;

    @Override
    protected JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendOrder(OrderData orderData) {
        super.send(destinationName, orderData);
    }

}
