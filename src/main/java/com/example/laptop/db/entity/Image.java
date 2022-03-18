package com.example.laptop.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Data
public class Image extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

    @OneToOne(mappedBy = "image")
    private Manufacturer manufacturer;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
