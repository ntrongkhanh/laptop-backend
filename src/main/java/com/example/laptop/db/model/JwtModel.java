package com.example.laptop.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtModel {

    private String token;

    private Long id;

    private String username;

    private Boolean isAdmin;
}