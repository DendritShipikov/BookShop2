package com.dendrit.bookshop.ordersapi.mapper;

import com.dendrit.bookshop.ordersapi.data.OrderData;
import com.dendrit.bookshop.ordersapi.data.OrderItemData;
import com.dendrit.bookshop.ordersapi.entities.Order;
import com.dendrit.bookshop.ordersapi.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public OrderData toOrderData(Order order) {
        OrderData orderData = new OrderData();
        orderData.setDate(order.getDate());
        orderData.setUserId(order.getUserId());
        orderData.setId(order.getId());
        orderData.setStatus(order.getStatus());
        for (OrderItem orderItem : order.getOrderItemList()) {
            OrderItemData orderItemData = new OrderItemData();
            orderItemData.setBookId(orderItem.getBookId());
            orderItemData.setBookCount(orderItem.getBookCount());
            orderData.getOrderItemDataList().add(orderItemData);
        }
        return orderData;
    }

    public Order toOrder(OrderData orderData) {
        Order order = new Order();
        for (OrderItemData orderItemData : orderData.getOrderItemDataList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBookId(orderItemData.getBookId());
            orderItem.setBookCount(orderItemData.getBookCount());
            order.getOrderItemList().add(orderItem);
        }
        order.setDate(orderData.getDate());
        order.setId(orderData.getId());
        order.setUserId(orderData.getUserId());
        order.setStatus(orderData.getStatus());
        return order;
    }

    public List<OrderData> toOrderDataList(Iterable<Order> orderIterable) {
        List<OrderData> orderDataList = new ArrayList<>();
        for (Order order : orderIterable) {
            orderDataList.add(this.toOrderData(order));
        }
        return orderDataList;
    }

    public List<Order> toOrderList(Iterable<OrderData> orderDataIterable) {
        List<Order> orderList = new ArrayList<>();
        for (OrderData orderData : orderDataIterable) {
            orderList.add(this.toOrder(orderData));
        }
        return orderList;
    }

}
