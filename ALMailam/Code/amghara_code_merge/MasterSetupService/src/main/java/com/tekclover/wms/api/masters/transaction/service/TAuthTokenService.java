package com.tekclover.wms.api.masters.transaction.service;

import com.tekclover.wms.api.masters.exception.BadRequestException;
import com.tekclover.wms.api.masters.transaction.config.TPropertiesConfig;
import com.tekclover.wms.api.masters.transaction.model.auth.AXAuthToken;
import com.tekclover.wms.api.masters.transaction.model.auth.AXUserAuth;
import com.tekclover.wms.api.masters.transaction.model.auth.AuthToken;
import com.tekclover.wms.api.masters.transaction.model.auth.AuthTokenRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Service
public class TAuthTokenService {

	@Autowired
	TPropertiesConfig TPropertiesConfig;

	/**
	 * Returns RestTemplate Object
	 * @return
	 */
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		// Object Convertor
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter
				.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
		restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
		return restTemplate;
	}

	/**
	 * 
	 * @param apiUrl
	 * @param clientId
	 * @param clientSecretKey
	 * @param grantType
	 * @param oauthUserName
	 * @param oauthPassword
	 * @return
	 */
	private AuthToken generateOAuthToken(String apiUrl, String clientId, String clientSecretKey, String grantType,
			String oauthUserName, String oauthPassword) {
		// Client Id and Client Secret Key to be sent as part of header for
		// authentication
		String credentials = clientId + ":" + clientSecretKey;
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		HttpEntity<String> request = new HttpEntity<String>(headers);
		String accessTokenUrl = apiUrl;

		// AuthToken URL dynamically
		if (apiUrl.equalsIgnoreCase("wms-masters-service")) {
			accessTokenUrl = TPropertiesConfig.getMastersAccessTokenUrl();
		} else if (apiUrl.equalsIgnoreCase("wms-idmaster-service")) {
			accessTokenUrl = TPropertiesConfig.getIdmasterAccessTokenUrl();
		} else if (apiUrl.equalsIgnoreCase("wms-connector-service")) {
			accessTokenUrl = TPropertiesConfig.getConnectorAccessTokenUrl();
		} else {
			log.info("The given URL is not available. Quiting.");
			throw new BadRequestException("The given URL is not available. Quiting");
		}

		log.info("Access token url: " + accessTokenUrl);
		accessTokenUrl += "?grant_type=" + grantType + "&username=" + oauthUserName + "&password=" + oauthPassword;
		log.info("accessTokenUrl : " + accessTokenUrl);

		ResponseEntity<AuthToken> response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, request, AuthToken.class);
		log.info("Access Token Response ---------" + response.getBody());
		return response.getBody();
	}

	/**
	 * 
	 * @param authTokenRequest
	 * @return
	 */
	public AuthToken getAuthToken(AuthTokenRequest authTokenRequest) {
		return generateOAuthToken(authTokenRequest.getApiName(), authTokenRequest.getClientId(),
				authTokenRequest.getClientSecretKey(), authTokenRequest.getGrantType(),
				authTokenRequest.getOauthUserName(), authTokenRequest.getOauthPassword());
	}

	/**
	 * getSetupServiceAuthToken
	 * 
	 * @return
	 */
	public AuthToken getIDMasterServiceAuthToken() {
		// Generate AuthToken for IDMasterService
		AuthTokenRequest authTokenRequest = new AuthTokenRequest();
		authTokenRequest.setApiName("wms-idmaster-service");
		authTokenRequest.setClientId(TPropertiesConfig.getClientId());
		authTokenRequest.setClientSecretKey(TPropertiesConfig.getClientSecretKey());
		authTokenRequest.setGrantType(TPropertiesConfig.getGrantType());
		authTokenRequest.setOauthUserName(TPropertiesConfig.getUsername());
		authTokenRequest.setOauthPassword(TPropertiesConfig.getPassword());
		return getAuthToken(authTokenRequest);
	}

	/**
	 * getCommonServiceAuthToken
	 * 
	 * @return
	 */
	public AuthToken getMastersServiceAuthToken() {
		// Generate AuthToken for MastersService
		AuthTokenRequest authTokenRequest = new AuthTokenRequest();
		authTokenRequest.setApiName("wms-masters-service");
		authTokenRequest.setClientId(TPropertiesConfig.getClientId());
		authTokenRequest.setClientSecretKey(TPropertiesConfig.getClientSecretKey());
		authTokenRequest.setGrantType(TPropertiesConfig.getGrantType());
		authTokenRequest.setOauthUserName(TPropertiesConfig.getUsername());
		authTokenRequest.setOauthPassword(TPropertiesConfig.getPassword());
		return getAuthToken(authTokenRequest);
	}

	// Generate AuthToken for ConnectorService
	public AuthToken getConnectorServiceAuthToken() {
		AuthTokenRequest authTokenRequest = new AuthTokenRequest();
		authTokenRequest.setApiName("wms-connector-service");
		authTokenRequest.setClientId(TPropertiesConfig.getClientId());
		authTokenRequest.setClientSecretKey(TPropertiesConfig.getClientSecretKey());
		authTokenRequest.setGrantType(TPropertiesConfig.getGrantType());
		authTokenRequest.setOauthUserName(TPropertiesConfig.getUsername());
		authTokenRequest.setOauthPassword(TPropertiesConfig.getPassword());
		return getAuthToken(authTokenRequest);
	}
	
	/*------------------AX-API----AUTH-TOKEN---------------------------*/
	/**
	 * 
	 * @return
	 */
	public AXAuthToken generateAXOAuthToken() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		AXUserAuth axapiUserAuth = new AXUserAuth();
		axapiUserAuth.setUsername(TPropertiesConfig.getAxapiAccessTokenUsername());
		axapiUserAuth.setPassword(TPropertiesConfig.getAxapiAccessTokenPassword());
		
		HttpEntity<?> entity = new HttpEntity<>(axapiUserAuth, headers);
		String accessTokenUrl = TPropertiesConfig.getAxapiAccessTokenUrl();
		log.info("Access token url: " + accessTokenUrl);
		
		ResponseEntity<AXAuthToken> response = 
				restTemplate.exchange(accessTokenUrl, HttpMethod.POST, entity, AXAuthToken.class);
		log.info("Access Token Response ---------" + response.getBody());
		return response.getBody();
	}
}