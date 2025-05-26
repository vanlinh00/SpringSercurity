package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.LocationDto;
import com.example.SpringSecurity.entity.LocationRequest;
import com.example.SpringSecurity.service.LocationRedisService;
import com.example.SpringSecurity.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LocationWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LocationService locationService;


    @Autowired
    private LocationRedisService redisService;

    @MessageMapping("/location") // client gửi tới /app/location
    public void receiveLocation(LocationRequest request) {
        // xử lý bất đồng bộ
        locationService.processLocationAsync(request);

        // Phản hồi về cho tất cả client đang sub /topic/locations
        messagingTemplate.convertAndSend("/topic/locations",
                "Người dùng " + request.getUserId() +
                        " đang ở [" + request.getLat() + ", " + request.getLng() + "]"
        );

    }
    //Khi client gửi vị trí mới đến /app/location, phương thức receiveLocation() được Spring gọi trên một WebSocket thread chính.

    @MessageMapping("/location/update")
    public void receiveLocationGroup(@Payload LocationDto location) throws JsonProcessingException {
//        // 1. Lưu Redis
        redisService.saveLocation(location);

        // 2. Broadcast tới group chung của người dùng
        String destination = "/topic/location/group-" + location.getGroupId();
        messagingTemplate.convertAndSend(destination, location);


        // Câu lệnh này gửi một message từ server đến broker (ở đây là SimpleBroker),
        // và broker sẽ phân phối message này đến tất cả các client đang subscribe đến topic/queue đó.
    }
}
