package com.dendrit.bookshop.bookapi.security;

import com.dendrit.bookshop.common.data.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/books").hasAnyAuthority(Role.PUBLISHER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/books/*").hasAnyAuthority(Role.PUBLISHER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/books/*").hasAnyAuthority(Role.PUBLISHER.name(), Role.ADMIN.name())
                .anyRequest().permitAll();
    }

}
