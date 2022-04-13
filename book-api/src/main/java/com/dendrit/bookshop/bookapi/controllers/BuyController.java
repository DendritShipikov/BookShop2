package com.dendrit.bookshop.bookapi.controllers;

import com.dendrit.bookshop.bookapi.aspects.CalculateTime;
import com.dendrit.bookshop.bookapi.services.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @CalculateTime
    public ResponseEntity<Void> buyAllFromCart() {
        buyService.buyAllFromCart();
        return ResponseEntity.accepted().build();
    }
}
