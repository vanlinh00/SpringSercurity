package com.example.SpringSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WearablePayload {
    private String userId;
    private String device;
    private String timestamp;
    private int heartRate;
    private int steps;
}
