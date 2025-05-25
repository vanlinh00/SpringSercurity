package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.OrderInfoDTO;
import com.example.SpringSecurity.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {
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

}
