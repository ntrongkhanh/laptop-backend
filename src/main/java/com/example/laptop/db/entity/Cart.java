package com.example.laptop.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
public class Cart extends BaseEntity {

    @Column(name = "total_price")
    private Long totalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartDetail> cartDetails;
}