package com.example.SpringSecurity.dto;

import lombok.Data;

@Data
public class LocationDto {
    private String userId;
    private String groupId;
    private double latitude;
    private double longitude;
    private long timestamp;
    // constructor, getter, setter
}
