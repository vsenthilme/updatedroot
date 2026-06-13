package com.tekclover.wms.api.transaction.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="datasourceone.datasource")
@Getter
@Setter
public class DataSourceOneConfig {
    private String url;
    private String password;
    private String username;


}