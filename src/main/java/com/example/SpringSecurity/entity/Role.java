package com.example.SpringSecurity.entity;

import jakarta.persistence.*;

@Table(name = "Role")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}