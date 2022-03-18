package com.example.laptop.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin = false;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Comment> comment;

    public User() {
        this.setActive(false);
    }
}