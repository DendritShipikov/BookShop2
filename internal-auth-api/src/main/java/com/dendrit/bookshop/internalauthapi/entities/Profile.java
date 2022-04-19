package com.dendrit.bookshop.internalauthapi.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Profile {

    @Id
    @GeneratedValue
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return
                Objects.equals(id, profile.id) &&
                Objects.equals(name, profile.name) &&
                Objects.equals(password, profile.password) &&
                Objects.equals(authorities, profile.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, authorities);
    }
}
