package com.courier.overc360.api.common.service;

import com.courier.overc360.api.common.config.PropertiesConfig;
import com.courier.overc360.api.common.controller.exception.BadRequestException;
import com.courier.overc360.api.common.model.auth.AuthToken;
import com.courier.overc360.api.common.model.pdf.UpdateCCR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Set;

@Slf4j
@Service
public class MidMileService {
	
	@Autowired
	PropertiesConfig propertiesConfig;
	
	@Autowired
	AuthTokenService authTokenService;
	
	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
	
	private String getMidmileServiceUrl () {
		return propertiesConfig.getMidmileServiceUrl();
	}
	
	
	//--------------------------------------------------------------------------------------------------------------------
	//POST
	public String updateCCRFromPdf(Set<UpdateCCR> updateCCRList) {
		try{
		HttpHeaders headers = new HttpHeaders();
			AuthToken authTokenForMidmileService = authTokenService.getMidmileServiceAuthToken();
			String authToken = authTokenForMidmileService.getAccess_token();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("User-Agent", "RestTemplate");
			headers.add("Authorization", "Bearer " + authToken);
			UriComponentsBuilder builder =
					UriComponentsBuilder.fromHttpUrl(getMidmileServiceUrl() + "ccr/update/pdf");
			HttpEntity<?> entity = new HttpEntity<>(updateCCRList, headers);
			ResponseEntity<String> result =
					getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
			log.info("result : " + result.getStatusCode());
			return result.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException("Exception while updating CCR" + e);
		}
	}

}