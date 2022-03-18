package com.example.laptop.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "active")
    private boolean active = true;

    public BaseEntity() {
        this.createdDate = new Date();
        this.updatedDate = this.getCreatedDate();
    }

    public BaseEntity(Long id) {
        this();
        this.id = id;
    }
}
