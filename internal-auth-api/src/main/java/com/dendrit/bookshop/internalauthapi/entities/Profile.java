package com.dendrit.bookshop.internalauthapi.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Profile {

    @Id
    private Long id;

    private String name;

    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
