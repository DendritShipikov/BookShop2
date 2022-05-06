package com.dendrit.bookshop.ordersapi;

import com.dendrit.bookshop.authorizationclient.client.AuthorizationClient;
import com.dendrit.bookshop.authorizationclient.security.JwtAuthenticationProvider;
import com.dendrit.bookshop.jmsnotificationclient.client.JmsNotificationClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        AuthorizationClient.class,
        JwtAuthenticationProvider.class,
        JmsNotificationClient.class
})
public class OrdersApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApiApplication.class, args);
    }
}
