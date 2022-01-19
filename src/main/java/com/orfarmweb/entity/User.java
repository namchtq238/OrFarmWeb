package com.orfarmweb.entity;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.GenerationType;
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
