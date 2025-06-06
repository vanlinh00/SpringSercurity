package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.Userto;
import com.example.SpringSecurity.exception.BadRequestException;
import com.example.SpringSecurity.exception.ResourceNotFoundException;
import com.example.SpringSecurity.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/users/{id}")
    public String TestController(@PathVariable int id) {
        if (id < 0) {
            throw new BadRequestException("ID không được âm");
        }
        if (id > 100) {
            throw new ResourceNotFoundException("Không tìm thấy user với ID = " + id);
        }
        return "Ok";
    }

    // ví dụ URL: GET/users?age=25?name=john
    @GetMapping("/users")
    public String getUser(
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(value = "name", required = false, defaultValue = "Anonymous") String name
    ) {
        return "Lọc user theo age = " + age + ", name = " + name;
    }


    @PostMapping("/users")
    public String setUser(@RequestBody Userto user) {

        // ví dụ xử lý user ở đây, ví dụ như lưu database học in ra log
        System.out.println(user);
        return "Ok"+user.toString();
    }


}
