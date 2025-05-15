package com.example.SpringSecurity.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class PrintService {

    @Async("printTaskExecutor")
    public CompletableFuture<String> printInvoice(String orderId) {
        try {
            // Tạo thư mục output nếu chưa có
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("output"));

            String fileName = "output/invoice_" + orderId + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Nội dung PDF
            document.add(new Paragraph("Đơn hàng: " + orderId));
            document.add(new Paragraph("Ngày in: " + LocalDateTime.now()));
            document.add(new Paragraph("Người in: Hệ thống giao hàng bưu cục"));
            document.add(new Paragraph("----------------------------------------"));

            document.close();

            String message = "✅ Đã in xong: " + fileName;
            System.out.println(message);
            return CompletableFuture.completedFuture(message);

        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture("❌ Lỗi khi in đơn " + orderId);
        }
    }
}
