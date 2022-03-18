package com.example.laptop.db.model;

import com.example.laptop.db.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderModel {

    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private String name;

    private String address;

    private String phone;

    private OrderStatus status;

    private Long userId;

    private Long totalPrice;

    private List<OrderDetailModel> orderDetailModels;
}
