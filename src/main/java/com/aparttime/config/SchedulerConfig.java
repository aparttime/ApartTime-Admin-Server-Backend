package com.aparttime.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public ScheduledExecutorService watchdogExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

}
