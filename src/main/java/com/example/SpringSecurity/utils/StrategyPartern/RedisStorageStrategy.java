package com.example.SpringSecurity.utils.StrategyPartern;

import com.example.SpringSecurity.entity.Location;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("redisStorageStrategy")
public class RedisStorageStrategy implements StorageStrategy {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisStorageStrategy(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void store(Location location) {
        // 👉 Strategy cụ thể: lưu vào Redis
        String key = "location:" + location.getUserId();
        redisTemplate.opsForValue().set(key, location);
    }
}
