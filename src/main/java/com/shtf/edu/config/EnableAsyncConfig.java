package com.shtf.edu.config;

import com.shtf.edu.config.properties.EnableAsyncProperties;
import lombok.Data;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * EnableAsyncConfig class
 *
 * @author chenlingyu
 * @date 2020/6/8 19:55
 */
@Configuration
@EnableAsync
@Data
@EnableConfigurationProperties(EnableAsyncProperties.class)
public class EnableAsyncConfig {

    private final EnableAsyncProperties enableAsyncProperties;

    @Bean
    public TaskExecutor taskExecutor() {
        //TODO上线前具体思考下异步现成池容量问题
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(enableAsyncProperties.getCorePoolSize());
        // 设置最大线程数
        executor.setMaxPoolSize(enableAsyncProperties.getMaxPoolSize());
        // 设置队列容量
        executor.setQueueCapacity(enableAsyncProperties.getQueueCapacity());
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(enableAsyncProperties.getKeepAliveSeconds());
        // 设置默认线程名称
        executor.setThreadNamePrefix(enableAsyncProperties.getThreadNamePrefix());
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
