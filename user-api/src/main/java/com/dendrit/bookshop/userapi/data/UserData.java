package com.dendrit.bookshop.userapi.data;

import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

public class UserData {

    @ApiModelProperty(notes = "User id")
    private Long id;

    @ApiModelProperty(notes = "Username")
    private String username;

    @ApiModelProperty(notes = "Set of roles")
    private Set<Role> roles;

    public UserData(Long id, String username, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
