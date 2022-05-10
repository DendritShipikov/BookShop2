package com.dendrit.bookshop.userapi.data;

import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

public class UserData {

    @ApiModelProperty(notes = "User id")
    private Long id;

    @ApiModelProperty(notes = "Username")
    private String username;

    @ApiModelProperty(notes = "Set of authorities")
    private Set<Role> authorities;

    public UserData(Long id, String username, Set<Role> authorities) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
