package com.dendrit.bookshop.bookapi.controllers;

import com.dendrit.bookshop.bookapi.data.AddBookToCartRequest;
import com.dendrit.bookshop.bookapi.data.CartItemData;
import com.dendrit.bookshop.bookapi.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/books")
    public List<CartItemData> getCart() {
        return cartService.getCart();
    }

    @PutMapping("cart/books")
    public void addBookToCart(@RequestBody AddBookToCartRequest addBookToCartRequest) {
        cartService.addBookToCart(addBookToCartRequest.getBookId(), addBookToCartRequest.getBookCount());
    }

    @DeleteMapping("/cart/books/{bookId}")
    public void deleteBookFromCart(@PathVariable Long bookId) {
        cartService.deleteBookFromCart(bookId);
    }

}
