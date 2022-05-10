package com.dendrit.bookshop.ordersapi;

import com.dendrit.bookshop.core.usersclient.UsersClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({UsersClientConfiguration.class})
public class OrdersApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApiApplication.class, args);
    }
}
