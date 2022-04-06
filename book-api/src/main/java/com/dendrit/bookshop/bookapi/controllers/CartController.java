package com.dendrit.bookshop.bookapi.controllers;

import com.dendrit.bookshop.bookapi.data.CartItemData;
import com.dendrit.bookshop.bookapi.data.UserData;
import com.dendrit.bookshop.bookapi.services.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private CartService cartService;

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/user")
    public List<CartItemData> getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData userData = (UserData) authentication.getPrincipal();
        return cartService.getCartByUserId(userData.getId());
    }

    @PutMapping("cart/book/{bookId}")
    public void addBookToCart(@PathVariable Long bookId) {
        cartService.addBookToCart(bookId);
    }

    @DeleteMapping("/cart/book/{bookId}")
    public void deleteBookFromCart(@PathVariable Long bookId) {
        cartService.deleteBookFromCart(bookId);
    }

}
