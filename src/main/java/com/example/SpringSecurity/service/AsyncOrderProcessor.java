package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.OrderInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncOrderProcessor {


    @Async("taskExecutor")
    public CompletableFuture<Void> processOrderAsync(OrderInfoDTO order) {
        System.out.println(Thread.currentThread().getName() + " - Processing order id: " + order.getOrderId());
        processOrder(order);
        return CompletableFuture.completedFuture(null);
    }

    public List<OrderInfoDTO> processOrdersSync(List<OrderInfoDTO> orders) {
        long start = System.currentTimeMillis();

        for (OrderInfoDTO order : orders) {
            processOrder(order); // chạy từng đơn một
        }

        long end = System.currentTimeMillis();
        System.out.println("SYNC total time: " + (end - start) + " ms");

        return orders;
    }

    public void processOrder(OrderInfoDTO order) {
//        if (!validateOrder(order)) {
//            System.out.println("Order " + order.getOrderId() + ": Validation failed");
//            sendEmail(order, "Đơn hàng không hợp lệ, vui lòng kiểm tra lại.");
//            return;
//        }
//
//        String logisticsStatus = callLogisticsApi(order);
//        if (!"OK".equals(logisticsStatus)) {
//            System.out.println("Order " + order.getOrderId() + ": Logistics status - " + logisticsStatus);
//            sendEmail(order, "Đơn hàng bị trì hoãn do logistics.");
//            return;
//        }
//
//        updateOrderStatus(order, "Đang vận chuyển");

        sendEmail(order, "Đơn hàng đang được vận chuyển.");
    }

    private boolean validateOrder(OrderInfoDTO order) {
        // Check trạng thái
        if (!"Chờ xử lý".equals(order.getStatus())) return false;

        // Check địa chỉ đầy đủ
        if (order.getSenderAddress() == null || order.getReceiverAddress() == null) return false;

        // Check số lượng hàng
        if (order.getQuantity() <= 0) return false;

        return true;
    }



    private String callLogisticsApi(OrderInfoDTO order) {
        try {
            // Giả lập gọi API chậm 100ms
            Thread.sleep(100);

            // Giả lập trả về trạng thái OK hoặc Delay
            if (order.getOrderId() % 10 == 0) {
                return "Delay"; // mỗi 10 đơn hàng có 1 đơn bị delay
            }

            return "OK";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error";
        }
    }

    private void updateOrderStatus(OrderInfoDTO order, String newStatus) {
        order.setStatus(newStatus);
        order.setUpdatedTime(LocalDateTime.now());
        System.out.println("Order " + order.getOrderId() + " status updated to " + newStatus);
    }

    private void sendEmail(OrderInfoDTO order, String message) {
        try {
            // Giả lập gửi mail chậm 50ms
            Thread.sleep(100);
            System.out.println("Email sent to order " + order.getOrderId() + ": " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
