package com.dendrit.bookshop.ordersapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@IdClass(OrderItemId.class)
public class OrderItem {

    @Id
    private Long bookId;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int bookCount;


    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }
}
