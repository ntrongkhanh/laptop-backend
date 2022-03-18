package com.example.laptop.db.model;

import com.example.laptop.db.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryModel {

    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private String name;

    private ProductType productType;
}
