package com.example.laptop.security.service;

public interface IUserDetailsImpl {

    Long getId();

    void setId(Long id);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    Boolean getIsAdmin();

    void setIsAdmin(Boolean isAdmin);
}
