package com.dendrit.bookshop.ordersapi.controllers;

import com.dendrit.bookshop.core.usersclient.data.UserData;
import com.dendrit.bookshop.ordersapi.data.OrderData;
import com.dendrit.bookshop.ordersapi.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersController {

    private OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/users/orders")
    public List<OrderData> getAllByUserId() {
        Long userId = ((UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return ordersService.getAllByUserId(userId);
    }
}
