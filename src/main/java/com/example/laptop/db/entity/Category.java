package com.example.laptop.db.entity;

import com.example.laptop.db.enums.ProductType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Data
public class Category extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "product_type")
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
