package com.example.laptop.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Mouse")
@Data
public class Mouse extends BaseEntity {

    @Column(name = "standard_connection")
    private String standardConnection;

    @Column(name = "connection_protocol")
    private String connectionProtocol;

    @Column(name = "sensor_eye")
    private String sensorEye;

    @Column(name = "dpi")
    private String dpi;

    @Column(name = "led")
    private String led;

    @Column(name = "button")
    private String button;

    @Column(name = "size")
    private String size;

    @Column(name = "battery")
    private String battery;

    @Column(name = "os")
    private String os;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
