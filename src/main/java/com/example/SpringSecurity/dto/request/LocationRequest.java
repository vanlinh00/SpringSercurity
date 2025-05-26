package com.example.SpringSecurity.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationRequest {
    private Long userId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
