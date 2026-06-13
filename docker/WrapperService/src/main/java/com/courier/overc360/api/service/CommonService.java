package com.courier.overc360.api.service;

import com.courier.overc360.api.config.PropertiesConfig;
import com.courier.overc360.api.model.auth.AuthToken;
import com.courier.overc360.api.model.dto.PDFMerger;
import com.courier.overc360.api.model.dto.UpdateCCR;
import com.courier.overc360.api.model.transaction.ConsignmentEntity;
import com.courier.overc360.api.model.transaction.FindCassandraConsignment;
import com.courier.overc360.api.model.transaction.FindIConsignment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

	private String getCommonServiceUrl() {
		return propertiesConfig.getCommonServiceUrl();
	}

	public UpdateCCR[] extractPdf(String fileName) {
		HttpHeaders headers = new HttpHeaders();
		AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
		String authToken = authTokenForCommonService.getAccess_token();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "RestTemplate");
		headers.add("Authorization", "Bearer " + authToken);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "pdf/extract/v2")
				.queryParam("fileName", fileName);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<UpdateCCR[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UpdateCCR[].class);
		return result.getBody();
	}

	public byte[] mergePdf(PDFMerger pdfMerger) {
		HttpHeaders headers = new HttpHeaders();
		AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
		String authToken = authTokenForCommonService.getAccess_token();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "RestTemplate");
		headers.add("Authorization", "Bearer " + authToken);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "pdf/merge/v2");
		HttpEntity<?> entity = new HttpEntity<>(pdfMerger, headers);
		ResponseEntity<byte[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, byte[].class);
		return result.getBody();
	}

	public String[] batchMergePdf(List<PDFMerger> pdfMergerList) {
		HttpHeaders headers = new HttpHeaders();
		AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
		String authToken = authTokenForCommonService.getAccess_token();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "RestTemplate");
		headers.add("Authorization", "Bearer " + authToken);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "pdf/merge/batch");
		HttpEntity<?> entity = new HttpEntity<>(pdfMergerList, headers);
		ResponseEntity<String[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String[].class);
		return result.getBody();
	}

	public String downloadPdf(String sourceUrl, String destinationDir, String documentName) {
		HttpHeaders headers = new HttpHeaders();
		AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
		String authToken = authTokenForCommonService.getAccess_token();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "RestTemplate");
		headers.add("Authorization", "Bearer " + authToken);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "pdf/v2/download")
				.queryParam("sourceUrl", sourceUrl)
				.queryParam("destinationDir", destinationDir)
				.queryParam("documentName", documentName);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
		return result.getBody();
	}

	public ConsignmentEntity[] findConsignment(FindCassandraConsignment findConsignment) {
		HttpHeaders headers = new HttpHeaders();
		AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
		String authToken = authTokenForCommonService.getAccess_token();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "RestTemplate");
		headers.add("Authorization", "Bearer " + authToken);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "consignment/find");
		HttpEntity<?> entity = new HttpEntity<>(findConsignment, headers);
		ResponseEntity<ConsignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentEntity[].class);
		return result.getBody();
	}

}
