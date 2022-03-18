package com.example.laptop.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "keyboard")
@Data
public class Keyboard extends BaseEntity {

    @Column(name = "size")
    private String size;

    @Column(name = "standard_connection")
    private String standardConnection;

    @Column(name = "connection_protocol")
    private String connectionProtocol;

    @Column(name = "led")
    private String led;

    @Column(name = "switch")
    private String _switch;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
