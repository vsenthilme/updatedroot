package com.mnrclara.wrapper.core.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mnrclara.wrapper.core.config.PropertiesConfig;
import com.mnrclara.wrapper.core.model.Folder;
import com.mnrclara.wrapper.core.model.FolderExistsResponse;
import com.mnrclara.wrapper.core.model.NewFolderBean;
import com.mnrclara.wrapper.core.model.Value;
import com.mnrclara.wrapper.core.model.auth.AuthToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SharePointService {

	@Autowired
	PropertiesConfig propertiesConfig;
	
	@Autowired
	AuthTokenService authTokenService;

	/**
	 * 
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
	 * @param fileLocation
	 * @throws Exception
	 */
	public void doUploadFile(Path fileLocation, String storeId, String requestId) throws Exception {
		InputStream inputStream = Files.newInputStream(fileLocation);
		byte[] data = inputStream.readAllBytes();
		
		AuthToken authToken = authTokenService.getSharePointOAuthToken();
		log.info("FileUpload authToken : " + authToken.getAccess_token());
		
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + authToken.getAccess_token());
		
		// Create two Folders
		String parentFolder = "CG";
		// https://graph.microsoft.com/v1.0/sites/207ace78-0651-4d8e-a273-5422ff52a99d/drive/items/root:/CG:/children
		String createParentFolder1 = propertiesConfig.getSpFileUploadParentUrl();
		createParentFolder1 = createParentFolder1.replaceFirst("@parent", parentFolder);
		log.info("----createParentFolder1----> : " + createParentFolder1);
		
		if (!checkFolderExists (authToken, storeId)) {
			// storeId
			NewFolderBean newFolderBean = new NewFolderBean();
			newFolderBean.setName(storeId);
			newFolderBean.setFolder(new Folder());
			log.info("----newFolderBean----> : " + newFolderBean);
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createParentFolder1);
			HttpEntity<NewFolderBean> requestEntity = new HttpEntity<>(newFolderBean, headers);
			ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, requestEntity, String.class);
			log.info("StoreID Folder Response : " + response.getBody());	
		}
		
		// requestId
		if (!checkSubFolderExists (authToken, parentFolder, storeId, requestId)) {
			// https://graph.microsoft.com/v1.0/sites/207ace78-0651-4d8e-a273-5422ff52a99d/drive/items/root:/CG/store1:/children
	//		String createParentFolder2 = "https://graph.microsoft.com/v1.0/sites/207ace78-0651-4d8e-a273-5422ff52a99d/drive/items/root:/CG/@storeId:/children";
			String createParentFolder2 = propertiesConfig.getSpFileUploadStoreIdUrl();
			createParentFolder2 = createParentFolder2.replaceFirst("@parent", parentFolder);
			createParentFolder2 = createParentFolder2.replaceFirst("@storeId", storeId);
			
			NewFolderBean newFolderBean = new NewFolderBean();
			newFolderBean.setFolder(new Folder());
			newFolderBean.setName(requestId);
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createParentFolder2);
			HttpEntity<NewFolderBean> requestEntity = new HttpEntity<>(newFolderBean, headers);
			ResponseEntity<String> response2 = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, requestEntity, String.class);
			log.info("RequestId Folder Response : " + response2.getBody());
		}
		
		String buildUrl = propertiesConfig.getSpFileUploadUrl();
		log.info("buildUrl : " + buildUrl);
		
		String fileName = fileLocation.getFileName().toString();
		buildUrl = buildUrl.replaceFirst("@folder1", parentFolder);
		buildUrl = buildUrl.replaceFirst("@folder2", storeId);
		buildUrl = buildUrl.replaceFirst("@folder3", requestId);
		buildUrl = buildUrl.replaceFirst("@file", fileName);
		log.info("--------buildUrl--------------->: " + buildUrl);
		
		HttpHeaders headers1 = new HttpHeaders();
		headers1.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers1.add("Authorization", "Bearer " + authToken.getAccess_token());
		
		UriComponentsBuilder builder1 = UriComponentsBuilder.fromHttpUrl(buildUrl);
		HttpEntity<byte[]> requestEntity1 = new HttpEntity<>(data, headers1);
		ResponseEntity<String> response3 = restTemplate.exchange(builder1.toUriString(), HttpMethod.PUT, requestEntity1, String.class);
		log.info("FileUpload Response : " + response3.getBody());
	}
	
	/**
	 * 
	 * @param authToken
	 * @param searchText
	 * @return
	 */
	private boolean checkFolderExists (AuthToken authToken, String searchText) {
//		https://graph.microsoft.com/v1.0/sites/207ace78-0651-4d8e-a273-5422ff52a99d/Drives/b!eM56IFEGjk2ic1Qi_1KpnfTRlMrY2K9JuOIWpyqvaQNAH-ACGzXyT7sM--rezEpv/root/search(q='3566')
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + authToken.getAccess_token());
	
		String url = 
				"https://graph.microsoft.com/v1.0/sites/207ace78-0651-4d8e-a273-5422ff52a99d/Drives/b!eM56IFEGjk2ic1Qi_1KpnfTRlMrY2K9JuOIWpyqvaQNAH-ACGzXyT7sM--rezEpv/root/search(q='@searchText')";
		url = url.replaceFirst("@searchText", searchText);
		log.info ("-----------url-----------> " + url);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<NewFolderBean> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<FolderExistsResponse> response = 
				restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, FolderExistsResponse.class);
		log.info("StoreID Folder Response : " + response.getBody());
		
		FolderExistsResponse folderExistsResponse = response.getBody();
		if (folderExistsResponse.getValue() != null && folderExistsResponse.getValue().size() > 0) {
			log.info("Search Value 1 : " + folderExistsResponse.getValue().get(0).getName());
			return true;
		} else {
			log.info("Search Value 2 : " + folderExistsResponse.getValue());
			return false;
		}
	}
	
	/**
	 * 
	 * @param authToken
	 * @param parent
	 * @param subFolder
	 * @return
	 */
	private boolean checkSubFolderExists (AuthToken authToken, String parent, String subFolder, String requestId) {
		// SubFolder Exists
		// https://graph.microsoft.com/v1.0/sites/207ace78-0651-4d8e-a273-5422ff52a99d/drive/root:/CG/23:/children
		
		try {
			RestTemplate restTemplate = getRestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "Bearer " + authToken.getAccess_token());

			String url = "https://graph.microsoft.com/v1.0/sites/207ace78-0651-4d8e-a273-5422ff52a99d/drive/root:/@parent/@subfolder:/children";
			url = url.replaceFirst("@parent", parent);
			url = url.replaceFirst("@subfolder", subFolder);
			
			log.info ("-----------url-----------> " + url);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			HttpEntity<NewFolderBean> requestEntity = new HttpEntity<>(headers);
			ResponseEntity<FolderExistsResponse> response = 
					restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, FolderExistsResponse.class);
			log.info("StoreID Folder Response : " + response.getBody());
			
			FolderExistsResponse folderExistsResponse = response.getBody();
			if (folderExistsResponse.getValue() != null && folderExistsResponse.getValue().size() > 0) {
				log.info("Search Value 1 : " + folderExistsResponse.getValue().get(0).getName());
				for (Value value : folderExistsResponse.getValue()) {
					log.info("Search Value #### : " + value.getName());
					if (value.getName().equalsIgnoreCase(requestId)) {
						return true;
					}
				}
			} else {
				log.info("Search Value 2 : " + folderExistsResponse.getValue());
				return false;
			}
		} catch (RestClientException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/*
	 * URL: http://site url/_api/web/GetFolderByServerRelativeUrl('/Folder Name')/Files('file name')/$value
	 * method: GET
	 * headers: Authorization: "Bearer " + accessToken
	 */
	public byte[] doDownloadFile(String fileName, String storeId, String requestId ) throws Exception {
		AuthToken authToken = authTokenService.getSharePointOAuthToken();
		log.info("FileDownload authToken : " + authToken.getAccess_token());
		
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add("Authorization", "Bearer " + authToken.getAccess_token());
		
		String buildUrl = propertiesConfig.getSpFileUploadUrl();
		log.info("buildUrl : " + buildUrl);
		
//		String folderName = "Test";
//		fileName = "a.txt";
		String parentFolder = "CG";
		buildUrl = buildUrl.replaceFirst("@folder1", parentFolder);
		buildUrl = buildUrl.replaceFirst("@folder2", storeId);
		buildUrl = buildUrl.replaceFirst("@folder3", requestId);
		buildUrl = buildUrl.replaceFirst("@file", fileName);
		log.info("--------buildUrl--------------->: " + buildUrl);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(buildUrl);
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, byte[].class);
		log.info("FileDownload Response : " + response.getBody());
		return response.getBody();
	}
	
	public static void main(String[] args) {
		//String buildUrl = "https://graph.microsoft.com/v1.0/sites/207ace78-0651-4d8e-a273-5422ff52a99d/Drives/b!eM56IFEGjk2ic1Qi_1KpnfTRlMrY2K9JuOIWpyqvaQNAH-ACGzXyT7sM--rezEpv/root:/Test/Request1.txt:/content";
		String buildUrl = "https://graph.microsoft.com/v1.0/sites/207ace78-0651-4d8e-a273-5422ff52a99d/Drives/b!eM56IFEGjk2ic1Qi_1KpnfTRlMrY2K9JuOIWpyqvaQNAH-ACGzXyT7sM--rezEpv/root:/@folder/@file:/content";
		String fileName = "xxww";
		String folderName = "Test";
		
		buildUrl = buildUrl.replaceFirst("@folder", folderName);
		buildUrl = buildUrl.replaceFirst("@file", fileName);
		log.info(buildUrl);
	}
}
