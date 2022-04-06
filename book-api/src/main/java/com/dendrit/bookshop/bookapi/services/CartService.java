package com.dendrit.bookshop.bookapi.services;

import com.dendrit.bookshop.bookapi.data.CartItemData;

import java.util.List;

public interface CartService {

    void addBookToCart(Long bookId, int bookCount);

    void deleteBookFromCart(Long bookId);

    List<CartItemData> getCart();

}
