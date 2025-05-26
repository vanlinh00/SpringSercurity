package com.example.SpringSecurity.utils.StrategyPartern;

import com.example.SpringSecurity.entity.Location;
import com.example.SpringSecurity.repository.LocationRepository;
import org.springframework.stereotype.Component;

@Component("databaseStorageStrategy")
public class DatabaseStorageStrategy implements StorageStrategy {

    private final LocationRepository locationRepository;

    public DatabaseStorageStrategy(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void store(Location location) {
        // 👉 Strategy cụ thể: lưu vào Database (PostgreSQL)
        locationRepository.save(location);
    }
}
