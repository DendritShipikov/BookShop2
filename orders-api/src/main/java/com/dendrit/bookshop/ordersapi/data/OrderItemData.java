package com.dendrit.bookshop.ordersapi.data;

public class OrderItemData {

    private Long bookId;

    private int bookCount;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }
}