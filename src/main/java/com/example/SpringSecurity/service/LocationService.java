package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.LocationRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

//    ✅ 1. Overloading vs Overriding
//    Overloading: nhiều method cùng tên khác tham số – dùng trong LocationService.

    // Overloaded method: lưu theo userId
    public void saveLocation(Long userId, double latitude, double longitude) {
        // save to DB
    }

    // Overloaded method: lưu theo DTO
//    public void saveLocation(LocationRequestDTO dto) {
//        saveLocation(dto.getUserId(), dto.getLatitude(), dto.getLongitude());
//    }


    @Async
    public void processLocationAsync(LocationRequest request) {
        // 1. Ghi vào DB
        saveLocationToDB(request);

        // 2. Kiểm tra vùng an toàn
        boolean isOutOfSafeZone = !isInSafeZone(request);
        if (isOutOfSafeZone) {
            // 3. Gửi cảnh báo tới người thân
            sendAlertToRelatives(request);
        }

        // 4. Lưu log
        logLocationActivity(request);
    }

    private void saveLocationToDB(LocationRequest r) {
        // TODO: save r to DB
        System.out.println("📝 Lưu vào DB: " + r);
    }

    private boolean isInSafeZone(LocationRequest r) {
        // TODO: logic kiểm tra vùng an toàn
        return Math.random() > 0.3; // giả lập
    }

    private void sendAlertToRelatives(LocationRequest r) {
        // TODO: gửi notification, email, SMS, push...
        System.out.println("🚨 Cảnh báo ra khỏi vùng an toàn: " + r);
    }

    private void logLocationActivity(LocationRequest r) {
        // TODO: lưu log lại DB hoặc file
        System.out.println("📜 Ghi log vị trí: " + r);
    }
}
