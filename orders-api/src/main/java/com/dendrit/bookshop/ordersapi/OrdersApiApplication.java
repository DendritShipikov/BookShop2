package com.dendrit.bookshop.ordersapi;

import com.dendrit.bookshop.core.notificationclient.NotificationClientConfiguration;
import com.dendrit.bookshop.core.usersclient.UsersClientConfiguration;
import com.dendrit.bookshop.core.usersclient.security.UsersSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        UsersClientConfiguration.class,
        UsersSecurityConfiguration.class,
        NotificationClientConfiguration.class
})
public class OrdersApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApiApplication.class, args);
    }
}
