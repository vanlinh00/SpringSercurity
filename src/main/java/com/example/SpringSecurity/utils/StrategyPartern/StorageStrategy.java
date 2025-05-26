package com.example.SpringSecurity.utils.StrategyPartern;

import com.example.SpringSecurity.entity.Location;

// Strategy Pattern (interface chiến lược)
public interface  StorageStrategy {
    void store(Location location);

}
