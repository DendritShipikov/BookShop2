package com.dendrit.bookshop.bookapi.controllers;

import com.dendrit.bookshop.common.audit.aspects.CalculateTime;
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
    @CalculateTime(name = "GET /cart/books")
    public List<CartItemData> getCart() {
        return cartService.getCart();
    }

    @PutMapping("cart/books")
    @CalculateTime(name = "PUT /cart/books")
    public ResponseEntity<Void> addBookToCart(@RequestBody AddBookToCartRequest addBookToCartRequest) {
        cartService.addBookToCart(addBookToCartRequest.getBookId(), addBookToCartRequest.getBookCount());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/cart/books/{bookId}")
    @CalculateTime(name = "DELETE /cart/books/{bookId}")
    public ResponseEntity<Void> deleteBookFromCart(@PathVariable Long bookId) {
        cartService.deleteBookFromCart(bookId);
        return ResponseEntity.noContent().build();
    }

}
