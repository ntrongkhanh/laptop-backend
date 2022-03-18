package com.example.laptop.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "laptop")
@Data
public class Laptop extends BaseEntity {

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "ram")
    private String ram;

    @Column(name = "screen")
    private String screen;

    @Column(name = "graphic_card")
    private String graphicCard;

    @Column(name = "storage")
    private String storage;

    @Column(name = "battery")
    private String battery;

    @Column(name = "port")
    private String port;

    @Column(name = "OS")
    private String OS;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
