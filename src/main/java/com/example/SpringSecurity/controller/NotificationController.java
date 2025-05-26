package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.utils.FactoryPattern.Notification;
import com.example.SpringSecurity.utils.FactoryPattern.NotificationFactory;
import com.example.SpringSecurity.dto.request.NotificationRequest;
import com.example.SpringSecurity.utils.NotificationTypeEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



//// Đây là factory	Factory Pattern
@RestController
@RequestMapping("/api/public/notifications")
public class NotificationController {

    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationRequest request) {
        try {
            // Convert String sang Enum
            NotificationTypeEnum type = NotificationTypeEnum.fromString(request.getType());

            // Tạo Notification từ Factory
            Notification notification = NotificationFactory.createNotification(type);

            // Gửi thông báo
            notification.send("Tlinh",request.getMessage());

            return "Thông báo đã được gửi qua " + type.name();

        } catch (IllegalArgumentException e) {
            return "Lỗi: " + e.getMessage();
        }
    }
}
