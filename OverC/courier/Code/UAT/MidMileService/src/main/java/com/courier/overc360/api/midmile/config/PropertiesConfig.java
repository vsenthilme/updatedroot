package com.courier.overc360.api.midmile.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:application-messages.properties")
public class PropertiesConfig {

    @Value("${idmaster.oauth.credentials.client_id}")
    private String clientId;

    @Value("${idmaster.oauth.credentials.client_secret_key}")
    private String clientSecretKey;

    @Value("${idmaster.oauth.grant_type}")
    private String grantType;

    @Value("${idmaster.oauth.grant_type.username}")
    private String username;

    @Value("${idmaster.oauth.grant_type.password}")
    private String password;

//    //---------------------------------------TokenUrl----------------------------------------------
    @Value("${common.oauth.access_token_url}")
    private String commonAccessTokenUrl;

//    //---------------------------------------------ServiceUrl--------------------------------------
    @Value("${api.common.service.url}")
    private String commonServiceUrl;

    @Value("${api.wrapper.service.url}")
    private String wrapperServiceUrl;

    //------------------------------------------ErrorLog_folderName----------------------------------------------------
    @Value("${errorlog.folder.name}")
    private String errorLogFolderName;

}
