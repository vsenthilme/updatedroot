package com.courier.overc360.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@org.springframework.scheduling.annotation.EnableAsync
public class WrapperServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
        SpringApplication.run(WrapperServiceApplication.class, args);
    }
}
