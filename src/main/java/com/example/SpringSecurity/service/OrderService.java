package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.OrderInfoDTO;
import com.example.SpringSecurity.dto.request.OrderRequest;
import com.example.SpringSecurity.entity.Order;
import com.example.SpringSecurity.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    // Bài  về Async và sync đồng bộ và async bất đồng bộ
    @Autowired
    private AsyncOrderProcessor asyncOrderProcessor;

    public List<OrderInfoDTO> processOrdersSync(List<OrderInfoDTO> orders) {
        asyncOrderProcessor.processOrdersSync(orders);
        return orders;
    }

    public void handleOrdersAsync(List<OrderInfoDTO> orders) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (OrderInfoDTO order : orders) {
            futures.add(asyncOrderProcessor.processOrderAsync(order));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }



    /*
 ✅ Vấn đề đặt ra (trước khi dùng cache)

Giả sử hệ thống quản lý giao hàng có:

1.000 tài xế đang hoạt động mỗi ngày.

Mỗi tài xế giao trung bình 10 đơn hàng/ngày.

Mỗi lần tài xế vào app để xem đơn hàng cần giao, app gọi API /orders/active/{driverId}
→ hệ thống truy vấn từ PostgreSQL.

➤ Như vậy, chỉ riêng một vòng đời tài xế mở app ~5 lần/ngày, hệ thống sẽ thực hiện:

1.000 tài xế × 5 lần × query DB = 5.000 query DB/ngày chỉ để lấy đơn hàng đang giao.

⛔ Hậu quả:

Tăng tải không cần thiết lên DB (mỗi query đều phải JOIN, WHERE status='DELIVERING', v.v.).

Trễ response → trải nghiệm tài xế kém.

Dễ dẫn đến nghẽn DB khi số lượng tài xế tăng lên (scalability kém).

     */

    private final OrderRepository orderRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public OrderService(OrderRepository orderRepository,
                        RedisTemplate<String, Object> redisTemplate,
                        ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }



    public void createOrder( OrderRequest orderRequest) {

        Order order = new Order();
        order.setDriverId(orderRequest.getDriverId());
        order.setStatus(orderRequest.getStatus());

        orderRepository.save(order);
        // 🔥 Xoá cache đơn hàng đang giao của tài xế này (nếu có)
        String key = "active_orders:driver:" + order.getDriverId();
        redisTemplate.delete(key);
    }

    // 🔹 Lấy danh sách đơn hàng đang giao của tài xế, có cache
    public List<Order> getActiveOrdersForDriver(Long driverId) throws JsonProcessingException {
        String key = "active_orders:driver:" + driverId;

        // Kiểm tra cache
        if (redisTemplate.hasKey(key)) {
            String json = (String) redisTemplate.opsForValue().get(key);
            Order[] orders = objectMapper.readValue(json, Order[].class);
            return Arrays.asList(orders);
        }

        // Nếu cache không có -> truy vấn DB và cache lại
        List<Order> orders = orderRepository.findByDriverIdAndStatus(driverId, "DELIVERING");
        String json = objectMapper.writeValueAsString(orders);
        redisTemplate.opsForValue().set(key, json, Duration.ofMinutes(10)); // TTL: 10 phút

        return orders;
    }

    // 🔹 Cập nhật trạng thái đơn hàng và xóa cache
    public void updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(newStatus);
        orderRepository.save(order);

        // Xóa cache đơn hàng của tài xế tương ứng
        String key = "active_orders:driver:" + order.getDriverId();
        redisTemplate.delete(key);
    }



}
