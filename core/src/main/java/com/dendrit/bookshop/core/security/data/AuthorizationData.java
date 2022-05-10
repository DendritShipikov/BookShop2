package com.dendrit.bookshop.core.security.data;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthorizationData {

    Collection<? extends GrantedAuthority> getAuthorities();

}
