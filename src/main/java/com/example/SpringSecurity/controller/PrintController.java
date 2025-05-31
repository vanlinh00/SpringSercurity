package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.service.InvoiceService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")  // cho phép frontend truy cập

public class PrintController {

    @Autowired
    private InvoiceService printService;

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



    @GetMapping("public/download-all")
    public ResponseEntity<Resource> downloadAllInvoices() throws IOException {
        String zipFileName = "output/invoices.zip";
        Path zipPath = Paths.get(zipFileName);

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            Files.list(Paths.get("output"))
                    .filter(p -> p.toString().endsWith(".pdf"))
                    .forEach(path -> {
                        try {
                            ZipEntry entry = new ZipEntry(path.getFileName().toString());
                            zos.putNextEntry(entry);
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }

        Resource resource = new UrlResource(zipPath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"invoices.zip\"")
                .body(resource);
    }

}
