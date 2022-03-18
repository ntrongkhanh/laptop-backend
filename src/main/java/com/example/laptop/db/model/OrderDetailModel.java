package com.example.laptop.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailModel {

    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private Long productId;

    private Integer quantity;

    private String image;

    private String name;

    private Long price;

    private Long totalPrice;
}
