package com.example.SpringSecurity.service.StrategryPattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class NotificationContext {

    @Autowired
    private ApplicationContext context;

    public void sendNotification(String type, String to, String message) {
        NotificationStrategy strategy = (NotificationStrategy) context.getBean(type.toLowerCase());
        strategy.send(to, message);
    }
}
