package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.config.PropertiesConfig;
import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.auth.AuthToken;
import com.courier.overc360.api.midmile.primary.model.consignment.AddConsignment;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.consignment.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

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

	private String getLastMileServiceUrl () {
		return propertiesConfig.getLastmileServiceUrl();
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

	//POST
	public String createConsignment(List<AddConsignment> addConsignment, String loginUserID, String masterAirwayBill) {
		try{
		HttpHeaders headers = new HttpHeaders();
			AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
			String authToken = authTokenForCommonService.getAccess_token();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("User-Agent", "ClassicWMS RestTemplate");
			headers.add("Authorization", "Bearer " + authToken);
			UriComponentsBuilder builder =
					UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "consignment")
							.queryParam("loginUserID", loginUserID)
							.queryParam("masterAirwayBill", masterAirwayBill);
			HttpEntity<?> entity = new HttpEntity<>(addConsignment, headers);
			ResponseEntity<String> result =
					getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
			log.info("result : " + result.getStatusCode());
			return result.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException("Exception : " + e);
		}
	}

	//POST
	@Transactional
	public void createConsignment(List<ConsignmentEntity> addConsignment, String masterAirwayBill) {
		try{
		HttpHeaders headers = new HttpHeaders();
			AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
			String authToken = authTokenForCommonService.getAccess_token();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("User-Agent", "ClassicWMS RestTemplate");
			headers.add("Authorization", "Bearer " + authToken);
			UriComponentsBuilder builder =
					UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "consignment/v2")
							.queryParam("masterAirwayBill", masterAirwayBill);
			HttpEntity<?> entity = new HttpEntity<>(addConsignment, headers);
			ResponseEntity<String> result =
					getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
			log.info("create cassandra result : " + result.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException("Exception : " + e);
		}
	}

	// Delivery To Consignment Create
	public Delivery[] createDeliveryFromConsignment(List<ConsignmentEntity> consignmentEntityList, String loginUserID) {
		try {
			HttpHeaders headers = new HttpHeaders();
			AuthToken authTokenForCommonService = authTokenService.getLastMileServiceAuthToken();
			String authToken = authTokenForCommonService.getAccess_token();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("User-Agent", "RestTemplate");
			headers.add("Authorization", " Bearer " + authToken);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/consignment")
					.queryParam("loginUserID", loginUserID);
			HttpEntity<?> entity = new HttpEntity<>(consignmentEntityList, headers);
			ResponseEntity<Delivery[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Delivery[].class);
			return result.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
			throw new BadRequestException("Exception : " + e);
		}
	}

}