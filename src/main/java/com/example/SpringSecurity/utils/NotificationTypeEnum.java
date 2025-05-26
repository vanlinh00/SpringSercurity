package com.example.SpringSecurity.utils;

public enum NotificationTypeEnum {
    EMAIL,
    SMS;

    public static NotificationTypeEnum fromString(String type) {
        try {
            return NotificationTypeEnum.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown notification type: " + type);
        }
    }
}
