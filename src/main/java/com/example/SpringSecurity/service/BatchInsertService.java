package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.AppUser;
import com.example.SpringSecurity.entity.UserOt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchInsertService {

    @PersistenceContext
    private EntityManager entityManager;
    // Inject EntityManager để thao tác trực tiếp với JPA thay vì dùng JpaRepository.
    // Dùng EntityManager thì bạn có thể gọi persist(), flush(), clear() chủ động.

    private static final int BATCH_SIZE = 1000;
    // Cứ sau mỗi 1000 record thì flush xuống database 1 lần.


    @Transactional // Đảm bảo toàn bộ phương thức chạy trong 1 transaction (nếu có lỗi sẽ rollback)
    public void insertMillionUsers() {

        // Ghi lại thời gian bắt đầu để đo hiệu năng
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= 1_000_000; i++) {

            // Lặp từ 1 đến 1 triệu để tạo 1 triệu bản ghi

            UserOt user = new UserOt("User " + i, "user" + i + "@example.com");
            // Tạo 1 đối tượng User mới với name và email theo index i

            entityManager.persist(user);
            // Ghi user này vào persistence context (chưa insert xuống DB thật)

            if (i % BATCH_SIZE == 0) {
                entityManager.flush();  // gửi batch xuống DB
                // flush: đẩy toàn bộ các entity trong persistence context xuống DB ngay lúc đó.
                // tránh giữ quá nhiều object trong bộ nhớ.

                entityManager.clear();  // xóa cache trong EntityManager
                // clear: xoá toàn bộ entity đã lưu trong persistence context,
                // tránh việc bị đầy bộ nhớ (OutOfMemory hoặc chậm dần).

                System.out.println("Inserted: " + i);
                // In ra số lượng bản ghi đã insert để theo dõi tiến trình
            }
        }

        // Sau khi kết thúc vòng lặp, nếu còn sót lại bản ghi nào < BATCH_SIZE thì flush lần cuối

        // Flush phần còn lại
        entityManager.flush();
        entityManager.clear();

        long endTime = System.currentTimeMillis();
        System.out.println("DONE: " + (endTime - startTime) / 1000.0 + "s");
    }
}
