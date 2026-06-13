package com.almailem.ams.api.connector.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:application-messages.properties")
//@PropertySource("classpath:application-messages.properties")
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
	
	//-----------------------------------------------------------------------------------
	@Value("${inbound.transaction.oauth.access_token_url}")
	private String inboundTransactionAccessTokenUrl;

	@Value("${outbound.transaction.oauth.access_token_url}")
	private String outboundTransactionAccessTokenUrl;

	@Value("${misc.transaction.oauth.access_token_url}")
	private String miscTransactionAccessTokenUrl;
	
	@Value("${enterprise.oauth.access_token_url}")
	private String enterpriseAccessTokenUrl;
	
	@Value("${masters.oauth.access_token_url}")
	private String mastersAccessTokenUrl;
	
	@Value("${idmaster.oauth.access_token_url}")
	private String idmasterAccessTokenUrl;
	
	//-----------------------------------------------------------------------------------
	@Value("${api.inbound.transaction.service.url}")
	private String inboundTransactionServiceUrl;

	@Value("${api.outbound.transaction.service.url}")
	private String outboundTransactionServiceUrl;

	@Value("${api.misc.transaction.service.url}")
	private String miscTransactionServiceUrl;
	
	@Value("${api.enterprise.service.url}")
	private String enterpriseServiceUrl;
	
	@Value("${api.masters.service.url}")
	private String mastersServiceUrl;
	
	@Value("${api.idmaster.service.url}")
	private String idmasterServiceUrl;
}
