package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.LocationDto;
import com.example.SpringSecurity.service.LocationRedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// 4. REST API: lấy vị trí hiện tại của cả nhóm
@RestController
@RequestMapping("/api/group")
public class LocationRestController {

    @Autowired
    private LocationRedisService redisService;

    // Dummy map nhóm → userId
    private static final Map<String, List<String>> GROUPS = Map.of(
            "group1", List.of("user1", "user2", "user3")
    );

    @GetMapping("/{groupId}/latest-locations")
    public ResponseEntity<List<LocationDto>> getGroupLocations(@PathVariable String groupId) throws JsonProcessingException {
        List<String> members = GROUPS.getOrDefault(groupId, List.of());
        return ResponseEntity.ok(redisService.getLocations(members));
    }
}

// 5. Frontend flow:
// - Khi mở màn hình → gọi API /group/{id}/latest-locations để lấy vị trí hiện tại
// - Kết nối WebSocket và subscribe /topic/location/group-{id}
// - Khi có dữ liệu mới → cập nhật bản đồ realtime
