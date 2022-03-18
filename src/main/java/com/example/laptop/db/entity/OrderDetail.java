package com.example.laptop.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Data
public class OrderDetail extends BaseEntity {

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
