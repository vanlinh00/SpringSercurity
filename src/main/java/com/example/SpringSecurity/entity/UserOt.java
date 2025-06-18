package com.example.SpringSecurity.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "userOt")
public class UserOt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    public UserOt() {}

    public UserOt(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters & setters
}
