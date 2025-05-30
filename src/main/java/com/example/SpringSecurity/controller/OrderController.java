package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.OrderInfoDTO;
import com.example.SpringSecurity.dto.request.OrderRequest;
import com.example.SpringSecurity.entity.Order;
import com.example.SpringSecurity.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/public/orders")
public class OrderController {

    /* Async và sync */

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/sync")
    public ResponseEntity<String> processSync() {
        List<OrderInfoDTO> orders = generateOrders(100); // hoặc lấy từ DB
        orderService.processOrdersSync(orders);
        return ResponseEntity.ok("Đã xử lý đồng bộ xong");
    }

    @GetMapping("/process-orders-async")
    public ResponseEntity<String> processOrdersAsync() {
        List<OrderInfoDTO> orders = generateOrders(100);
        long start = System.currentTimeMillis();

        orderService.handleOrdersAsync(orders);

        long end = System.currentTimeMillis();
        return ResponseEntity.ok("ASYNC total time: " + (end - start) + " ms");
    }

    private List<OrderInfoDTO> generateOrders(int count) {
        List<OrderInfoDTO> orders = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            OrderInfoDTO order = new OrderInfoDTO(
                    (long) i,
                    "Chờ xử lý",
                    "Vanlinh",
                    "Tlinh",
                    "HN100",
                    "Post ha nội",
                    3000
            );
            orders.add(order);
        }

        return orders;
    }

/*
    ✅ Giải pháp: Dùng Redis làm Cache

    Redis là in-memory database → tốc độ truy xuất rất nhanh (micro giây).

    Khi tài xế lần đầu mở app → truy vấn DB rồi cache vào Redis.

    Những lần sau → app lấy luôn từ Redis → không query DB nữa.
*/

    // 🔹 Controller
    @PostMapping("create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return ResponseEntity.ok("Order created");
    }

    // 🔹 API lấy đơn hàng đang giao (có dùng cache)
    @GetMapping("/active/{driverId}")
    public ResponseEntity<List<Order>> getActiveOrders(@PathVariable Long driverId) throws JsonProcessingException, JsonProcessingException {
        List<Order> orders = orderService.getActiveOrdersForDriver(driverId);
        return ResponseEntity.ok(orders);
    }

    // 🔹 API cập nhật trạng thái đơn hàng
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId,
                                                    @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Updated");
    }
}
