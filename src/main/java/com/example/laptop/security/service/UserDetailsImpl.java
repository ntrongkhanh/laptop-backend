package com.example.laptop.security.service;

import com.example.laptop.db.entity.User;
import com.example.laptop.utils.AuthoritiesConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Boolean isAdmin;

    public UserDetailsImpl(Long id, String username, String password, Boolean isAdmin,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        String role = null;
        if (user.isAdmin()) {
            role = AuthoritiesConstants.ADMIN;
        } else {
            role = AuthoritiesConstants.USER;

        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(role));
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.isAdmin(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}