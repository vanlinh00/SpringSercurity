package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    // Custom query nếu cần
    List<Location> findByUserId(Long userId);
}