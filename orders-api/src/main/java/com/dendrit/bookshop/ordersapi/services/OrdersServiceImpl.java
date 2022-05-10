package com.dendrit.bookshop.ordersapi.services;

import com.dendrit.bookshop.ordersapi.data.OrderData;
import com.dendrit.bookshop.ordersapi.entities.Order;
import com.dendrit.bookshop.ordersapi.entities.OrderStatus;
import com.dendrit.bookshop.ordersapi.exceptions.OrderNotFoundException;
import com.dendrit.bookshop.ordersapi.mapper.OrderMapper;
import com.dendrit.bookshop.ordersapi.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    private OrderRepository orderRepository;

    private OrderMapper orderMapper;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderData saveOrder(OrderData orderData) {
        Order order = orderMapper.toOrder(orderData);
        orderRepository.save(order);
        orderData.setId(order.getId());
        return orderData;
    }

    @Override
    @Transactional
    public OrderData getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("order with id = " + id + "' not found"));
        return orderMapper.toOrderData(order);
    }

    @Override
    @Transactional
    public List<OrderData> getAllByUserId(Long userId) {
        List<Order> orderList = orderRepository.findAllByUserId(userId);
        return orderMapper.toOrderDataList(orderList);
    }

    @Override
    @Transactional
    public void updateById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("order with id = " + id + "' not found"));
        order.setStatus(OrderStatus.DONE);
        orderRepository.save(order);
    }

}
