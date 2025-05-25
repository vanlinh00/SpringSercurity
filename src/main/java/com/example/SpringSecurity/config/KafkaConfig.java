package com.example.SpringSecurity.config;



import com.example.SpringSecurity.dto.DeliveryStatusDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = false)

public class KafkaConfig {

    @Bean
    public List<NewTopic> topics() {
        return List.of(
                new NewTopic("delivery-status-topic-1-partition", 1, (short) 1),
                new NewTopic("delivery-status-topic-3-partition", 3, (short) 1)
        );
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        // số consumer threads = số partition mà bạn muốn chạy song song
//        factory.setConcurrency(3);
//        return factory;
//    }
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "delivery_group");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // ✅ CHỈNH LẠI Ở ĐÂY
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//
//    // Cấu hình factory để tạo Kafka Producer với kiểu dữ liệu gửi là JSON
//    @Bean
//    public ProducerFactory<String, DeliveryStatusDTO> producerFactory() {
//        Map<String, Object> config = new HashMap<>();
//
//        // (1) Địa chỉ Kafka broker — đây là nơi producer sẽ kết nối để gửi message
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//
//        //(2) Serializer cho key là chuỗi
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        //(2) Serializer cho value là JSON — vì dữ liệu ta gửi đi là một object DTO
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//  // Bước 1) cấu hình KafkaTemplate
//    @Bean
//    public KafkaTemplate<String, DeliveryStatusDTO> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
}

/*

[Producer]
     | gửi message
     v
[Docker Container] chứa [Kafka Broker]
     | lưu message vào
     v
[Topic]  (ví dụ: delivery-status-topic)
     | gồm nhiều
     v
[Partition 0]    [Partition 1]    [Partition 2]  ...
     | chứa các
     v
[Message 1, Message 2, Message 3,...]


thành phần message

Message {
   Key: "order123",
   Value: {"orderId":"order123","status":"DELIVERED","deliveredAt":"2025-05-24T15:00:00"},
   Timestamp: 1684945200000,
   Headers: {traceId: "abc-123"},
   Partition: 2,
   Offset: 15
}


 */
