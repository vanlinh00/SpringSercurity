package com.example.SpringSecurity.entity;

import lombok.Data;

@Data
public class LocationRequest {
    private Long userId;
    private Double lat;
    private Double lng;
}
