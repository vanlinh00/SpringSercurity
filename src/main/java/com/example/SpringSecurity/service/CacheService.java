package com.example.SpringSecurity.service;
/*
✅ Cách lấy giá trị từ cache với key "users::id" t
 */

import com.example.SpringSecurity.entity.AppUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;



    /*
    các dùng JObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();

String json = objectMapper.writeValueAsString(myObject); // Java object -> JSON
MyClass obj = objectMapper.readValue(jsonString, MyClass.class); // JSON -> Java object

     */

    public void setCachedUserById(Long id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = "users:" + id;
        AppUser user = new AppUser();
        user.setId(id);
        String value = objectMapper.writeValueAsString(user);
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getCachedUserById(Long id) {
        String key = "users:" + id;
        return redisTemplate.opsForValue().get(key);
    }


}
