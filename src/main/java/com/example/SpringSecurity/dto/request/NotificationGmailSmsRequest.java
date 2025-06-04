package com.example.SpringSecurity.dto.request;

import lombok.Data;

@Data
public class NotificationGmailSmsRequest {
    private String type;
    private String to;
    private String message;

}
