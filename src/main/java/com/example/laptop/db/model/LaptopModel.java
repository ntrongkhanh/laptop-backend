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
public class LaptopModel {

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

    private Integer quantity;

    private String image;

    private String status;

    private Long price;

    private String national;

    private String weight;

    private String guarantee;

    private String description;

    private String color;

    private String cpu;

    private String ram;

    private String screen;

    private String graphicCard;

    private String storage;

    private String battery;

    private String port;

    private String OS;
}
