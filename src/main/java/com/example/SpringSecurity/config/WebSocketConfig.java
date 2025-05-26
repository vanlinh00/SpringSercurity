package com.example.SpringSecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    //✅ Ý nghĩa
    //Nó tạo ra một endpoint WebSocket có tên /ws, mà client (như HTML bạn đang dùng)
    // có thể kết nối vào từ địa chỉ http://localhost:8080/ws (mặc định port 8080).

   // Giống như bạn tạo một API /api/users để nhận HTTP request,
   // //thì đây là endpoint dùng để nhận kết nối WebSocket.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")    //Điểm kết nối WebSocket
                .setAllowedOriginPatterns("*")
                .withSockJS();          // enable SockJS fallback
    }

    //
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //kích hoạt Nhà môi giới đơn giản
        //, broker này sẽ lắng nghe và phát tin nhắn tới tất cả
        // //client nào đang subscribe các destination bắt đầu bằng /topic
        //Hoặc bạn có thể cấu hình dùng một broker bên ngoài, ví dụ RabbitMQ, ActiveMQ, Kafka,... (đây gọi là Full-featured broker).
        registry.enableSimpleBroker("/topic", "/queue");
        //Server gửi /topic/alerts → nhiều client nhận

        //đặt Tiền tố đích của ứng dụng
        registry.setApplicationDestinationPrefixes("/app");
        // Tiền tố đường dẫn client gửi tin đến server
        // Client gửi /app/location, server xử lý @MessageMapping("/location
    }
}
