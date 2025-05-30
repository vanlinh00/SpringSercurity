package com.example.SpringSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // Bật @Async
public class AsyncConfig {
    /*

    Bản chất Bean printTaskExecutor là tạo pool thread sử sụng dụng cái thằng design pattern ObjectPooling ( queue)

     */
    @Bean(name = "printTaskExecutor")   //Đăng ký một Bean có tên "printTaskExecutor" để Spring quản lý.
    public Executor printTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // số thread cơ bản
        executor.setMaxPoolSize(20);// số thread tối đa
        executor.setQueueCapacity(100);  // hàng đợi
        executor.setThreadNamePrefix("PrintWorker-");
        executor.initialize();
        return executor;
    }


    @Bean (name = "taskExecutor-gps")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutorOrder() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20); // số thread cơ bản
        executor.setMaxPoolSize(50); // số thread tối đa
        executor.setQueueCapacity(1000); // hàng đợi
        executor.setThreadNamePrefix("OrderExecutor-");
        executor.initialize();
        return executor;
    }
}
