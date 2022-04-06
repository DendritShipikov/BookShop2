package com.dendrit.bookshop.bookapi.data;

public class AddBookToCartRequest {

    private Long bookId;

    private int bookCount;

    public AddBookToCartRequest() {}

    public AddBookToCartRequest(Long bookId, int bookCount) {
        this.bookId = bookId;
        this.bookCount = bookCount;
    }

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
