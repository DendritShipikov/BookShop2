package com.dendrit.bookshop.authorizationclient.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthorizationData {

    Collection<? extends GrantedAuthority> getAuthorities();

}
