package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.LocationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// 2. RedisService: lưu và lấy vị trí hiện tại
@Service
public class LocationRedisService {
    private static final String PREFIX = "latest_location:";

    @Autowired
    private StringRedisTemplate redisTemplate;


    public void saveLocation(LocationDto location) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String key = PREFIX + location.getUserId();
        String value = mapper.writeValueAsString(location);
        redisTemplate.opsForValue().set(key, value);
    }

    public LocationDto getLocation(String userId) throws JsonProcessingException {
        String value = redisTemplate.opsForValue().get(PREFIX + userId);
        if (value == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(value, LocationDto.class);
    }

    public List<LocationDto> getLocations(List<String> userIds) throws JsonProcessingException {
        List<LocationDto> result = new ArrayList<>();
        for (String userId : userIds) {
            LocationDto dto = getLocation(userId);
            if (dto != null) result.add(dto);
        }
        return result;
    }
}