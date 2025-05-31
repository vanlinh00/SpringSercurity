package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.entity.AppUser;

import com.example.SpringSecurity.security.JwtTokenUtil;

import com.example.SpringSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/*
Chú ý quan trọng:
Backend cần cho phép CORS (Cross-Origin Resource Sharing) để frontend Angular (chạy trên port 4200)
có thể gọi API backend (chạy port 8080) không bị trình duyệt chặn.
 */
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUser user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String token = jwtUtil.generateToken(auth.getName()); // hoặc lấy từ UserDetails
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

