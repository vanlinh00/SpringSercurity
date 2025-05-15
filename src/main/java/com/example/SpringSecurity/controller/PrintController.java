package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api")
public class PrintController {

    @Autowired
    private PrintService printService;

    @GetMapping("public/print-all")
    public List<String> printAllInvoices() throws Exception {

        // Danh sách đơn hàng (giả lập)
        List<String> orderIds = IntStream.range(1, 20)
                .mapToObj(i -> "ORD-" + i)
                .collect(Collectors.toList());

        // Gửi các job in song song
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (String orderId : orderIds) {
            futures.add(printService.printInvoice(orderId));
        }

        // Chờ tất cả job hoàn tất
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Thu kết quả
        List<String> results = new ArrayList<>();
        for (CompletableFuture<String> future : futures) {
            results.add(future.get());
        }

        return results;
    }
}
