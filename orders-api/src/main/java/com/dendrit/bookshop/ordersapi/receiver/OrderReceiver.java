package com.dendrit.bookshop.ordersapi.receiver;

import com.dendrit.bookshop.ordersapi.data.OrderData;
import com.dendrit.bookshop.ordersapi.services.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiver.class);

    private OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @RabbitListener(queues = "${book-api.queue}")
    public void saveOrder(OrderData orderData) {
        LOGGER.info("receive order");
        ordersService.saveOrder(orderData);
    }

    @RabbitListener(queues = "${notification-api.queue}")
    public void updateOrder(Long id) {
        ordersService.updateById(id);
    }

}
