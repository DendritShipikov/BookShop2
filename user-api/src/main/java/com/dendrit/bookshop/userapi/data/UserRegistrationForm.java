package com.dendrit.bookshop.userapi.data;

import com.dendrit.bookshop.common.data.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

public class UserRegistrationForm {

    @ApiModelProperty(notes = "Username that will be registered", required = true)
    private String username;

    @ApiModelProperty(notes = "Password that will be registered", required = true)
    private String password;

    @ApiModelProperty(notes = "Set of roles", required = true)
    private Set<Role> roles;

    public UserRegistrationForm() {}

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
