package com.example.SpringSecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")    //Điểm kết nối WebSocket
                .setAllowedOriginPatterns("*")
                .withSockJS();          // enable SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //kích hoạt Nhà môi giới đơn giản
        registry.enableSimpleBroker("/topic", "/queue");
        //Server gửi /topic/alerts → nhiều client nhận

        //đặt Tiền tố đích của ứng dụng
        registry.setApplicationDestinationPrefixes("/app");
        // Tiền tố đường dẫn client gửi tin đến server
        // Client gửi /app/location, server xử lý @MessageMapping("/location
    }
}
