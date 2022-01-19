package com.orfarmweb.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String briefDesc;
    private Float salePrice;
    private Float percentDiscount;
    private Integer quantityProd;
    private boolean isHot;
    private String image;
    @ManyToOne(targetEntity = Category.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cate_id", referencedColumnName = "id")
    private Category category;
    @OneToMany(targetEntity = OrderDetail.class, mappedBy = "product")
    private Set<OrderDetail> orderDetail;
}
