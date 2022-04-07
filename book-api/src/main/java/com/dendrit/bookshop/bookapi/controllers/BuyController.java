package com.dendrit.bookshop.bookapi.controllers;

import com.dendrit.bookshop.bookapi.services.BuyService;
import com.dendrit.bookshop.bookapi.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuyController {

    private BuyService buyService;

    @Autowired
    public void setBuyService(BuyService buyService) {
        this.buyService = buyService;
    }

    @PostMapping("/books/buy")
    public void buyAllFromCart() {
        buyService.buyAllFromCart();
    }
}