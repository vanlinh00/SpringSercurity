package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.entity.LocationRequest;
import com.example.SpringSecurity.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LocationWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LocationService locationService;

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
}
