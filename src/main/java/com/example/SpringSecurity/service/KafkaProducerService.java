package com.example.SpringSecurity.service;



import com.example.SpringSecurity.dto.DeliveryStatusDTO;
import com.example.SpringSecurity.utils.KafkaTopics;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// Service để gửi message Kafka khi cập nhật trạng thái phát hàng
@Service
public class KafkaProducerService {

    // Đây là tên của Kafka topic — nơi mà message sẽ được gửi tới.
    // Mỗi topic đại diện cho một dòng dữ liệu (message stream) phân biệt.


    @Autowired
    private KafkaTemplate<String, DeliveryStatusDTO> kafkaTemplate;

    public void sendDeliveryStatus(DeliveryStatusDTO dto) {
      /*
        // Gửi dữ liệu lên Kafka topic với key là orderId
        // (dùng để định tuyến tới partition phù hợp nếu có nhiều)

        //        // key = orderId, Kafka sẽ dựa vào key này để phân phối message vào partition tương ứng
        kafkaTemplate.send(TOPIC, dto.getOrderId(), dto);
*/
        for(int i=0; i<100; i++){
            DeliveryStatusDTO newDto = new DeliveryStatusDTO();
            newDto.setOrderId("order-" + i);
            newDto.setStatus("DELIVERED");
            kafkaTemplate.send(KafkaTopics.DELIVERY_STATUS_TOPIC, "key-" + i, newDto);
        }

    }
}
