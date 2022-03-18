package com.example.laptop.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cart_detail")
@Data
public class CartDetail extends BaseEntity {

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartDetail(int quantity, Cart cart, Product product) {
        this.quantity = quantity;
        this.cart = cart;
        this.product = product;
    }
}
