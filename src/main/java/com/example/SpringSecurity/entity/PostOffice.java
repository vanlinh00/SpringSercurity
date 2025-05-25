package com.example.SpringSecurity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_offices")
public class PostOffice {
    @Id
    private Long id;
    private String code;
    private String name;
}
