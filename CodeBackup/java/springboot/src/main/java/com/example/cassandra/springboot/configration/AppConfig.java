package com.example.cassandra.springboot.configration;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public @Bean CqlSession session() {
        return CqlSession.builder().withKeyspace("store").build();
    }
}