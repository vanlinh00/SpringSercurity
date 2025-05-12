package com.example.SpringSecurity.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtils {

    /**
     * Lấy thông tin người dùng đã đăng nhập
     * @return trả về đối tượng User hoặc null nếu không có người dùng đã đăng nhập
     */
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return (User) authentication.getPrincipal();
        }

        return null;
    }

    /**
     * Lấy tên người dùng đã đăng nhập
     * @return tên người dùng hoặc null nếu không có người dùng đăng nhập
     */
    public static String getCurrentUsername() {
        User currentUser = getCurrentUser();
        return currentUser != null ? currentUser.getUsername() : null;
    }

    /**
     * Lấy các quyền (roles) của người dùng đã đăng nhập
     * @return danh sách quyền của người dùng
     */
    public static List<String> getCurrentRoles() {
        User currentUser = getCurrentUser();

        if (currentUser != null) {
            return currentUser.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toList());
        }

        return List.of(); // trả về danh sách rỗng nếu không có người dùng đăng nhập
    }

    /**
     * Kiểm tra người dùng có quyền cụ thể không
     * @param role quyền cần kiểm tra
     * @return true nếu người dùng có quyền đó, ngược lại false
     */
    public static boolean hasRole(String role) {
        List<String> roles = getCurrentRoles();
        return roles.contains(role);
    }
}
