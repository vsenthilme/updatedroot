package com.tekclover.wms.api.transaction.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="datasourcetwo.datasource")
@Getter
@Setter
public class DataSourceTwoConfig {

    private String url;
    private String password;
    private String username;


}