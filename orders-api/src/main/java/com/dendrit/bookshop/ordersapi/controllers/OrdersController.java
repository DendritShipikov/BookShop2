package com.dendrit.bookshop.ordersapi.controllers;

import com.dendrit.bookshop.ordersapi.data.OrderData;
import com.dendrit.bookshop.ordersapi.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.dendrit.bookshop.ordersapi.util.UserUtil;

import java.util.List;

@RestController
public class OrdersController {

    private OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/orders/{id}")
    public OrderData getOrderById(@PathVariable Long id) {
        return ordersService.getById(id);
    }

    @GetMapping("/orders")
    public List<OrderData> getOrders() {
        return ordersService.getAllByUserId(UserUtil.getUserId());
    }

}
