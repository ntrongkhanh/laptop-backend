package com.example.laptop.db.entity;

import com.example.laptop.db.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product extends BaseEntity {

    @Column(name = "status")
    private String status;

    @Column(name = "weight")
    private String weight;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "guarantee")
    private String guarantee;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "name")
    private String name;

    @Column(name = "model_code")
    private String modelCode;

    @Column(name = "year")
    private Date year;

    @Column(name = "product_type")
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Image> imageEntities;

    @OneToMany(mappedBy = "product")
    private List<Comment> commentEntities;

    @OneToMany(mappedBy = "product")
    private List<CartDetail> cartDetails;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private List<Discount> discounts;

    @OneToOne(mappedBy = "product")
    private Laptop laptop;

    @OneToOne(mappedBy = "product")
    private Ram ram;

    @OneToOne(mappedBy = "product")
    private Keyboard keyboard;

    @OneToOne(mappedBy = "product")
    private Mouse mouse;
}
