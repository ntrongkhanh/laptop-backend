package com.example.laptop.db.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentModel {

    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private Long productId;

    private String content;

    private UserModel user;

    private Long parentId;

    private List<CommentModel> children;
}
