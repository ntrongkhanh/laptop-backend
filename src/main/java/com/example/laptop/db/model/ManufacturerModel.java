package com.example.laptop.db.model;

import com.example.laptop.db.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManufacturerModel {

    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private String name;

    private Long imageId;

    private ProductType productType;

    private String national;
}
