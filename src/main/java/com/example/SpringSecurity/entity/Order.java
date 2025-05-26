package com.example.SpringSecurity.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // khóa chính

    @Column(name = "status")
    private String status; // trạng thái đơn hàng

    @Column(name = "sender_id")
    private Long senderId; // ID người gửi (thay vì dùng @ManyToOne -> chỉ dùng ID)

    @Column(name = "receiver_id")
    private Long receiverId; // ID người nhận

    @Column(name = "post_office_id")
    private Long postOfficeId; // ID bưu cục phát

    @Column(name = "driver_id")
    private Long driverId;

}
