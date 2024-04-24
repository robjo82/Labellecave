package com.labellecave.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;

    @Column(nullable = false, name = "alcohol_degree")
    private float alcoholDegree;

    @Column(nullable = false)
    private int volume;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int stock;

}
