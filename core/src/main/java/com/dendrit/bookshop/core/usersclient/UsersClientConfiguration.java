package com.dendrit.bookshop.core.usersclient;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
@EnableConfigurationProperties(UsersClientProperties.class)
public class UsersClientConfiguration {

    private final UsersClientProperties usersClientProperties;

    @Autowired
    public UsersClientConfiguration(UsersClientProperties usersClientProperties) {
        this.usersClientProperties = usersClientProperties;
    }

   @Bean
   public UsersClient usersClient() throws Exception {
       SSLContext sslContext = new SSLContextBuilder()
               .loadTrustMaterial(usersClientProperties.getTrustStore().getURL(), usersClientProperties.getTrustStorePassword().toCharArray())
               .build();
       SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
       HttpClient httpClient = HttpClients
               .custom()
               .setSSLSocketFactory(connectionSocketFactory)
               .build();
       HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
       RestTemplate restTemplate = new RestTemplate(requestFactory);
       UsersClient usersClient = new UsersClient();
       usersClient.setUsersClientProperties(usersClientProperties);
       usersClient.setRestTemplate(restTemplate);
       return usersClient;
   }

}
