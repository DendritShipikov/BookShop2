package com.dendrit.bookshop.bookapi.data;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public class UserData {

    private Long id;

    private String username;

    private Set<SimpleGrantedAuthority> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<SimpleGrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Set<SimpleGrantedAuthority> roles) {
        this.roles = roles;
    }
}
