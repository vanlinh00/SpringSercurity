package com.example.SpringSecurity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {
    private Long driverId;
    private String status;
    private String recipient;
    private String address;
}
