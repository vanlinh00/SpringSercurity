package com.example.SpringSecurity.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/*
🎯 1. Wearable là gì?
Wearable là các thiết bị đeo trên cơ thể có thể đo lường và ghi lại các chỉ số sinh học hoặc hành vi cá nhân, ví dụ:

Thiết bị	Mô tả
Fitbit	Vòng tay thông minh chuyên đo nhịp tim, bước chân, giấc ngủ.
Apple Watch	Đồng hồ thông minh: đo nhịp tim, ECG, đếm bước, gửi cảnh báo rơi, gọi cấp cứu.
Garmin, Mi Band, Samsung Watch	Thiết bị đeo theo dõi vận động, tập luyện, sức khỏe, vị trí.

 */

/*

✅ Có 2 Cách Đồng Bộ Dữ Liệu từ Wearable:
Cách	Chủ động	Ưu điểm	Nhược điểm
1. Pull (lập lịch backend tự lấy)	✅ Backend chủ động	Dễ kiểm soát, đơn giản	Phải lập lịch, tốn tài nguyên
2. Push (thiết bị/ hãng tự đẩy)	✅ Wearable → Gửi webhook đến backend bạn	Realtime, nhanh	Khó hơn, phải cấu hình webhook, xác thực


→ Là cách 1: Pull – tức là lập job định kỳ để gọi API tới nền tảng của wearable (Fitbit, Apple, Garmin...) để lấy dữ liệu sức khỏe về, ví dụ:

// Gửi request GET đến Fitbit API
RestTemplate rest = new RestTemplate();
HttpHeaders headers = new HttpHeaders();
headers.setBearerAuth(accessToken); // token của user
HttpEntity<?> entity = new HttpEntity<>(headers);

ResponseEntity<String> response = rest.exchange(
    "https://api.fitbit.com/1/user/-/activities/heart/date/today/1d.json",
    HttpMethod.GET,
    entity,
    String.class
);

Kết quả trả về là JSON → bạn parse và lưu vào bảng PHR trong DB.


cách 2:
✅ Tùy loại wearable, việc gửi dữ liệu sẽ có 3 kiểu phổ biến:
Kiểu gửi	Mô tả	Ví dụ
Gửi theo sự kiện	Gửi mỗi khi có dữ liệu mới hoặc ngưỡng thay đổi	Đo nhịp tim mới, sleep kết thúc, người dùng đạt đủ bước
Gửi theo chu kỳ (polling)	Gửi định kỳ mỗi vài phút	Mỗi 15 phút gửi nhịp tim, mỗi 1 giờ gửi sleep
Gửi theo batch	Gửi gộp 1 lần (sáng hôm sau gửi data của cả đêm)	Dữ liệu giấc ngủ thường gửi sau khi kết thúc đêm ngủ



 */


/*

Câu hỏi rất hay! Việc chọn giữa Pull và Push (webhook) khi đồng bộ dữ liệu từ wearable còn tùy theo yêu cầu hệ thống, thiết bị hỗ trợ, và khả năng tích hợp của bên bạn. Dưới đây là phân tích thực tế của cả hai cách để bạn hiểu sâu và chọn được đúng kiến trúc.

🔁 1. Pull (Backend chủ động lấy dữ liệu)
🟢 Ưu điểm:
✅ Dễ triển khai: Bạn chỉ cần dùng RestTemplate, WebClient, hoặc HttpClient để gọi API public từ vendor.

✅ Dễ kiểm soát thời gian: Có thể lên lịch (cron job) theo ý muốn: mỗi 5p, 1h, 1 ngày, v.v.

✅ Ít bị phụ thuộc: Không cần bên vendor cấu hình gì.

🔴 Nhược điểm:
❌ Không realtime: Dữ liệu trễ 5p–30p (tùy lịch).

❌ Tốn tài nguyên hệ thống: Nếu gọi API hàng loạt (nhiều user), có thể bị throttling hoặc charge phí.

❌ Phải xử lý đồng bộ & retry khi lỗi: Phức tạp khi user đông.

🔔 2. Push (Webhook từ thiết bị/vendor)
🟢 Ưu điểm:
✅ Realtime: Nhận ngay khi user đo.

✅ Hiệu suất cao: Chỉ xử lý khi có sự kiện. Tiết kiệm CPU/backend.

✅ Dễ tích hợp với Queue/Kafka → async

🔴 Nhược điểm:
❌ Khó cấu hình hơn: Phải cung cấp callback URL, verify signature, đăng ký app với vendor.

❌ Khó debug khi có lỗi: Vì do vendor đẩy.

❌ Cần server public có HTTPS để nhận được webhook.

✅ Vậy dùng cái nào là tốt nhất?
Tình huống	Nên chọn
✔ Thiết bị không hỗ trợ webhook	✅ Pull
✔ Cần realtime, thiết bị có hỗ trợ webhook	✅ Push (webhook)
✔ Cần độ tin cậy cao, đảm bảo dữ liệu đầy đủ	✅ Kết hợp cả 2
✔ MVP, muốn làm nhanh trước	✅ Pull trước, push sau

📌 Trong thực tế:
🔁 Nhiều hệ thống lớn dùng cả 2 cách song song:

Webhook (push) để lấy realtime.

Pull định kỳ 1 lần/ngày để đối chiếu / lấy dữ liệu thiếu.

 */
/*
1. Pull (lập lịch backend tự lấy)	✅ Backend chủ động	Dễ kiểm soát, đơn giản	Phải lập lịch, tốn tài nguyên
 */
@Service
public class WearableSyncScheduler {

    // Giả lập gọi API để sync dữ liệu
    private final WearableApiService wearableApiService;

    public WearableSyncScheduler(WearableApiService wearableApiService) {
        this.wearableApiService = wearableApiService;
    }

    /**
     * Job chạy mỗi 5 phút (cron biểu thức phút/giờ/ngày/tháng/ngày-trong-tuần)
     */
    @Scheduled(cron = "0 */1 * * * *")  // every 5 minutes
    public void syncFromWearable() {
        System.out.println("[SYNC JOB] Start syncing data from wearable at " + LocalDateTime.now());

        try {
            wearableApiService.syncAllUsers();
        } catch (Exception ex) {
            System.err.println("❌ Sync failed: " + ex.getMessage());
        }

        System.out.println("[SYNC JOB] Done.");
    }
}
