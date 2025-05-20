package com.example.SpringSecurity.repository;


import com.example.SpringSecurity.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser , Long> {

    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);

    // Tìm theo username và password
    Optional<AppUser> findByUsernameAndPassword(String username, String password);


}
