package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// Controller nhận yêu cầu REST để cập nhật trạng thái đơn hàng
@RestController
@RequestMapping("/api/public/deliveries")

public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/{orderId}/status")
    public ResponseEntity<String> updateDeliveryStatus(@PathVariable String orderId,
                                                       @RequestParam String status) {

        // Gọi service để xử lý cập nhật và gửi trạng thái qua Kafka
        deliveryService.updateStatus(orderId, status);
        return ResponseEntity.ok("Đã gửi trạng thái phát hàng qua Kafka");
    }
}
