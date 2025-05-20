package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.entity.AppUser;
import com.example.SpringSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public/users")
public class UserController {

    @Autowired
    private UserService userService;

    /*

    1. Có 2 cách Cached

      +    redisTemplate.opsForValue().set(key, value);
      +     @GetMapping("/cached/{id}")

     */

    @GetMapping("/cached/{id}")
    public ResponseEntity<Map<String, Object>> getUserCached(@PathVariable Long id) {
        long start = System.currentTimeMillis();
        AppUser user = userService.getUserById(id);
        long end = System.currentTimeMillis();
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("time_ms", end - start);
        result.put("from_cache", true);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/cached/username/{username}")
    public ResponseEntity<Map<String, Object>> getUserCachedByUsername(@PathVariable String username) {
        long start = System.currentTimeMillis();
        AppUser user = userService.getUserByUsername(username); // Gọi service có dùng @Cacheable
        long end = System.currentTimeMillis();
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("time_ms", end - start);
        result.put("from_cache", true);
        return ResponseEntity.ok(result);
    }



    @GetMapping("/no-cache/{id}")
    public ResponseEntity<Map<String, Object>> getUserNoCache(@PathVariable Long id) {
        long start = System.currentTimeMillis();
        AppUser user = userService.getUserByIdNoCache(id);
        long end = System.currentTimeMillis();

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("time_ms", end - start);
        result.put("from_cache", false);
        return ResponseEntity.ok(result);
    }
}
