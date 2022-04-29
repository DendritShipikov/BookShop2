package com.dendrit.bookshop.ordersapi;

import com.dendrit.bookshop.authorizationclient.client.AuthorizationClient;
import com.dendrit.bookshop.authorizationclient.security.JwtAuthenticationProvider;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AuthorizationClient.class, JwtAuthenticationProvider.class})
public class OrdersApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApiApplication.class, args);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
