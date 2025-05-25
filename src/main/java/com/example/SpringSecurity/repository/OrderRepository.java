package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.dto.OrderInfoDTO;
import com.example.SpringSecurity.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Lấy thông tin đơn hàng, trạng thái
// Join User 2 lần để lấy người gửi, người nhận
// Join PostOffice lấy thông tin bưu cục
    @Query("""
    SELECT new com.example.SpringSecurity.dto.OrderInfoDTO(
        o.id, o.status,
        sender.name, receiver.name,
        p.code, p.name,1000
    )
    FROM Order o
    JOIN User sender ON o.senderId = sender.id
    JOIN User receiver ON o.receiverId = receiver.id
    JOIN PostOffice p ON o.postOfficeId = p.id
""")
    List<OrderInfoDTO> fetchTop10000OrderInfo();


}
