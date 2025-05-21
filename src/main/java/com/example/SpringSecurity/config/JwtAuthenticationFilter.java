package com.example.SpringSecurity.config;

import com.example.SpringSecurity.security.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Bước 1:  Lấy giá trị Authorization từ header của request

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Bước 2:
        // Nếu không có Authorization header hoặc không bắt đầu bằng "Bearer ", thì bỏ qua filter này,
        // chuyển sang filter tiếp theo trong chuỗi (ví dụ: authorizeHttpRequests trong SecurityConfig)

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);  /// Bỏ qua xác thực JWT
            // chạy đến hàm Bước 3 authorizeHttpRequests() trong SecurityConfig
            return;
        }

        // BƯớc 3    // Cắt chuỗi "Bearer " để lấy ra phần token thật sự
        jwt = authHeader.substring(7);
        // BƯớc 4:     // Trích xuất username (hoặc email, ID...) từ trong JWT
        username = jwtUtil.extractUsername(jwt);

        //    // Nếu username tồn tại và chưa có Authentication trong SecurityContext (tránh gán lại)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           //         // Tải thông tin user từ hệ thống (thường là từ database)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //        // Kiểm tra xem token có hợp lệ với thông tin user đó không (hết hạn chưa, chữ ký đúng không, v.v.)
            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                //            // Tạo một đối tượng Authentication mới với thông tin user
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Gắn thêm thông tin request vào Authentication (ví dụ: IP, session, v.v.)

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Gán Authentication vào SecurityContext để các filter phía sau biết user đã xác thực

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Tiếp tục chuyển request đến filter tiếp theo trong chuỗi (ví dụ: authorizeHttpRequests)

        filterChain.doFilter(request, response);
    }
}
