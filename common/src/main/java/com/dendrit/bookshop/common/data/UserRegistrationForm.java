package com.dendrit.bookshop.common.data;

import java.util.Set;

public class UserRegistrationForm {

    private String username;

    private String password;

    private Set<Role> roles;

    public UserRegistrationForm(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
