package com.example.laptop.db.entity;

import com.example.laptop.db.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "total_price")
    private long totalPrice;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderCode")
    private Long orderCode;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
