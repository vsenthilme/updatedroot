package com.mnrclara.api.setup.service;

import java.net.http.HttpClient;
import java.util.Collections;
import java.util.List;

import com.mnrclara.api.setup.model.auth.AuthToken;
import com.mnrclara.api.setup.model.notification.HhtNotification;
import com.mnrclara.api.setup.model.notificationmessage.NotificationMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mnrclara.api.setup.config.PropertiesConfig;
import com.mnrclara.api.setup.model.clientuser.EMail;
import com.mnrclara.api.setup.model.commonservice.SMS;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonService {
	
	@Autowired
	PropertiesConfig propertiesConfig;
	
	@Autowired
	AuthTokenService authTokenService;

	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
	
	private String getCommonServiceApiUrl () {
		return propertiesConfig.getCommonServiceUrl();
	}
	
	private String getManagementUrl() {
		return propertiesConfig.getManagementServiceUrl();
	}
	
	/**
	 * 
	 * @param toNumber
	 * @param textMessage
	 * @param authToken
	 * @return
	 */
	public Boolean sendSMS(Long toNumber, String textMessage, String authToken) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("User-Agent", "MNRClara's RestTemplate");
			headers.add("Authorization", "Bearer " + authToken);
			SMS sms = new SMS();
			sms.setToNumber(toNumber);
			sms.setTextMessage(textMessage);
			
			HttpEntity<?> entity = new HttpEntity<>(sms, headers);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceApiUrl() + "communication/sendSMS");
			ResponseEntity<Boolean> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Boolean.class);
			log.info("result : " + result.getStatusCode());
			return result.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @param email
	 * @param authToken
	 * @return
	 */
	public String sendEmail(EMail email, String authToken) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("User-Agent", "MNRClara's RestTemplate");
			headers.add("Authorization", "Bearer " + authToken);
			HttpEntity<?> entity = new HttpEntity<>(email, headers);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceApiUrl() + "communication/sendEmail");
			ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
			log.info("result : " + result.getStatusCode());
			return result.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	// Send Notification
	public String sendNotification(NotificationMsg notificationMsg, String loginUserID) {
		try {
			log.info("Send Notification in mangagement -------- ------------- >");
			HttpHeaders headers = new HttpHeaders();
			AuthToken authToken = authTokenService.getManagementServiceAuthToken();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("User-Agent", "MNRClara's RestTemplate");
			headers.add("Authorization", "Bearer " + authToken.getAccess_token());
			HttpEntity<?> entity = new HttpEntity<>(notificationMsg, headers);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getManagementUrl() + "pushNotification/send")
					.queryParam("loginUserID", loginUserID);
			ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
			log.info("result : " + result.getStatusCode());
			return result.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// Send Notification
	public HhtNotification[] getToken(String classId, String userId) {
		try {
			log.info("Get Token --------> Management Service");
			HttpHeaders headers = new HttpHeaders();
			AuthToken authToken = authTokenService.getManagementServiceAuthToken();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("User-Agent", "MNRClara's RestTemplate");
			headers.add("Authorization", "Bearer " + authToken.getAccess_token());
			HttpEntity<?> entity = new HttpEntity<>(headers);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getManagementUrl() + "hhtnotification/token")
					.queryParam("classId", classId)
					.queryParam("userId", userId);
			ResponseEntity<HhtNotification[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, HhtNotification[].class);
			log.info("result : " + result.getStatusCode());
			return result.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
