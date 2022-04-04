package com.dendrit.bookshop.userapi.data;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class UserRegistrationForm {

    @ApiModelProperty(notes = "Username that will be registered", required = true)
    @NotNull
    @NotEmpty(message = "username can not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "username can contains only digits and letters and can not be empty")
    private String username;

    @ApiModelProperty(notes = "Password that will be registered", required = true)
    @NotNull
    @NotEmpty(message = "password can not be empty")
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
