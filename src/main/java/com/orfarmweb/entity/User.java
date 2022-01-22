package com.orfarmweb.entity;
import com.orfarmweb.constaint.Role;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.GenerationType;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private Role role;
    @OneToMany(targetEntity = Cart.class, mappedBy = "user")
    private Set<Cart> cart;
}
