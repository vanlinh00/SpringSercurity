package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.service.CacheService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/users")
public class CacheController {
    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping("/raw/{id}")
    public ResponseEntity<Object> getRawCache(@PathVariable Long id) {
        Object cachedValue = cacheService.getCachedUserById(id);
        return ResponseEntity.ok(cachedValue);
    }



    @PostMapping("/update")
    public ResponseEntity<?> updateLocation(@RequestParam Long id) throws JsonProcessingException {
        cacheService.setCachedUserById(id);
        return ResponseEntity.ok("Location updated");
    }
}
