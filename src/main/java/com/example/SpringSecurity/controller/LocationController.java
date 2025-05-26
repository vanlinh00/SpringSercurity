package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.request.LocationRequest;
import com.example.SpringSecurity.dto.response.LocationResponse;
import com.example.SpringSecurity.entity.Location;
import com.example.SpringSecurity.service.LocationService;
import com.example.SpringSecurity.service.LocationService2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Strategy Pattern

//Tách rời hành vi (behavior) khỏi đối tượng.
//Giúp code dễ mở rộng, dễ test, và áp dụng nguyên lý Open/Closed.
// Nói chung khi chiến lước gì có khăng thay đổi
@RestController
@RequestMapping("/api/public/location")
public class LocationController {

    private final LocationService2 locationService2;

    public LocationController(LocationService2 locationService) {
        this.locationService2 = locationService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveLocation(@RequestBody LocationRequest request) {
        Location location = new Location();
        location.setUserId(request.getUserId());
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setTimestamp(request.getTimestamp());

        locationService2.saveLocation(location);
        return ResponseEntity.ok("Location saved.");
    }

    @GetMapping("/{userId}")
    public List<LocationResponse> getLocationHistory(@PathVariable Long userId) {
        List<Location> locations = locationService2.getLocationsByUser(userId);
        return locations.stream().map(loc -> {
            LocationResponse res = new LocationResponse();
            res.setId(loc.getId());
            res.setUserId(loc.getUserId());
            res.setLatitude(loc.getLatitude());
            res.setLongitude(loc.getLongitude());
            res.setTimestamp(loc.getTimestamp());
            return res;
        }).toList();
    }
}
