package com.dendrit.bookshop.bookapi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@IdClass(CartItemId.class)
@Table(name = "cart_items")
public class CartItem {

    @Id
    private Long bookId;

    @Id
    private Long userId;

    private int bookCount;

    public CartItem() {}

    public CartItem(Long bookId, Long userId, int bookCount) {
        this.bookId = bookId;
        this.userId = userId;
        this.bookCount = bookCount;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        CartItem cartItem = (CartItem) o;
        return bookCount == cartItem.bookCount && Objects.equals(bookId, cartItem.bookId) && Objects.equals(userId, cartItem.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, userId, bookCount);
    }
}
