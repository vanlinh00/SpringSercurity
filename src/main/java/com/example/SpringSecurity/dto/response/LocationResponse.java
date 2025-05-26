package com.example.SpringSecurity.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationResponse {
    private Long id;
    private Long userId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
