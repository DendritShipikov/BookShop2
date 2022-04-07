package com.dendrit.bookshop.internalauthapi.data;

import com.dendrit.bookshop.internalauthapi.entities.Authority;

import java.util.Set;

public class ProfileData {

    private Long id;

    private String name;

    private Set<Authority> authorities;

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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
