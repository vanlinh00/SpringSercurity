package com.example.SpringSecurity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderInfoDTO   {
    private Long orderId;
    private String status;

    private String senderName;
    private String receiverName;

    private String postOfficeCode;
    private String postOfficeName;



    private String senderAddress;

    private String receiverAddress;

    private int quantity;
    private double weight; // đơn vị: kg
    private String serviceType; // VD: "Tiêu chuẩn", "Nhanh", "Sieu toc"
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    // Constructor dùng để map dữ liệu từ query
    public OrderInfoDTO(Long orderId, String status,
                        String senderName, String receiverName,
                        String postOfficeCode, String postOfficeName,Integer quantity) {
        this.orderId = orderId;
        this.status = status;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.postOfficeCode = postOfficeCode;
        this.postOfficeName = postOfficeName;
        this.quantity = quantity;
    }

    // Các getter/setter nếu cần (hoặc dùng Lombok @Data cho ngắn gọn)
}
