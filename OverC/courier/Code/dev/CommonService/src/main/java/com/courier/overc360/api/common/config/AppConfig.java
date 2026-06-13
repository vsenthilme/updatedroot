package com.courier.overc360.api.common.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public @Bean CqlSession session() {
        return CqlSession.builder().withKeyspace("overc").build();
    }
}