package com.example.laptop.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ram")
@Data
public class Ram extends BaseEntity {

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "buss")
    private String buss;

    @Column(name = "Voltage")
    private String Voltage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
