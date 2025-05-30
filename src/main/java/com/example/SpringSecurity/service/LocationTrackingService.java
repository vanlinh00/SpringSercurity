package com.example.SpringSecurity.service;


import com.example.SpringSecurity.dto.LocationDto;
import com.example.SpringSecurity.dto.SafeZoneDTO;
import com.example.SpringSecurity.dto.UserAppSafeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
public class LocationTrackingService {

    // ✅ 1. HashMap – cache tạm local (chỉ dùng demo/test)
    private final Map<Long, LocationDto> lastKnownLocationMap = new HashMap<>();

    // ✅ RedisTemplate để cache production
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // ✅ 2. equals() và hashCode() – dùng để kiểm tra vùng an toàn có thay đổi
    private final Map<Long, SafeZoneDTO> currentSafeZoneMap = new HashMap<>();

    // ✅ Cập nhật vị trí người dùng
    public void updateUserLocation(Long userId, LocationDto location) {

        // ✅ 3. Autoboxing – dùng Long (wrapper) thay vì long
        // ✅ 4. HashMap logic: hashCode -> hash -> bucket -> equals
        lastKnownLocationMap.put(userId, location); // demo local cache

        // ✅ 5. String immutability + StringBuilder (thay vì String +)
        String redisKey = "location:" + userId; // String là immutable
        redisTemplate.opsForValue().set(redisKey, location, Duration.ofMinutes(5));

        StringBuilder message = new StringBuilder();

        message.append("📍 Vị trí mới userId=").append(userId)
                .append(": (").append(location.getLatitude())
                .append(", ").append(location.getLongitude()).append(")");

        log.info(message.toString());
    }


    // ✅ Lấy vị trí gần nhất từ Redis
    public LocationDto getLastLocation(Long userId) {
        Object result = redisTemplate.opsForValue().get("location:" + userId);

        // ✅ 6. Java pass-by-value
        // Không thể làm: location = new LocationDTO() và mong muốn bên ngoài thay đổi

        if (result instanceof LocationDto) {
            return (LocationDto) result;
        }

        return null;
    }


    // ✅ Kiểm tra nếu user đi ra khỏi vùng an toàn
    public boolean isUserOutOfSafeZone(Long userId, LocationDto currentLocation) {
        SafeZoneDTO zone = currentSafeZoneMap.get(userId);
        if (zone == null) return false;

        double distance = calculateDistance(
                zone.getLat(), zone.getLng(),
                currentLocation.getLatitude(), currentLocation.getLongitude()
        );

        boolean isOutside = distance > zone.getRadius(); // tính bằng mét
        if (isOutside) {
            log.warn("⚠️ User {} đã RA KHỎI vùng an toàn ({}m > {}m)", userId, (int) distance, zone.getRadius());
        } else {
            log.info("✅ User {} vẫn trong vùng an toàn ({}m ≤ {}m)", userId, (int) distance, zone.getRadius());
        }

        return isOutside;
    }
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // Bán kính Trái Đất (mét)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // đơn vị: mét
    }


    private SafeZoneDTO calculateZoneFor(LocationDto location) {
        // dummy logic – bạn thay bằng logic geofence thực tế
        double lat = Math.floor(location.getLatitude());
        double lng = Math.floor(location.getLongitude());
        return new SafeZoneDTO(lat, lng, 100);
    }


    // ✅ So sánh trạng thái bằng equals(), không dùng ==
    public boolean isStatusChanged(String oldStatus, String newStatus) {
        return !Objects.equals(oldStatus, newStatus); // tránh NullPointerException
    }

    public void Main() {

        UserAppSafeDTO user1 = new UserAppSafeDTO("USER", "READ");
        UserAppSafeDTO user2 = new UserAppSafeDTO("USER", "READ");
        UserAppSafeDTO user3 = user1;

        // So sánh bằng ==
        System.out.println("user1 == user2 ? " + (user1 == user2)); // false
        System.out.println("user1 == user3 ? " + (user1 == user3)); // true

        // So sánh bằng equals()
        System.out.println("user1.equals(user2) ? " + user1.equals(user2)); // true
    }
}
