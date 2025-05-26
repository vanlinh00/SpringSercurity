package com.example.SpringSecurity.dto.request;

import lombok.Data;

@Data
public class NotificationRequest {
    private String type;     // EMAIL hoặc SMS
    private String message;


}
