package com.example.SpringSecurity.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class InvoiceService {

    /*

    ✅ I. Lý do cần chia làm 3 luồng (threads)

    🧠 1. Tối ưu hiệu suất - Tránh chờ đợi IO

    Tác vụ in phiếu gồm nhiều bước chậm như:

    Ghi file PDF (FileOutputStream, PdfWriter)

    Tạo barcode (mất CPU)

    Tạo thư mục, kiểm tra tồn tại

    Nếu xử lý tuần tự, mỗi tác vụ sẽ block toàn bộ hệ thống, khiến hiệu suất giảm nghiêm trọng.

➡️ Chạy bất đồng bộ (@Async) giúp xử lý song song nhiều đơn hàng, tăng tốc độ tổng thể.
     */
    @Async("printTaskExecutor")  // thread pool printTaskExecutor để chạy method
    public CompletableFuture<String> printInvoice(String orderId) {
        try {
            // Tạo thư mục output nếu chưa có
            Files.createDirectories(Paths.get("output"));

            String fileName = "output/invoice_" + orderId + ".pdf";
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Font tiếng Việt
            BaseFont bf = BaseFont.createFont("arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(bf, 16, Font.BOLD);
            Font normalFont = new Font(bf, 12);

            // Tiêu đề
            Paragraph title = new Paragraph("📦 MẪU PHIẾU GIAO HÀNG\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // 1. Thông tin đơn hàng
            document.add(new Paragraph("1. Thông tin đơn hàng:", titleFont));
            document.add(new Paragraph("Mã đơn hàng (Order ID): " + orderId, normalFont));
            document.add(new Paragraph("Ngày tạo đơn: " + LocalDateTime.now().toLocalDate(), normalFont));
            document.add(new Paragraph("Đơn vị vận chuyển: Shopee Xpress\n", normalFont));

            // 2. Người gửi
            document.add(new Paragraph("2. Thông tin người gửi:", titleFont));
            document.add(new Paragraph("Họ tên: Nguyễn Văn A", normalFont));
            document.add(new Paragraph("SĐT: 0988 123 456", normalFont));
            document.add(new Paragraph("Địa chỉ: 123 Đường ABC, Quận 1, TP.HCM\n", normalFont));

            // 3. Người nhận
            document.add(new Paragraph("3. Thông tin người nhận:", titleFont));
            document.add(new Paragraph("Họ tên: Trần Thị B", normalFont));
            document.add(new Paragraph("SĐT: 0909 654 321", normalFont));
            document.add(new Paragraph("Địa chỉ: 456 Đường XYZ, Quận Bình Thạnh, TP.HCM\n", normalFont));

            // 4. Bảng sản phẩm
            document.add(new Paragraph("4. Thông tin bưu gửi:", titleFont));
            PdfPTable table = new PdfPTable(new float[]{1, 4, 2, 3});
            table.setWidthPercentage(100);
            table.addCell(createCell("STT", bf, true));
            table.addCell(createCell("Tên sản phẩm", bf, true));
            table.addCell(createCell("Số lượng", bf, true));
            table.addCell(createCell("Giá (VND)", bf, true));

            table.addCell(createCell("1", bf, false));
            table.addCell(createCell("Áo thun nam", bf, false));
            table.addCell(createCell("2", bf, false));
            table.addCell(createCell("300,000", bf, false));

            table.addCell(createCell("2", bf, false));
            table.addCell(createCell("Quần jean nữ", bf, false));
            table.addCell(createCell("1", bf, false));
            table.addCell(createCell("450,000", bf, false));

            table.addCell(createCell("", bf, false));
            table.addCell(createCell("Tổng cộng", bf, true));
            table.addCell(createCell("3", bf, true));
            table.addCell(createCell("750,000", bf, true));

            document.add(table);
            document.add(Chunk.NEWLINE);

            // 5. COD & thanh toán
            document.add(new Paragraph("5. COD & Thanh toán:", titleFont));
            document.add(new Paragraph("Thu hộ COD: 750,000 VND", normalFont));
            document.add(new Paragraph("Đã thanh toán: ❌", normalFont));
            document.add(new Paragraph("Phương thức thanh toán: Thanh toán khi nhận hàng (COD)\n", normalFont));

            // 6. Mã vạch
            document.add(new Paragraph("6. Mã vạch (barcode):", titleFont));
            Barcode128 barcode = new Barcode128();
            barcode.setCode(orderId);
            Image barcodeImage = barcode.createImageWithBarcode(writer.getDirectContent(), null, null);
            barcodeImage.scalePercent(150);
            document.add(barcodeImage);

            document.close();

            String message = "✅ Đã in phiếu giao hàng: " + fileName;
            System.out.println(message);
            return CompletableFuture.completedFuture(message);

        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture("❌ Lỗi khi in phiếu giao hàng: " + orderId);
        }
    }

    private PdfPCell createCell(String content, BaseFont bf, boolean isHeader) {
        Font font = new Font(bf, 12, isHeader ? Font.BOLD : Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }
}
