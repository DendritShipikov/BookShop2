package com.dendrit.bookshop.bookapi;

import com.dendrit.bookshop.common.audit.EnableAudit;
import com.dendrit.bookshop.core.ordersclient.OrdersClientConfiguration;
import com.dendrit.bookshop.core.usersclient.UsersClientConfiguration;
import com.dendrit.bookshop.core.usersclient.security.UsersSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@EnableAudit
@Import({
        UsersClientConfiguration.class,
        UsersSecurityConfiguration.class,
        OrdersClientConfiguration.class
})
public class BookApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApiApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dendrit.bookshop.bookapi.controllers"))
                .build();
    }
}
