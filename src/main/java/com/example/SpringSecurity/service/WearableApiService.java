package com.example.SpringSecurity.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WearableApiService {

    public void syncAllUsers() {
        // Giả lập danh sách user
        List<String> users = List.of("user01", "user02");
        for (String user : users) {
            // Gọi API từ wearable (giả lập)
            System.out.println("🔄 Syncing data for user: " + user);

            // Giả lập thời gian
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
