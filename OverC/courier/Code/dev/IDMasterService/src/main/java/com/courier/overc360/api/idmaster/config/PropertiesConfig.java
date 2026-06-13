package com.courier.overc360.api.idmaster.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:application-messages.properties")
public class PropertiesConfig {

    @Value("${api.wrapper.service.url}")
    private String wrapperServiceUrl;

    //------------------------------------------ErrorLog_folderName----------------------------------------------------
    @Value("${errorlog.folder.name}")
    private String errorLogFolderName;

}
