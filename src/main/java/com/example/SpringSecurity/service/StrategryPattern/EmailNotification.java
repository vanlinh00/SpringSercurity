package com.example.SpringSecurity.service.StrategryPattern;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component("email")

public class EmailNotification implements NotificationStrategy {


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(String to, String message) {
        SimpleMailMessage smMessage = new SimpleMailMessage();
        smMessage.setFrom("vanlinh20192019@gmail.com");
        smMessage.setTo(to);
        smMessage.setSubject("THÔNG BÁO TỪ SAFE ZONE");
        smMessage.setText(message);

        mailSender.send(smMessage);
        System.out.println("✅ Đã gửi email đến " + to);
    }
}
