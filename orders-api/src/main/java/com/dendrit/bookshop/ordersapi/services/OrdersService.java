package com.dendrit.bookshop.ordersapi.services;

import com.dendrit.bookshop.ordersapi.data.OrderData;

import java.util.List;

public interface OrdersService {

    OrderData saveOrder(OrderData orderData);

    OrderData getById(Long id);

    List<OrderData> getAllByUserId(Long userId);

    void updateById(Long id);

}
