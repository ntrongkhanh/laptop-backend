package com.example.laptop.db.model;

import com.example.laptop.db.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RamModel {

    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private Long manufacturerId;

    private String manufacturer;

    private Long categoryId;

    private String category;

    private ProductType productType;

    private List<ImageModel> images;

    private String modelCode;

    private String name;

    private Date year;

    private String status;

    private Long price;

    private String national;

    private String weight;

    private String guarantee;

    private String description;

    private String color;

    private String buss;

    private String capacity;

    private String voltage;
}
