package com.dendrit.bookshop.bookapi;

import com.dendrit.bookshop.authenticationclient.client.AuthenticationClient;
import com.dendrit.bookshop.authenticationclient.client.AuthenticationClientRestProperties;
import com.dendrit.bookshop.authenticationclient.client.TokenHolderImpl;
import com.dendrit.bookshop.authorizationclient.client.AuthorizationClient;
import com.dendrit.bookshop.authorizationclient.security.JwtAuthenticationProvider;
import com.dendrit.bookshop.common.audit.aspects.CalculateTimeAspect;
import com.dendrit.bookshop.notificationclient.client.NotificationClient;
import com.dendrit.bookshop.notificationclient.client.NotificationClientRestProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@Import({
        CalculateTimeAspect.class,
        NotificationClient.class,
        NotificationClientRestProperties.class,
        TokenHolderImpl.class,
        AuthenticationClientRestProperties.class,
        AuthenticationClient.class,
        AuthorizationClient.class,
        JwtAuthenticationProvider.class
})
public class BookApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dendrit.bookshop.bookapi.controllers"))
                .build();
    }
}
