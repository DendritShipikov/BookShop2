package com.dendrit.bookshop.bookapi.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderData {
    private Long id;

    private Long userId;

    private Date date;

    private OrderStatus status;

    private List<OrderItemData> orderItemDataList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderItemData> getOrderItemDataList() {
        return orderItemDataList;
    }

    public void setOrderItemDataList(List<OrderItemData> orderItemDataList) {
        this.orderItemDataList = orderItemDataList;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
