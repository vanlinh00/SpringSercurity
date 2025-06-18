package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.WearablePayload;
import org.springframework.stereotype.Service;

@Service
public class WearableDataService {

    public boolean verifySignature(WearablePayload payload, String signature) {
        // ⚠️ Giả sử vendor yêu cầu verify chữ ký HMAC
        // Ở đây đơn giản hóa, thực tế bạn cần key & thuật toán từ vendor
        return true; // TODO: implement real verification
    }

    public void saveData(WearablePayload payload) {
        // TODO: Map và lưu vào DB
        System.out.println("Saving wearable data: " + payload);
        // Gợi ý: Có thể gửi Kafka hoặc lưu DB ở đây
    }
}
