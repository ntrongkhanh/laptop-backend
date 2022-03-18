package com.example.laptop.db.entity;

import com.example.laptop.db.enums.ProductType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "manufacturer")
@Data
public class Manufacturer extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "product_type")
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    @Column(name = "national")
    private String national;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products;
}
