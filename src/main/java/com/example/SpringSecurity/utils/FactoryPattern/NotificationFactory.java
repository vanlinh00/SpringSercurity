package com.example.SpringSecurity.utils.FactoryPattern;


import com.example.SpringSecurity.utils.NotificationTypeEnum;

//	Factory Pattern
public class NotificationFactory {

    public static Notification createNotification(NotificationTypeEnum type) {

        switch (type) {
            case EMAIL:
                return new EmailNotification();
            case SMS:
                return new SMSNotification();
            default:
                throw new IllegalArgumentException("Unknown NotificationTypeEnum: " + type);
        }
    }
}
