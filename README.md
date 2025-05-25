
## 📡 1. Socket – Giao tiếp mạng hai chiều

### 🔹 Khái niệm:

* Socket là một **giao diện lập trình mạng** cho phép 2 chương trình trao đổi dữ liệu với nhau qua giao thức TCP hoặc UDP.
* **Client – Server**: Một phía tạo socket để lắng nghe (server), phía còn lại kết nối và gửi dữ liệu (client).

### 🔹 Luồng hoạt động:

1. **Server socket** tạo ra và bắt đầu lắng nghe trên một port.
2. **Client socket** gửi yêu cầu kết nối tới server.
3. Sau khi kết nối được thiết lập:

   * Dữ liệu có thể được truyền **2 chiều**.
   * Mỗi kết nối được quản lý qua một thread (hoặc async non-blocking).

### 📌 Minh họa:

![socket](https://github.com/user-attachments/assets/97e44093-1bbe-42e3-b22e-5d15e0859688)

* Mỗi client khi kết nối sẽ được xử lý bởi một thread riêng biệt.
* Server có thể bị quá tải nếu quá nhiều kết nối đồng thời.

---

## 🧬 2. Kafka – Hệ thống xử lý log và stream real-time

### 🔹 Khái niệm:

Apache Kafka là một hệ thống message broker theo kiến trúc **pub-sub (publish-subscribe)** với khả năng xử lý log **theo thời gian thực** và cực kỳ **scalable**.

### 🔹 Các thành phần chính:

| Thành phần    | Mô tả                            |
| ------------- | -------------------------------- |
| **Producer**  | Gửi message vào Kafka            |
| **Consumer**  | Nhận message từ Kafka            |
| **Broker**    | Server Kafka nhận và lưu message |
| **Topic**     | Chủ đề phân loại message         |
| **Partition** | Kafka chia nhỏ topic để scale    |

### 🔹 Minh họa:

![1\_Partitions](https://github.com/user-attachments/assets/934da0f3-5d83-40f4-85f9-eef7a3786994)
![3\_partitions](https://github.com/user-attachments/assets/3c6d2f8e-8889-49d5-882c-31649f0df851)
![So sanh](https://github.com/user-attachments/assets/91e54488-02e2-4231-9c94-72b5f161d099)

* Mỗi consumer có thể đọc message song song theo partition.
* Kafka đảm bảo tính **durability** và **high-throughput** cho message streaming.

---

## ⏳ 3. Async vs Sync trong lập trình

### 🔹 Đồng bộ (Synchronous):

* Mỗi tác vục diễn ra **tuần tự**, chờ đến khi hoàn tất rồi mới chạy câu lệnh tiếp theo.
* Đọn giản, an toàn, dễ debug.
* **Nhược điểm**: Gây tự nghẽn, hiệu suất thấp với tác vục chậm (I/O).

### 🔹 Bất đồng bộ (Asynchronous):

* Các tác vục chạy song song, **không chờ đổi**.
* Cải thiện đáng kể hiệu suất hệ thống.
* Cần quản lý thread/ExecutorService hoặc framework async (điển hình: `@Async` trong Spring).

### 🔹 Minh họa:

![sysnc](https://github.com/user-attachments/assets/8d7ace99-6701-485e-b3be-28018e781322)
![sothread](https://github.com/user-attachments/assets/2dff3d10-ce6d-4c3c-8fd4-e87803283ff8)
![sosanh Aysnc vs sysnc](https://github.com/user-attachments/assets/a9584bd7-e30c-404b-8605-4d3b5d1f1b64)
![Screenshot 2025-05-25 172237](https://github.com/user-attachments/assets/cc1b7a89-1eb5-408f-97ac-baa7171cbeb4)
![Async](https://github.com/user-attachments/assets/057f9b32-7827-4e42-a724-f2a46bfac7fd)

---

> ✨ Tài liệu tổng hợp dành cho backend developer nắm vững kiến thức core về Socket, Kafka và Async/Synchronous Programming.
