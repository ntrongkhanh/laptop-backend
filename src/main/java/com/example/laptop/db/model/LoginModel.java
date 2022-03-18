package com.example.laptop.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginModel {

    @Email
    private String email;

    private String password;
}
