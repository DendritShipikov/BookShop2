package com.dendrit.bookshop.bookapi;

import com.dendrit.bookshop.authenticationclient.client.AuthenticationClient;
import com.dendrit.bookshop.authenticationclient.client.AuthenticationClientRestProperties;
import com.dendrit.bookshop.authenticationclient.client.TokenHolderImpl;
import com.dendrit.bookshop.authorizationclient.client.AuthorizationClient;
import com.dendrit.bookshop.authorizationclient.security.JwtAuthenticationProvider;
import com.dendrit.bookshop.common.audit.EnableAudit;
import com.dendrit.bookshop.notificationclient.client.NotificationClient;
import com.dendrit.bookshop.notificationclient.client.NotificationClientRestProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.net.ssl.SSLContext;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@EnableAudit
@Import({
        NotificationClient.class,
        NotificationClientRestProperties.class,
        TokenHolderImpl.class,
        AuthenticationClientRestProperties.class,
        AuthenticationClient.class,
        AuthorizationClient.class,
        JwtAuthenticationProvider.class
})
public class BookApiApplication {

    @Value("classpath:bookshop.p12")
    private Resource trustStore;
    @Value("qazwsx")
    private String trustStorePassword;

    public static void main(String[] args) {
        SpringApplication.run(BookApiApplication.class, args);
    }

//    @Bean
//    public RestTemplate restTemplate() throws Exception {
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
//                .build();
//        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
//        HttpClient httpClient = HttpClients
//                .custom()
//                .setSSLSocketFactory(connectionSocketFactory)
//                .build();
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        return new RestTemplate(requestFactory);
//    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dendrit.bookshop.bookapi.controllers"))
                .build();
    }
}
