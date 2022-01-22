package com.orfarmweb.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    @OneToMany(targetEntity = Product.class, mappedBy = "cart")
    @Column(name = "product_id")
    private Set<Product> product;
    private Integer quantity;
    private boolean status=false;
    private boolean isDelete=false;
}
