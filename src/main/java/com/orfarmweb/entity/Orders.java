package com.orfarmweb.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer amount;
    private String status;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @CreatedDate
    private Date createAt;
    private Float totalPrice;
    private String address;
    private String phoneNumber;
    private String note;
    @OneToMany(targetEntity = OrderDetail.class, mappedBy = "orders")
    private Set<OrderDetail> orderDetails;
}
