package com.dendrit.bookshop.bookapi.controllers;

import com.dendrit.bookshop.common.audit.aspects.Audit;
import com.dendrit.bookshop.bookapi.data.AddBookToCartRequest;
import com.dendrit.bookshop.bookapi.data.CartItemData;
import com.dendrit.bookshop.bookapi.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Audit
    public List<CartItemData> getCart() {
        return cartService.getCart();
    }

    @PutMapping("cart/books")
    @Audit
    public ResponseEntity<Void> addBookToCart(@RequestBody AddBookToCartRequest addBookToCartRequest) {
        cartService.addBookToCart(addBookToCartRequest.getBookId(), addBookToCartRequest.getBookCount());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/cart/books/{bookId}")
    @Audit
    public ResponseEntity<Void> deleteBookFromCart(@PathVariable Long bookId) {
        cartService.deleteBookFromCart(bookId);
        return ResponseEntity.noContent().build();
    }

}
