package com.dendrit.bookshop.userapi.data;

import io.swagger.annotations.ApiModelProperty;

public class UserLoginForm {

    @ApiModelProperty(notes = "Username", required = true)
    private String username;

    @ApiModelProperty(notes = "Password", required = true)
    private String password;

    public UserLoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
