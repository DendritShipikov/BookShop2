package com.dendrit.bookshop.notificationapi;

import com.dendrit.bookshop.core.ordersclient.OrdersClientConfiguration;
import com.dendrit.bookshop.core.usersclient.security.UsersSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@SpringBootApplication
@Import({
        UsersSecurityConfiguration.class,
        OrdersClientConfiguration.class
})
public class NotificationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApiApplication.class, args);
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername("<username>");
        javaMailSender.setPassword("<password>");
        javaMailSender.setPort(25);
        javaMailSender.setHost("smtp.gmail.com");
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.debug", true);
        return javaMailSender;
    }

}
