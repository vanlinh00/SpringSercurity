package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public access";
    }


    //
    @GetMapping("/user")
   @PreAuthorize("hasRole('USER')")
    public String userEndpoint() {
      //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = SecurityUtils.getCurrentUsername();
        List<String> roles = SecurityUtils.getCurrentRoles();

        // Thực hiện các hành động với thông tin người dùng
        System.out.println("User: " + username);
        System.out.println("Roles: " + roles);


        return "User access "+username+"roles "+roles;
    }

    @GetMapping("/admin")
   @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
      //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "Admin access";
    }
}
