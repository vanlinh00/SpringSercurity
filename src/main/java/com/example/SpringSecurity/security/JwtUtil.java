//package com.example.SpringSecurity.security;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    //// Tạo khóa an toàn 256 bits cho HMACSHA256
//   // private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    private final String secretKey;
//
//    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
//        if (secretKey == null || secretKey.isBlank()) {
//            throw new IllegalArgumentException("Secret key cannot be null or blank");
//        }
//        this.secretKey = secretKey;
//    }
//
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .signWith(secretKey)  // Sử dụng secretKey đã tạo
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)  // Sử dụng secretKey khi xác thực
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()));
//    }
//
//
//}
