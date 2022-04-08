package com.dendrit.bookshop.notificationapi.data;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public class ProfileData {

    private Long id;

    private String name;

    private Set<SimpleGrantedAuthority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
