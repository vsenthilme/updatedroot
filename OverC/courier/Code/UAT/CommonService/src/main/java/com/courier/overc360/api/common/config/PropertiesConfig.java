package com.courier.overc360.api.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:application-messages.properties")
public class PropertiesConfig {

    @Value("${midmile.oauth.credentials.client_id}")
    private String clientId;

    @Value("${midmile.oauth.credentials.client_secret_key}")
    private String clientSecretKey;

    @Value("${midmile.oauth.grant_type}")
    private String grantType;

    @Value("${midmile.oauth.grant_type.username}")
    private String username;

    @Value("${midmile.oauth.grant_type.password}")
    private String password;

    //    //---------------------------------------TokenUrl----------------------------------------------
    @Value("${midmile.oauth.access_token_url}")
    private String midmileAccessTokenUrl;

    //    //---------------------------------------------ServiceUrl--------------------------------------
    @Value("${api.midmile.service.url}")
    private String midmileServiceUrl;


//--------------FILE UPLOAD-------------------------------------------------------------

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    @Value("${doc.storage.base.path}")
    private String docStorageBasePath;

    @Value("${doc.storage.document.path}")
    private String docStorageDocumentPath;

}