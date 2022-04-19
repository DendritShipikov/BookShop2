package com.dendrit.bookshop.bookapi.data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemData that = (CartItemData) o;
        return bookCount == that.bookCount && Objects.equals(bookData, that.bookData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookData, bookCount);
    }
}
