package com.dendrit.bookshop.bookapi.controllers;

import com.dendrit.bookshop.bookapi.services.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuyController {

    private CartService cartService;

    public CartService getCartService() {
        return cartService;
    }

    @PostMapping("/books/buy")
    public void buy() {
        cartService.getCart();
    }

}
