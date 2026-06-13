package com.courier.overc360.api.batch.config.singleton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("accountService")
    public AccountService getAccountService() {
        return new AccountService();
    }
}