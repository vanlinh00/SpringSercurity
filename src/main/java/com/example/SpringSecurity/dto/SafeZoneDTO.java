package com.example.SpringSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class SafeZoneDTO {
    private double lat;
    private double lng;
    private double radius;

}
