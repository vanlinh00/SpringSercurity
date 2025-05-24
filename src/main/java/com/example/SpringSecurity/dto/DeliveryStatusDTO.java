package com.example.SpringSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO dùng để truyền dữ liệu trạng thái đơn hàng qua Kafka
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatusDTO {
    private String orderId; // Mã đơn hàng
    private String status; // Trạng thái đơn hàng (DELIVERED, FAILED...)
    private String deliveredAt; // Thời gian giao hàng
}
