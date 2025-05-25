package com.example.SpringSecurity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private Long id; // khóa chính

    private String status; // trạng thái đơn hàng

    @Column(name = "sender_id")
    private Long senderId; // ID người gửi (thay vì dùng @ManyToOne -> chỉ dùng ID)

    @Column(name = "receiver_id")
    private Long receiverId; // ID người nhận

    @Column(name = "post_office_id")
    private Long postOfficeId; // ID bưu cục phát
}
