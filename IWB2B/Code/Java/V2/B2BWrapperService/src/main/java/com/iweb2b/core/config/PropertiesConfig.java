package com.iweb2b.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:application-messages.properties")
public class PropertiesConfig {

	@Value("${b2bmaster.oauth.credentials.client_id}")
	private String clientId;
	
	@Value("${b2bmaster.oauth.credentials.client_secret_key}")
	private String clientSecretKey;
	
	@Value("${b2bmaster.oauth.grant_type}")
	private String grantType;
	
	@Value("${b2bmaster.oauth.grant_type.username}")
	private String username;
	
	@Value("${b2bmaster.oauth.grant_type.password}")
	private String password;
	
	//-----------------------------------------------------------------------------------
	@Value("${b2bmaster.oauth.access_token_url}")
	private String b2bmasterAccessTokenUrl;
	
	@Value("${b2bintegration.oauth.access_token_url}")
	private String b2bintegrationAccessTokenUrl;
	
	@Value("${b2bportal.oauth.access_token_url}")
	private String b2bportalAccessTokenUrl;
	
	//-----------------------------------------------------------------------------------
	@Value("${api.b2bmaster.service.url}")
	private String b2bmasterServiceUrl;
	
	@Value("${api.b2bintegration.service.url}")
	private String b2bintegrationServiceUrl;
	
	@Value("${api.b2bportal.service.url}")
	private String b2bportalServiceUrl;


	//-----------------------------------------------------------------------------------
	// file.upload.location
	@Value("${file.upload.location}")
	private String fileUploadLocation;
	
	@Value("${file.uplpad.url}")
	private String fileUploadUrl;

	//-----------------------------------------------------------------------------------
	
	/*
	 * ------------LIFETIME_TOKEN--------------------------------------------------------
	 */
	//-----------------------------------------------------------------------------------
	// b2b-integration-token
	@Value("${b2b.integration.token}")
	private String b2bIntegrationToken;
	
	// b2b.integration.webhook.token
	@Value("${b2b.integration.webhook.token}")
	private String b2bIntegrationWebhookToken;
	
	/*
	 * #LIFETIME_TOKEN_AJX
	 * ajx.integration.token=$2a$10$qXQLvwrNowIUbiTDpytK1e5pjM.4vRufvBulmXCjBsvD0OvEq3epS
	 * ajx.integration.webhook.token=$2a$10$yB09LeUhaCldMYZAbYqtO.ch2f7HUN85y3K58SCOXAwREY4aMdpsG
	 */
	@Value("${ajx.integration.token}")
	private String ajxIntegrationToken;
	
	@Value("${ajx.integration.webhook.token}")
	private String ajxIntegrationWebhookToken;
	
	/*
	 * #LIFETIME_TOKEN_FLOW
	 * flow.integration.token=$2a$10$Vn4woOCOdaUquPUpQ5msuOgsUBYjGs2J9bw.88frzdJpZan9hKG52
	 */
	@Value("${flow.integration.token}")
	private String flowIntegrationToken;

	/*
	 * #LIFETIME_TOKEN_EMIRATES_POST
	 * flow.integration.token=$2a$10$WTKixiTNx8HKjT3/4M0g0OOwnOiUc1mrQSsR9JAtSt9aIWX9LKWaO
	 */
	@Value("${ep.integration.token}")
	private String epIntegrationToken;

}