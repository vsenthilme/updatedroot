package com.courier.overc360.api.service;

import com.courier.overc360.api.config.PropertiesConfig;
import com.courier.overc360.api.model.auth.AuthToken;
import com.courier.overc360.api.model.dto.InvoicePDFMerger;
import com.courier.overc360.api.model.dto.PDFMerger;
import com.courier.overc360.api.model.dto.UpdateCCR;
import com.courier.overc360.api.model.transaction.ConsignmentEntity;
import com.courier.overc360.api.model.transaction.ConsignmentOutput;
import com.courier.overc360.api.model.transaction.FindCassandraConsignment;
import com.courier.overc360.api.model.transaction.FindIConsignment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
		
	public UpdateCCR[] extractPdf(String fileName, String loginUserID, String consoleId) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
            String authToken = authTokenForCommonService.getAccess_token();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "pdf/extract/v3")
                    .queryParam("fileName", fileName)
                    .queryParam("consoleId", consoleId)
                    .queryParam("loginUserId", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<UpdateCCR[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UpdateCCR[].class);
            return result.getBody();
        } catch (Exception e) {
			log.error("Exception while pdf Extract : " + e.toString());
            throw e;
        }
    }

	public UpdateCCR[] extractPdf(String fileName, String loginUserID) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
            String authToken = authTokenForCommonService.getAccess_token();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "pdf/extract/v2")
                    .queryParam("fileName", fileName)
                    .queryParam("loginUserId", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<UpdateCCR[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UpdateCCR[].class);
            return result.getBody();
        } catch (Exception e) {
			log.error("Exception while pdf Extract : " + e.toString());
            throw e;
        }
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

	public ConsignmentOutput[] findConsignmentV4(FindCassandraConsignment findConsignment) {
		HttpHeaders headers = new HttpHeaders();
		AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
		String authToken = authTokenForCommonService.getAccess_token();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "RestTemplate");
		headers.add("Authorization", "Bearer " + authToken);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "consignment/find/v2");
		HttpEntity<?> entity = new HttpEntity<>(findConsignment, headers);
		ResponseEntity<ConsignmentOutput[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentOutput[].class);
		return result.getBody();
	}

	// Batch pdf merge
	public byte[] mergePdfs(List<PDFMerger> pdfMergerList) {
		HttpHeaders headers = new HttpHeaders();
		AuthToken authTokenForCommonService = authTokenService.getCommonServiceAuthToken();
		String authToken = authTokenForCommonService.getAccess_token();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "RestTemplate");
		headers.add("Authorization", "Bearer " + authToken);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ZipOutputStream zos = new ZipOutputStream(baos)) {
			int batchCounter = 1;
			for (PDFMerger pdfMerger : pdfMergerList) {
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "pdf/merge/v3");
				HttpEntity<?> entity = new HttpEntity<>(pdfMerger, headers);
				ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);

				String[] batchPaths = result.getBody().split(",");
//				log.info("Wrapper Service BatchPath <---------------------------------------> {}", batchPaths);
				for (String batchPath : batchPaths) {
					// Assuming you have a method to read file content from the path
					byte[] batchBytes = readBytesFromPath(batchPath);
					log.info("Wrapper Service BatchBytes <------------------------------------> {}", batchBytes);
					ZipEntry zipEntry = new ZipEntry("batch_" + batchCounter + ".pdf");
					zos.putNextEntry(zipEntry);
					zos.write(batchBytes);
					zos.closeEntry();
					batchCounter++;
				}
			}
			zos.finish();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	// Method to read bytes from a file path
	private byte[] readBytesFromPath(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}

	/**
	 *
	 * @param invoicePDFMerger
	 * @return
	 */
	public byte[] mergeInvoicePdf(InvoicePDFMerger invoicePDFMerger) {
		HttpHeaders headers = new HttpHeaders();
		String authToken = authTokenService.getCommonServiceAuthToken().getAccess_token();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + authToken);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommonServiceUrl() + "/api/invoice/pdf/merge");

		HttpEntity<InvoicePDFMerger> entity = new HttpEntity<>(invoicePDFMerger, headers);
		ResponseEntity<byte[]> response = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, byte[].class);

		return response.getBody();
	}

}