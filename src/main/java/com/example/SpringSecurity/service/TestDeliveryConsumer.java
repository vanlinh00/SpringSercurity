package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.DeliveryStatusDTO;
import com.example.SpringSecurity.utils.KafkaTopics;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TestDeliveryConsumer {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final int TOTAL_MESSAGES = 100;

    private long startTime = 0;

    @KafkaListener(
            topics = KafkaTopics.DELIVERY_STATUS_TOPIC,
            groupId = "delivery_group",
            concurrency = "3",  // 3 consumer thread chạy song song
    autoStartup = "false" // <- Dòng này ngăn không cho listener chạy lúc khởi động

            )
    public void consume(ConsumerRecord<String, String> record) {
        try {
            if (counter.get() == 0) {
                startTime = System.currentTimeMillis();
            }

            ObjectMapper mapper = new ObjectMapper();
            DeliveryStatusDTO dto = mapper.readValue(record.value(), DeliveryStatusDTO.class);

            // Giả lập xử lý mất 100ms
            Thread.sleep(100);

            int processed = counter.incrementAndGet();
            if (processed == TOTAL_MESSAGES) {
                long endTime = System.currentTimeMillis();
                System.out.println("=== Tổng thời gian xử lý " + TOTAL_MESSAGES + " tin nhắn: " + (endTime - startTime) + " ms ===");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
                 Producer
                        ↓
          ┌────────────────────────────┐
          │  Topic: delivery-status    │
          └────────────────────────────┘
            │       │         │
        Partition0 Partition1 Partition2
            ↓         ↓         ↓
      ┌────────┐ ┌────────┐ ┌────────┐
      │Consumer│ │Consumer│ │Consumer│
      │   A    │ │   B    │ │   C    │
      └────────┘ └────────┘ └────────┘
       groupId = delivery_group

     */
}
