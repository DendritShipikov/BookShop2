package com.dendrit.bookshop.ordersapi.repositories;

import com.dendrit.bookshop.ordersapi.entities.OrderItem;
import com.dendrit.bookshop.ordersapi.entities.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
