package com.dendrit.bookshop.core.usersclient.security;

import com.dendrit.bookshop.core.usersclient.UsersClient;
import com.dendrit.bookshop.core.usersclient.UsersClientConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(UsersAuthenticationProperties.class)
@Import(UsersClientConfiguration.class)
public class UsersSecurityConfiguration {

    private UsersAuthenticationProperties usersAuthenticationProperties;

    public UsersSecurityConfiguration(UsersAuthenticationProperties usersAuthorizationProperties) {
        this.usersAuthenticationProperties = usersAuthorizationProperties;
    }

    @Bean
    public UsersAuthenticationProvider usersAuthenticationProvider(UsersClient usersClient) {
        UsersAuthenticationProvider usersAuthenticationProvider = new UsersAuthenticationProvider();
        usersAuthenticationProvider.setUsersClient(usersClient);
        usersAuthenticationProvider.setUsersAuthenticationProperties(usersAuthenticationProperties);
        return usersAuthenticationProvider;
    }

}
