package com.example.SpringSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostOfficeReportDTO {
    private String postOfficeName;
    private String customerName;
    private Long parcelCount;
}
