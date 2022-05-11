package com.dendrit.bookshop.core.ordersclient;

import com.dendrit.bookshop.core.jms.AbstractJmsAdapter;
import com.dendrit.bookshop.core.ordersclient.data.OrderData;
import org.springframework.jms.core.JmsTemplate;

public class OrdersClient extends AbstractJmsAdapter {

    private JmsTemplate jmsTemplate;

    private OrdersClientProperties ordersClientProperties;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setOrdersClientProperties(OrdersClientProperties ordersClientProperties) {
        this.ordersClientProperties = ordersClientProperties;
    }

    public void sendOrder(OrderData orderData) {
        super.send(ordersClientProperties.getOrdersQueue(), orderData);
    }

    public void sendConfirm(Long id) {
        super.send(ordersClientProperties.getConfirmsQueue(), id);
    }

}
