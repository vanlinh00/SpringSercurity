package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.AppUser;
import com.example.SpringSecurity.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Lưu người dùng, mã hóa mật khẩu trước khi lưu vào DB
    public AppUser saveUser(AppUser user) {

        // Kiểm tra nếu username đã tồn tại
        if (userRepository.existsByUsername(user.getUsername())) {
            //   throw new RuntimeException("Username is already taken");
            throw new IllegalArgumentException("Username is already taken");

        }

        // Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    // Tìm người dùng bằng tên đăng nhập
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm người dùng từ cơ sở dữ liệu
        AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Trả về đối tượng UserDetails
        return new org.springframework.security.core.userdetails.User(user.getUsername(), // Tên người dùng
                user.getPassword(), // Mật khẩu đã mã hóa
                mapRolesToAuthorities(user.getRoles()) // Quyền của người dùng
        );

    }

    // Chuyển danh sách roles thành danh sách quyền (authorities)
    private List<SimpleGrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)) // Tạo authority từ mỗi role
                .collect(Collectors.toList());
    }


    // Redis cacheable dữ liệu

    //tự động lưu cache kết quả trả về của một phương thức
    @Cacheable(value = "users", key = "#username")
    public AppUser getUserByUsername(String username) {
        simulateSlowQuery();
        return userRepository.findByUsername(username).orElse(null);
    }
    /*
    Khi gọi hàm lần đầu:
       + Sẽ thực sự gọi method, truy vấn database.
       + Kết quả được lưu vào cache Redis với key "users::id".
    Khi gọi lần sau:
        + Nếu key "users::id" đã tồn tại trong cache, Spring không gọi lại method nữa.
        + Thay vào đó, trả kết quả từ cache luôn → rất nhanh.
     */

    @Cacheable(value = "users", key = "#id")
    public AppUser getUserById(Long id) {
        simulateSlowQuery();
        return userRepository.findById(id).orElse(null);
    }

    /*
     Lý do vì sao Redis nhanh hơn database (như MySQL, PostgreSQL):

     ✅ 1. Redis lưu dữ liệu trong RAM (bộ nhớ) còn MySQL/PostgreSQL lưu dữ liệu trên ổ cứng hoặc SSD.
     ✅ 2. Redis là key-value store đơn giản

     */
    public AppUser getUserByIdNoCache(Long id) {
        simulateSlowQuery();
        return userRepository.findById(id).orElse(null);
    }

    private void simulateSlowQuery() {
        try {
            Thread.sleep(2000); // giả lập độ trễ 2 giây
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
