package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.OrderInfoDTO;
import com.example.SpringSecurity.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/public/orders")
public class OrderController {

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

//    @GetMapping("/async")
//    public ResponseEntity<String> processAsync() {
//        List<OrderInfoDTO> orders = generateOrders(100);
//        orderService.processOrdersAsync(orders);
//        return ResponseEntity.ok("Đang xử lý bất đồng bộ...");
//    }
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


}
