package com.example.SpringSecurity.controller;
import com.example.SpringSecurity.dto.request.NotificationGmailSmsRequest;
import com.example.SpringSecurity.service.StrategryPattern.NotificationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/")
public class NotificationController2 {
    
    @Autowired
    private NotificationContext notificationContext;

    @PostMapping("/notify")
    public String notifyUser(@RequestBody NotificationGmailSmsRequest request) {
        notificationContext.sendNotification(request.getType(), request.getTo(), request.getMessage());
        return "Notification sent via " + request.getType();
    }
}
