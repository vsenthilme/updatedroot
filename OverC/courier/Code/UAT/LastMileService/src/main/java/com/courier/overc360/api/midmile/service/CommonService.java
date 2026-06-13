package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.config.PropertiesConfig;
import com.courier.overc360.api.midmile.primary.model.auth.AuthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

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
	
	private String getCommonServiceUrl () {
		return propertiesConfig.getCommonServiceUrl();
	}
	
	
	//--------------------------------------------------------------------------------------------------------------------
	//POST
	public String downLoadDocument(String sourceUrl, String destinationDir, String documentName) {
		try{
		HttpHeaders headers = new HttpHeaders();
			AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
			String authToken = authTokenForCommonService.getAccess_token();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("User-Agent", "ClassicWMS RestTemplate");
			headers.add("Authorization", "Bearer " + authToken);
			UriComponentsBuilder builder =
					UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "pdf/v2/download")
							.queryParam("sourceUrl", sourceUrl)
							.queryParam("destinationDir", destinationDir)
							.queryParam("documentName", documentName);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			ResponseEntity<String> result =
					getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
			log.info("result : " + result.getStatusCode());
			return result.getBody();
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}

}