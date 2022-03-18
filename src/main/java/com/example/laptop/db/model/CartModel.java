package com.example.laptop.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartModel {

    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private Long totalPrice;

    private List<CartDetailModel> cartDetailModels;
}
