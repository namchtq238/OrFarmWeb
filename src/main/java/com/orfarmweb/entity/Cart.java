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
    @ManyToOne(targetEntity = Product.class)
    private Product product;
    private Integer quantity;
    private boolean isDelete=false;
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User user;
}
