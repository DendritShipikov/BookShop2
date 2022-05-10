package com.dendrit.bookshop.ordersapi.entities;

import java.io.Serializable;
import java.util.Objects;

public class OrderItemId implements Serializable {

    private Long bookId;

    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(bookId, that.bookId) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, order);
    }
}
