package com.example.SpringSecurity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
//   // 👉 1) đóng gói Encapsulation: các field là private, truy cập qua getter/setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;
    private Double longitude;

    private Long userId;

    private LocalDateTime timestamp;

}
