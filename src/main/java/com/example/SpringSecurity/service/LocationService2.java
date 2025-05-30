package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.Location;

import java.util.List;


// Interface dùng Abstraction

public interface LocationService2 {
    void saveLocation(Location location);  // abstraction
    List<Location> getLocationsByUser(Long userId);
}
