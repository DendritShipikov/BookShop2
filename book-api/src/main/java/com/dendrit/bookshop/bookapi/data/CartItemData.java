package com.dendrit.bookshop.bookapi.data;

public class CartItemData {

    private BookData bookData;

    private int bookCount;

    public CartItemData() {}

    public CartItemData(BookData bookData, int bookCount) {
        this.bookData = bookData;
        this.bookCount = bookCount;
    }

    public BookData getBookData() {
        return bookData;
    }

    public void setBookData(BookData bookData) {
        this.bookData = bookData;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }
}
