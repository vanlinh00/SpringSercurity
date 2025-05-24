package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.DeliveryStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// Service để xử lý nghiệp vụ cập nhật trạng thái đơn hàng
@Service
public class DeliveryService {

    @Autowired
    private KafkaProducerService kafkaProducer;

    public void updateStatus(String orderId, String status) {

        // Tạo DTO chứa thông tin trạng thái đơn hàng
        DeliveryStatusDTO dto = new DeliveryStatusDTO(orderId, status, LocalDateTime.now().toString());

        // Gửi message đến hệ thống PHP thông qua Kafka
        kafkaProducer.sendDeliveryStatus(dto);
    }
}
