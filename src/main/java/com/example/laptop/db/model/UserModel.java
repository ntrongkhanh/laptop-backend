package com.example.laptop.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModel {

    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private String name;

    @Email
    @JsonProperty("username")
    private String email;

    private String password;
}
