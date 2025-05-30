package com.example.SpringSecurity.service.impl;


import com.example.SpringSecurity.entity.Location;
import com.example.SpringSecurity.repository.LocationRepository;
import com.example.SpringSecurity.utils.StrategyPartern.StorageStrategy;
import com.example.SpringSecurity.service.LocationService2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

//        // 👉 sử dụng Strategy Pattern để hoán đổi chiến lược lưu dữ liệu
@Service
public class LocationServiceImpl implements  LocationService2 {

    private final LocationRepository locationRepository;

    // Đây là hành động Tách rời hành vi (behavior) khỏi đối tượng.
    private final StorageStrategy storageStrategy; // Strategy Pattern

    public LocationServiceImpl(LocationRepository locationRepository,
                              // @Qualifier("redisStorageStrategy") StorageStrategy storageStrategy) {
                               @Qualifier("databaseStorageStrategy") StorageStrategy storageStrategy) {

        this.locationRepository = locationRepository;
        this.storageStrategy = storageStrategy; // chọn chiến lược lưu dữ liệu (Redis hoặc DB)
    }


    //Overriding: Ghi đè method từ lớp cha, ví dụ trong service extends interface.
    @Override
    public void saveLocation(Location location) {
        // 👉 sử dụng Strategy Pattern để hoán đổi chiến lược lưu dữ liệu
        storageStrategy.store(location); // có thể dùng Redis hoặc Database
    }

    @Override
    public List<Location> getLocationsByUser(Long userId) {
        return locationRepository.findByUserId(userId);
    }


}
