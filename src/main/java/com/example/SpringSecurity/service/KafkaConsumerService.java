package com.example.SpringSecurity.service;// KafkaConsumerService.java


import com.example.SpringSecurity.utils.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// Consumer để nhận message Kafka từ hệ thống PHP gửi sang

@Service
public class KafkaConsumerService {

    // Annotation này định nghĩa một Consumer.
    // "topics" là tên Kafka topic để lắng nghe.
    // "groupId" giúp Kafka xác định các consumer thuộc cùng một nhóm để chia sẻ dữ liệu
    // và tránh xử lý trùng lặp.
//
//    @KafkaListener(topics = KafkaTopics.DELIVERY_STATUS_TOPIC, groupId = "delivery_group")
//    public void consumePickupComplete(String message) {
//
//        // Nhận message dạng text từ hệ thống PHP và xử lý tiếp (ví dụ: cập nhật DB)
//        System.out.println("Nhận từ PHP: " + message);
//    }
}
