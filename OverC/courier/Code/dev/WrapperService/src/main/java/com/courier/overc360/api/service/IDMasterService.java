package com.courier.overc360.api.service;

import com.courier.overc360.api.config.PropertiesConfig;
import com.courier.overc360.api.exception.BadRequestException;
import com.courier.overc360.api.model.idmaster.Module;
import com.courier.overc360.api.model.idmaster.*;
import com.courier.overc360.api.model.user.AddUserManagement;
import com.courier.overc360.api.model.user.FindUserManagement;
import com.courier.overc360.api.model.user.UpdateUserManagement;
import com.courier.overc360.api.model.user.UserManagement;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class IDMasterService {

    @Autowired
    PropertiesConfig propertiesConfig;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    private String getIDMasterServiceApiUrl() {
        return propertiesConfig.getIdmasterServiceUrl();
    }

    //===============================================NumberRange=======================================================
    // Get All NumberRangeCode Details
    public NumberRange[] getAllNumberRanges(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "numberRange");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<NumberRange[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, NumberRange[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get a NumberRangeCode
    public NumberRange getNumberRange(String languageId, Long numberRangeCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "numberRange/" + numberRangeCode)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<NumberRange> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, NumberRange.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create new NumberRangeCode
    public NumberRange createNumberRange(AddNumberRange addNumberRange, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "numberRange")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addNumberRange, headers);
        ResponseEntity<NumberRange> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, NumberRange.class);
//        log.info("result : " + result.getStatusCode());
        return result.getBody();
    }

    // Update NumberRange
    public NumberRange updateNumberRange(String languageId, Long numberRangeCode, String loginUserID, String numberRangeObject,
                                         UpdateNumberRange updateNumberRange, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateNumberRange, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "numberRange/" + numberRangeCode)
                    .queryParam("languageId", languageId)
                    .queryParam("numberRangeObject", numberRangeObject)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<NumberRange> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, NumberRange.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete NumberRange
    public boolean deleteNumberRange(String languageId, Long numberRangeCode, String numberRangeObject, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "numberRange/" + numberRangeCode)
                    .queryParam("languageId", languageId)
                    .queryParam("numberRangeObject", numberRangeObject)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find NumberRange
    public NumberRange[] findNumberRange(FindNumberRange findNumberRange, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "numberRange/find");
            HttpEntity<?> entity = new HttpEntity<>(findNumberRange, headers);
            ResponseEntity<NumberRange[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, NumberRange[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //=================================================ErrorLog========================================================
    // Get All ErrorLog Details
    public ErrorLog[] getAllErrorLogs(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "errorLog/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ErrorLog[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ErrorLog[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ErrorLog
    public ErrorLog[] findErrorLogs(FindErrorLog findErrorLog, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "errorLog/find");
            HttpEntity<?> entity = new HttpEntity<>(findErrorLog, headers);
            ResponseEntity<ErrorLog[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ErrorLog[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //=================================================Language========================================================
    // Get All Language Details
    public Language[] getAllLanguages(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "language");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Language[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Language[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Language
    public Language getLanguage(String languageId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "language/" + languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Language> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Language.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Language
    public Language createLanguage(AddLanguage newLanguage, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "language")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newLanguage, headers);
            ResponseEntity<Language> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Language.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Language
    public Language updateLanguage(String languageId, String loginUserID, UpdateLanguage updateLanguage, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateLanguage, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "language/" + languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Language> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Language.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Language
    public boolean deleteLanguage(String languageId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "language/" + languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Language
    public Language[] findLanguages(FindLanguage findLanguage, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "language/find");
            HttpEntity<?> entity = new HttpEntity<>(findLanguage, headers);
            ResponseEntity<Language[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Language[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================Company========================================================
    // Get All Company Details
    public Company[] getAllCompanies(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "company");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Company[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Company[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Company
    public Company getCompany(String languageId, String companyId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "company/" + companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Company> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Company.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Company
    public Company createCompany(AddCompany addCompany, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "company")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCompany, headers);
            ResponseEntity<Company> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Company.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Company
    public Company updateCompany(String languageId, String companyId, String loginUserID, UpdateCompany updateCompany, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCompany, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "company/" + companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Company> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Company.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Company
    public boolean deleteCompany(String languageId, String companyId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "company/" + companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Company
    public Company[] findCompany(FindCompany findCompany, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "company/find");
            HttpEntity<?> entity = new HttpEntity<>(findCompany, headers);
            ResponseEntity<Company[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Company[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===================================================SubProduct====================================================
    // Get All SubProduct Details
    public SubProduct[] getAllSubProducts(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "subProduct");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<SubProduct[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, SubProduct[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get SubProduct
    public SubProduct getSubProduct(String languageId, String companyId, String subProductId, String subProductValue, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "subProduct/" + subProductId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductValue", subProductValue);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<SubProduct> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, SubProduct.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create SubProduct
    public SubProduct createSubProduct(AddSubProduct addSubProduct, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "subProduct")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addSubProduct, headers);
            ResponseEntity<SubProduct> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, SubProduct.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update SubProduct
    public SubProduct updateSubProduct(String languageId, String companyId, String subProductId, String subProductValue,
                                       UpdateSubProduct updateSubProduct, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateSubProduct, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "subProduct/" + subProductId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductValue", subProductValue)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<SubProduct> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, SubProduct.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete SubProduct
    public boolean deleteSubProduct(String languageId, String companyId, String subProductId,
                                    String subProductValue, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "subProduct/" + subProductId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductValue", subProductValue)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find SubProduct
    public SubProduct[] findSubProducts(FindSubProduct findSubProduct, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "subProduct/find");
            HttpEntity<?> entity = new HttpEntity<>(findSubProduct, headers);
            ResponseEntity<SubProduct[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, SubProduct[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create SubProducts - bulk
    public SubProduct[] createSubProductBulk(List<AddSubProduct> addSubProductList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "subProduct/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addSubProductList, headers);
            ResponseEntity<SubProduct[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, SubProduct[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update SubProducts - bulk
    public SubProduct[] updateSubProductBulk(List<UpdateSubProduct> updateSubProductList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateSubProductList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "subProduct/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<SubProduct[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, SubProduct[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete SubProducts - bulk
    public boolean deleteSubProductBulk(List<SubProductDeleteInput> subProductDeleteInputs, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(subProductDeleteInputs, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "subProduct/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===================================================Product=======================================================
    // Get All Product Details
    public Product[] getAllProducts(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "product");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Product[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Product[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Product
    public Product getProduct(String languageId, String companyId, String subProductId, String productId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "product/" + productId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductId", subProductId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Product> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Product.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Product
    public Product createProduct(AddProduct addProduct, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "product")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addProduct, headers);
            ResponseEntity<Product> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Product.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Product
    public Product updateProduct(String languageId, String companyId, String subProductId, String productId,
                                 UpdateProduct updateProduct, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateProduct, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "product/" + productId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductId", subProductId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Product> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Product.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Product
    public boolean deleteProduct(String languageId, String companyId, String subProductId, String productId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "product/" + productId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductId", subProductId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Product
    public Product[] findProducts(FindProduct findProduct, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "product/find");
            HttpEntity<?> entity = new HttpEntity<>(findProduct, headers);
            ResponseEntity<Product[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Product[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create Products - bulk
    public Product[] createProductBulk(List<AddProduct> addProductList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "product/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addProductList, headers);
            ResponseEntity<Product[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Product[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Products - bulk
    public Product[] updateProductBulk(List<UpdateProduct> updateProductList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateProductList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "product/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Product[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Product[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Products - bulk
    public boolean deleteProductBulk(List<ProductDeleteInput> productDeleteInputs, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(productDeleteInputs, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "product/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // ==================================================Rate======================================================
    // Get All Rate Details
    public Rate[] getAllRateDetails(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rate");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Rate[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Rate[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Rate
    public Rate getRate(String languageId, String companyId, String partnerId, String rateParameterId, Long lineNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rate/" + partnerId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("rateParameterId", rateParameterId)
                    .queryParam("lineNo", lineNo);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Rate> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Rate.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Rate
    public Rate[] createRate(AddRate[] addRate, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rate")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addRate, headers);
            ResponseEntity<Rate[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Rate[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Rate
    public Rate[] updateRate(Rate[] updateRate, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateRate, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rate/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Rate[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Rate[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Rate
    public boolean deleteRate(DeleteRate[] deleteRate, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteRate, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rate/delete")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Rate
    public Rate[] findRate(FindRate findRate, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rate/find");
            HttpEntity<?> entity = new HttpEntity<>(findRate, headers);
            ResponseEntity<Rate[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Rate[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================ServiceType====================================================
    // Get All ServiceType Details
    public ServiceType[] getAllServiceTypes(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "serviceType");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ServiceType[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ServiceType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ServiceType
    public ServiceType getServiceType(String companyId, String languageId, String serviceTypeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "serviceType/" + serviceTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ServiceType> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ServiceType.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ServiceType
    public ServiceType createServiceType(AddServiceType newServiceType, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "serviceType")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newServiceType, headers);
            ResponseEntity<ServiceType> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ServiceType.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update ServiceType
    public ServiceType updateServiceType(String companyId, String languageId, String serviceTypeId, String loginUserID,
                                         UpdateServiceType updateServiceType, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateServiceType, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "serviceType/" + serviceTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ServiceType> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ServiceType.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ServiceType
    public boolean deleteServiceType(String companyId, String languageId, String serviceTypeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "serviceType/" + serviceTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ServiceType
    public ServiceType[] findServiceType(FindServicetype findServiceType, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "serviceType/find");
            HttpEntity<?> entity = new HttpEntity<>(findServiceType, headers);
            ResponseEntity<ServiceType[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ServiceType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //=================================================ConsignmentType=================================================
    // Get All ConsignmentType Details
    public ConsignmentType[] getConsignmentTypes(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignmentType");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ConsignmentType[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ConsignmentType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ConsignmentType
    public ConsignmentType getConsignmentType(String companyId, String languageId, String consignmentTypeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignmentType/" + consignmentTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ConsignmentType> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ConsignmentType.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ConsignmentType
    public ConsignmentType createConsignmentType(AddConsignmentType newConsignmentType, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignmentType")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newConsignmentType, headers);
            ResponseEntity<ConsignmentType> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentType.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update ConsignmentType
    public ConsignmentType updateConsignmentType(String companyId, String languageId, String consignmentTypeId, String loginUserID,
                                                 UpdateConsignmentType updateConsignmentTypeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateConsignmentTypeId, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignmentType/" + consignmentTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ConsignmentType> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ConsignmentType.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ConsignmentType
    public boolean deleteConsignmentType(String companyId, String languageId, String consignmentTypeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignmentType/" + consignmentTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ConsignmentType
    public ConsignmentType[] findConsignmentType(FindConsignmentType findConsignmentType, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignmentType/find");
            HttpEntity<?> entity = new HttpEntity<>(findConsignmentType, headers);
            ResponseEntity<ConsignmentType[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    //===================================================LoadType======================================================
    // Get All LoadType Details
    public LoadType[] getAllLoadTypes(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "loadType");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<LoadType[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, LoadType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get LoadType
    public LoadType getLoadType(String languageId, String companyId, String loadTypeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "loadType/" + loadTypeId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<LoadType> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, LoadType.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create LoadType
    public LoadType createLoadType(AddLoadType addLoadType, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "loadType")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addLoadType, headers);
            ResponseEntity<LoadType> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, LoadType.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update LoadType
    public LoadType updateLoadType(String languageId, String companyId, String loadTypeId,
                                   UpdateLoadType updateLoadType, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateLoadType, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "loadType/" + loadTypeId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<LoadType> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, LoadType.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete LoadType
    public boolean deleteLoadType(String languageId, String companyId, String loadTypeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "loadType/" + loadTypeId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find LoadType
    public LoadType[] findLoadTypes(FindLoadType findLoadType, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "loadType/find");
            HttpEntity<?> entity = new HttpEntity<>(findLoadType, headers);
            ResponseEntity<LoadType[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, LoadType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===================================================Country=======================================================
    // Get All Country Details
    public Country[] getAllCountries(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "country");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Country[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Country[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Country
    public Country getCountry(String languageId, String companyId, String countryId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "country/" + countryId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Country> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Country.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Country
    public Country createCountry(AddCountry addCountry, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "country")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCountry, headers);
            ResponseEntity<Country> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Country.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Country
    public Country updateCountry(String languageId, String companyId, String countryId, UpdateCountry updateCountry, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCountry, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "country/" + countryId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Country> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Country.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Country
    public boolean deleteCountry(String languageId, String companyId, String countryId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "country/" + countryId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Country
    public Country[] findCountries(FindCountry findCountry, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "country/find");
            HttpEntity<?> entity = new HttpEntity<>(findCountry, headers);
            ResponseEntity<Country[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Country[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================Province=======================================================
    // Get All Province Details
    public Province[] getAllProvinces(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "province");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Province[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Province[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Province
    public Province getProvince(String languageId, String companyId, String countryId, String provinceId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "province/" + provinceId)
                    .queryParam("countryId", countryId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Province> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Province.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Province
    public Province createProvince(AddProvince addProvince, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "province")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addProvince, headers);
            ResponseEntity<Province> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Province.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Province
    public Province updateProvince(String languageId, String companyId, String countryId, String provinceId,
                                   UpdateProvince updateProvince, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateProvince, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "province/" + provinceId)
                    .queryParam("countryId", countryId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Province> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Province.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Province
    public boolean deleteProvince(String languageId, String companyId, String countryId, String provinceId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "province/" + provinceId)
                    .queryParam("countryId", countryId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Province
    public Province[] findProvinces(FindProvince findProvince, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "province/find");
            HttpEntity<?> entity = new HttpEntity<>(findProvince, headers);
            ResponseEntity<Province[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Province[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //=====================================================City========================================================
    // Get All City Details
    public City[] getAllCities(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "city");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<City[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, City[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get City
    public City getCity(String languageId, String companyId, String countryId, String provinceId, String districtId, String cityId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "city/" + cityId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("countryId", countryId)
                    .queryParam("provinceId", provinceId)
                    .queryParam("districtId", districtId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<City> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, City.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create City
    public City createCity(AddCity addCity, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "city")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCity, headers);
            ResponseEntity<City> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, City.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update City
    public City updateCity(String languageId, String companyId, String countryId, String provinceId, String districtId, String cityId,
                           UpdateCity updateCity, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCity, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "city/" + cityId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("countryId", countryId)
                    .queryParam("provinceId", provinceId)
                    .queryParam("districtId", districtId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<City> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, City.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete City
    public boolean deleteCity(String languageId, String companyId, String countryId, String provinceId, String districtId, String cityId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "city/" + cityId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("countryId", countryId)
                    .queryParam("provinceId", provinceId)
                    .queryParam("districtId", districtId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find City
    public City[] findCity(FindCity findCity, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "city/find");
            HttpEntity<?> entity = new HttpEntity<>(findCity, headers);
            ResponseEntity<City[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, City[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // =============================================RateParameter======================================================
    // Get All RateParameter Details
    public RateParameter[] getAllRateParameters(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rateParameter");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RateParameter[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RateParameter[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get RateParameter
    public RateParameter getRateParameter(String rateParameterId, String companyId, String languageId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rateParameter/" + rateParameterId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RateParameter> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RateParameter.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create RateParameter
    public RateParameter createRateParameter(AddRateParameter addRateParameter, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rateParameter")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addRateParameter, headers);
            ResponseEntity<RateParameter> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RateParameter.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update RateParameter
    public RateParameter updateRateParameter(String companyId, String rateParameterId, String languageId,
                                             UpdateRateParameter updateRateParameter, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateRateParameter, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rateParameter/" + rateParameterId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<RateParameter> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, RateParameter.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete RateParameter
    public boolean deleteRateParameter(String companyId, String rateParameterId, String languageId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rateParameter/" + rateParameterId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find RateParameter
    public RateParameter[] findRateParameter(FindRateParameter findRateParameter, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rateParameter/find");
            HttpEntity<?> entity = new HttpEntity<>(findRateParameter, headers);
            ResponseEntity<RateParameter[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RateParameter[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // =================================================Status=========================================================
    // Get All Status Details
    public Status[] getAllStatus(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "status");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Status[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Status[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Status
    public Status getStatus(String languageId, String statusId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "status/" + statusId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Status> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Status.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Status
    public Status createStatus(AddStatus addStatus, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "status")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addStatus, headers);
            ResponseEntity<Status> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Status.class);
            log.info("Status Create Response Time " + new Date());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Status
    public Status updateStatus(String languageId, String statusId, UpdateStatus updateStatus, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateStatus, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "status/" + statusId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Status> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Status.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Status
    public boolean deleteStatus(String languageId, String statusId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "status/" + statusId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Status
    public Status[] findStatus(FindStatus findStatus, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "status/find");
            HttpEntity<?> entity = new HttpEntity<>(findStatus, headers);
            ResponseEntity<Status[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Status[].class);
            log.info("result : " + result.getStatusCode());
            log.info("Find Response Status Time " + new Date());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // ==================================================Currency======================================================
    // Get All Currency Details
    public Currency[] getAllCurrency(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currency");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Currency[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Currency[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // Get Currency
    public Currency getCurrency(String currencyId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currency/" + currencyId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Currency> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Currency.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Currency
    public Currency createCurrency(AddCurrency addCurrency, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currency")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCurrency, headers);
            ResponseEntity<Currency> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Currency.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Currency
    public Currency updateCurrency(String currencyId, UpdateCurrency updateCurrency, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCurrency, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currency/" + currencyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Currency> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Currency.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Currency
    public boolean deleteCurrency(String currencyId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currency/" + currencyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Currency
    public Currency[] findCurrency(FindCurrency findCurrency, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currency/find");
            HttpEntity<?> entity = new HttpEntity<>(findCurrency, headers);
            ResponseEntity<Currency[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Currency[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //====================================================Hub==========================================================
    // Get All Hub Details
    public Hub[] getAllHubs(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hub");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Hub[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Hub[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Hub
    public Hub getHub(String languageId, String companyId, String hubCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hub/" + hubCode)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Hub> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Hub.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Hub
    public Hub createHub(AddHub addHub, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hub")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addHub, headers);
            ResponseEntity<Hub> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Hub.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Hub
    public Hub updateHub(String languageId, String companyId, String hubCode,
                         UpdateHub updateHub, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateHub, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hub/" + hubCode)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Hub> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Hub.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Hub
    public boolean deleteHub(String languageId, String companyId, String hubCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hub/" + hubCode)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Hub
    public Hub[] findHubs(FindHub findHub, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hub/find");
            HttpEntity<?> entity = new HttpEntity<>(findHub, headers);
            ResponseEntity<Hub[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Hub[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===================================================Customer======================================================
    // Get All Customer Details
    public Customer[] getAllCustomers(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customer");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Customer[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Customer[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Customer
    public Customer getCustomer(String languageId, String companyId, String subProductId, String subProductValue,
                                String productId, String customerId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customer/" + customerId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductId", subProductId)
                    .queryParam("subProductValue", subProductValue)
                    .queryParam("productId", productId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Customer> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Customer.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Customer
    public Customer createCustomer(AddCustomer addCustomer, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customer")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCustomer, headers);
            ResponseEntity<Customer> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Customer.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Customer
    public Customer updateCustomer(String languageId, String companyId, String subProductId, String subProductValue, String productId,
                                   String customerId, UpdateCustomer updateCustomer, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCustomer, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customer/" + customerId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductId", subProductId)
                    .queryParam("subProductValue", subProductValue)
                    .queryParam("productId", productId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Customer> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Customer.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Customer
    public boolean deleteCustomer(String languageId, String companyId, String subProductId, String subProductValue, String productId,
                                  String customerId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customer/" + customerId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductId", subProductId)
                    .queryParam("subProductValue", subProductValue)
                    .queryParam("productId", productId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Customer
    public Customer[] findCustomers(FindCustomer findCustomer, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customer/find");
            HttpEntity<?> entity = new HttpEntity<>(findCustomer, headers);
            ResponseEntity<Customer[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Customer[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create Customers - bulk
    public Customer[] createCustomerBulk(List<AddCustomer> addCustomerList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customer/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCustomerList, headers);
            ResponseEntity<Customer[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Customer[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Customers - bulk
    public Customer[] updateCustomerBulk(List<UpdateCustomer> updateCustomerList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCustomerList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customer/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Customer[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Customer[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Customers - bulk
    public boolean deleteCustomerBulk(List<CustomerDeleteInput> customerDeleteInputs, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(customerDeleteInputs, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customer/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===================================================Consignor=======================================================
    // Get All Consignor Details
    public Consignor[] getAllConsignors(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignor");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Consignor[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Consignor[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Consignor
    public Consignor getConsignor(String languageId, String companyId, String subProductId, String subProductValue,
                                  String productId, String customerId, String consignorId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignor/" + consignorId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductId", subProductId)
                    .queryParam("subProductValue", subProductValue)
                    .queryParam("productId", productId)
                    .queryParam("customerId", customerId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Consignor> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Consignor.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Consignor
    public Consignor createConsignor(AddConsignor addConsignor, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignor")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addConsignor, headers);
            ResponseEntity<Consignor> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Consignor.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Consignor
    public Consignor updateConsignor(String languageId, String companyId, String subProductId, String subProductValue, String productId, String customerId,
                                     String consignorId, UpdateConsignor updateConsignor, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateConsignor, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignor/" + consignorId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductId", subProductId)
                    .queryParam("subProductValue", subProductValue)
                    .queryParam("productId", productId)
                    .queryParam("customerId", customerId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Consignor> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Consignor.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Consignor
    public boolean deleteConsignor(String languageId, String companyId, String subProductId, String subProductValue, String productId,
                                   String customerId, String consignorId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignor/" + consignorId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("subProductId", subProductId)
                    .queryParam("subProductValue", subProductValue)
                    .queryParam("productId", productId)
                    .queryParam("customerId", customerId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Consignor
    public Consignor[] findConsignors(FindConsignor findConsignor, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignor/find");
            HttpEntity<?> entity = new HttpEntity<>(findConsignor, headers);
            ResponseEntity<Consignor[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Consignor[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create Consignors - bulk
    public Consignor[] createConsignorBulk(List<AddConsignor> addConsignorList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignor/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addConsignorList, headers);
            ResponseEntity<Consignor[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Consignor[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Consignors - bulk
    public Consignor[] updateConsignorBulk(List<UpdateConsignor> updateConsignorList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateConsignorList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignor/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Consignor[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Consignor[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Consignors - bulk
    public boolean deleteConsignorBulk(List<ConsignorDeleteInput> consignorDeleteInputs, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(consignorDeleteInputs, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "consignor/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===============================================PartnerHubMapping=================================================
    // Get All PartnerHubMapping Details
    public PartnerHubMapping[] getAllPartnerHubMappings(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "partnerHubMapping");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PartnerHubMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PartnerHubMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get PartnerHubMapping
    public PartnerHubMapping getPartnerHubMapping(String languageId, String companyId, String hubCode, String partnerType, String partnerId, String authToken, String productCode) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "partnerHubMapping/" + partnerId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("hubCode", hubCode)
                    .queryParam("partnerType", partnerType)
                    .queryParam("productCode", productCode);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PartnerHubMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PartnerHubMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create PartnerHubMapping
    public PartnerHubMapping createPartnerHubMapping(AddPartnerHubMapping addPartnerHubMapping, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "partnerHubMapping")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addPartnerHubMapping, headers);
            ResponseEntity<PartnerHubMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PartnerHubMapping.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update PartnerHubMapping
    public PartnerHubMapping updatePartnerHubMapping(String languageId, String companyId, String hubCode, String partnerType, String partnerId,
                                                     UpdatePartnerHubMapping updatePartnerHubMapping, String loginUserID, String authToken, String productCode) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updatePartnerHubMapping, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "partnerHubMapping/" + partnerId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("hubCode", hubCode)
                    .queryParam("partnerType", partnerType)
                    .queryParam("loginUserID", loginUserID)
                    .queryParam("productCode", productCode);
            ResponseEntity<PartnerHubMapping> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, PartnerHubMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete PartnerHubMapping
    public boolean deletePartnerHubMapping(String languageId, String companyId, String hubCode, String partnerType, String partnerId, String loginUserID, String authToken, String productCode) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "partnerHubMapping/" + partnerId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("hubCode", hubCode)
                    .queryParam("partnerType", partnerType)
                    .queryParam("loginUserID", loginUserID)
                    .queryParam("productCode", productCode);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find PartnerHubMapping
    public PartnerHubMapping[] findPartnerHubMappings(FindPartnerHubMapping findPartnerHubMapping, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "partnerHubMapping/find");
            HttpEntity<?> entity = new HttpEntity<>(findPartnerHubMapping, headers);
            ResponseEntity<PartnerHubMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PartnerHubMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //====================================================HSCode=======================================================
    // Get All HSCode Details
    public HSCode[] getAllHSCodes(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hsCode");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<HSCode[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, HSCode[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get HSCode
    public HSCode getHSCode(String languageId, String companyId, String hsCode, String specialApprovalId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hsCode/" + hsCode)
                    .queryParam("languageId", languageId)
                    .queryParam("specialApprovalId", specialApprovalId)
                    .queryParam("companyId", companyId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<HSCode> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, HSCode.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create HSCode
    public HSCode createHSCode(AddHSCode addHSCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hsCode")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addHSCode, headers);
            ResponseEntity<HSCode> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, HSCode.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update HSCode
    public HSCode updateHSCode(String languageId, String companyId, String hsCode, String specialApprovalId,
                               UpdateHSCode updateHSCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateHSCode, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hsCode/" + hsCode)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("specialApprovalId", specialApprovalId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<HSCode> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, HSCode.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete HSCode
    public boolean deleteHSCode(String languageId, String companyId, String hsCode, String specialApprovalId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hsCode/" + hsCode)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("specialApprovalId", specialApprovalId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find HSCode
    public HSCode[] findHSCodes(FindHSCode findHSCode, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hsCode/find");
            HttpEntity<?> entity = new HttpEntity<>(findHSCode, headers);
            ResponseEntity<HSCode[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, HSCode[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===============================================ProvinceMapping===================================================
    // Get All ProvinceMapping Details
    public ProvinceMapping[] getAllProvinceMappings(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "provinceMapping");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ProvinceMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ProvinceMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ProvinceMapping
    public ProvinceMapping getProvinceMapping(String languageId, String companyId, String provinceId, String partnerId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "provinceMapping/" + partnerId)
                    .queryParam("provinceId", provinceId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ProvinceMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ProvinceMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ProvinceMapping
    public ProvinceMapping createProvinceMapping(AddProvinceMapping addProvinceMapping, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "provinceMapping")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addProvinceMapping, headers);
            ResponseEntity<ProvinceMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ProvinceMapping.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update ProvinceMapping
    public ProvinceMapping updateProvinceMapping(String languageId, String companyId, String provinceId, String partnerId,
                                                 UpdateProvinceMapping updateProvinceMapping, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateProvinceMapping, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "provinceMapping/" + partnerId)
                    .queryParam("provinceId", provinceId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ProvinceMapping> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ProvinceMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ProvinceMapping
    public boolean deleteProvinceMapping(String languageId, String companyId, String provinceId, String partnerId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "provinceMapping/" + partnerId)
                    .queryParam("provinceId", provinceId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ProvinceMapping
    public ProvinceMapping[] findProvinceMappings(FindProvinceMapping findProvinceMapping, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "provinceMapping/find");
            HttpEntity<?> entity = new HttpEntity<>(findProvinceMapping, headers);
            ResponseEntity<ProvinceMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ProvinceMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //===============================================CountryMapping===================================================
    // Get All CountryMapping Details
    public CountryMapping[] getAllCountryMappings(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "countryMapping");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CountryMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CountryMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get CountryMapping
    public CountryMapping getCountryMapping(String languageId, String companyId, String countryId, String partnerId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "countryMapping/" + partnerId)
                    .queryParam("countryId", countryId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CountryMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CountryMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create CountryMapping
    public CountryMapping createCountryMapping(AddCountryMapping addCountryMapping, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "countryMapping")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCountryMapping, headers);
            ResponseEntity<CountryMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CountryMapping.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update CountryMapping
    public CountryMapping updateCountryMapping(String languageId, String companyId, String countryId, String partnerId,
                                               UpdateCountryMapping updateCountryMapping, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCountryMapping, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "countryMapping/" + partnerId)
                    .queryParam("countryId", countryId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CountryMapping> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CountryMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete CountryMapping
    public boolean deleteCountryMapping(String languageId, String companyId, String countryId, String partnerId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "countryMapping/" + partnerId)
                    .queryParam("countryId", countryId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CountryMapping
    public CountryMapping[] findCountryMappings(FindCountryMapping findCountryMapping, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "countryMapping/find");
            HttpEntity<?> entity = new HttpEntity<>(findCountryMapping, headers);
            ResponseEntity<CountryMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CountryMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //===============================================CityMapping===================================================
    // Get All CityMapping Details
    public CityMapping[] getAllCityMappings(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "cityMapping");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CityMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CityMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get CityMapping
    public CityMapping getCityMapping(String languageId, String companyId, String cityId, String partnerId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "cityMapping/" + partnerId)
                    .queryParam("cityId", cityId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CityMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CityMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create CityMapping
    public CityMapping createCityMapping(AddCityMapping addCityMapping, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "cityMapping")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCityMapping, headers);
            ResponseEntity<CityMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CityMapping.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update CityMapping
    public CityMapping updateCityMapping(String languageId, String companyId, String cityId, String partnerId,
                                         UpdateCityMapping updateCityMapping, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCityMapping, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "cityMapping/" + partnerId)
                    .queryParam("cityId", cityId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CityMapping> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CityMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete CityMapping
    public boolean deleteCityMapping(String languageId, String companyId, String cityId, String partnerId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "cityMapping/" + partnerId)
                    .queryParam("cityId", cityId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CityMapping
    public CityMapping[] findCityMappings(FindCityMapping findCityMapping, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "cityMapping/find");
            HttpEntity<?> entity = new HttpEntity<>(findCityMapping, headers);
            ResponseEntity<CityMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CityMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //----------------------------------------------------Menu---------------------------------------------------------
    // Get All Menu Details
    public Menu[] getAllMenus(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "menu");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Menu[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Menu[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Menu
    public Menu getMenu(Long menuId, Long subMenuId, Long authorizationObjectId, String companyId, String languageId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "menu/" + menuId)
                    .queryParam("subMenuId", subMenuId)
                    .queryParam("authorizationObjectId", authorizationObjectId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Menu> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Menu.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Menu
    public Menu createMenu(AddMenu addMenu, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "menu")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addMenu, headers);
            ResponseEntity<Menu> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Menu.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Menu - Bulk create
    public Menu[] createMenuBulk(List<AddMenu> newMenuId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "menu/bulk")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newMenuId, headers);
            ResponseEntity<Menu[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Menu[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Patch Menu
    public Menu updateMenu(Long menuId, Long subMenuId, Long authorizationObjectId, String companyId, String languageId,
                           String loginUserID, UpdateMenu updateMenu, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateMenu, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "menu/" + menuId)
                    .queryParam("subMenuId", subMenuId)
                    .queryParam("authorizationObjectId", authorizationObjectId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Menu> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Menu.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Menu
    public boolean deleteMenu(Long menuId, Long subMenuId, Long authorizationObjectId, String companyId,
                              String languageId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "menu/" + menuId)
                    .queryParam("subMenuId", subMenuId)
                    .queryParam("authorizationObjectId", authorizationObjectId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Menus
    public Menu[] findMenus(FindMenu findMenu, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "menu/find");
            HttpEntity<?> entity = new HttpEntity<>(findMenu, headers);
            ResponseEntity<Menu[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Menu[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================District====================================================
    // Get All District Details
    public District[] getAllDistrict(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "district");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<District[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, District[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get District
    public District getDistrict(String languageId, String companyId, String countryId, String provinceId, String districtId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "district/" + districtId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId).queryParam("countryId", countryId).queryParam("provinceId", provinceId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<District> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, District.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create District
    public District createDistrict(AddDistrict newDistrict, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "district")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newDistrict, headers);
            ResponseEntity<District> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, District.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update District
    public District updateDistrict(String languageId, String companyId, String countryId, String provinceId, String districtId,
                                   UpdateDistrict updateDistrict, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateDistrict, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "district/" + districtId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("countryId", countryId)
                    .queryParam("provinceId", provinceId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<District> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, District.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete District
    public boolean deleteDistrict(String languageId, String companyId, String countryId, String provinceId, String districtId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "district/" + districtId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId).queryParam("provinceId", provinceId).queryParam("countryId", countryId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find District
    public District[] findDistrict(FindDistrict findDistrict, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "district/find");
            HttpEntity<?> entity = new HttpEntity<>(findDistrict, headers);
            ResponseEntity<District[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, District[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // ========================================District Mapping==============================================//


    // Get All DistrictMapping Details
    public DistrictMapping[] getAllDistrictMappings(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "districtMapping");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<DistrictMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, DistrictMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get DistrictMapping
    public DistrictMapping getDistrictMapping(String languageId, String companyId, String partnerId, String districtId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "districtMapping/" + partnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("districtId", districtId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<DistrictMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, DistrictMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create DistrictMapping
    public DistrictMapping createDistrictMapping(AddDistrictMapping addDistrictMapping, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "districtMapping")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addDistrictMapping, headers);
            ResponseEntity<DistrictMapping> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, DistrictMapping.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update DistrictMapping
    public DistrictMapping updateDistrictMapping(String languageId, String companyId, String partnerId,
                                                 String districtId, UpdateDistrictMapping updateDistrictMapping, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateDistrictMapping, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "districtMapping/" + partnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("districtId", districtId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<DistrictMapping> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, DistrictMapping.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete DistrictMapping
    public boolean deleteDistrictMapping(String languageId, String companyId, String partnerId, String districtId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "districtMapping/" + partnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("districtId", districtId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find DistrictMapping
    public DistrictMapping[] findDistrictMappings(FindDistrictMapping findDistrictMapping, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "districtMapping/find");
            HttpEntity<?> entity = new HttpEntity<>(findDistrictMapping, headers);
            ResponseEntity<DistrictMapping[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, DistrictMapping[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //===============================================CurrencyExchangeRate===================================================
    // Get All CurrencyExchangeRate Details
    public CurrencyExchangeRate[] getAllCurrencyExchangeRate(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currencyExchangeRate");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CurrencyExchangeRate[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CurrencyExchangeRate[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get CurrencyExchangeRate
    public CurrencyExchangeRate getCurrencyExchangeRate(String languageId, String companyId, String fromCurrencyId, String toCurrencyId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currencyExchangeRate/" + fromCurrencyId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("toCurrencyId", toCurrencyId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CurrencyExchangeRate> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CurrencyExchangeRate.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create CurrencyExchangeRate
    public CurrencyExchangeRate createCurrencyExchangeRate(AddCurrencyExchangeRate addCurrencyExchangeRate, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currencyExchangeRate")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCurrencyExchangeRate, headers);
            ResponseEntity<CurrencyExchangeRate> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CurrencyExchangeRate.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update CurrencyExchangeRate
    public CurrencyExchangeRate updateCurrencyExchangeRate(String languageId, String companyId, String fromCurrencyId, String toCurrencyId,
                                                           UpdateCurrencyExchangeRate updateCurrencyExchangeRate, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCurrencyExchangeRate, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currencyExchangeRate/" + fromCurrencyId)
                    .queryParam("toCurrencyId", toCurrencyId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CurrencyExchangeRate> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CurrencyExchangeRate.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete CurrencyExchangeRate
    public boolean deleteCurrencyExchangeRate(String languageId, String companyId, String fromCurrencyId, String toCurrencyId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currencyExchangeRate/" + fromCurrencyId)
                    .queryParam("toCurrencyId", toCurrencyId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CurrencyExchangeRate
    public CurrencyExchangeRate[] findCurrencyExchangeRate(FindCurrencyExchangeRate findCurrencyExchangeRate, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "currencyExchangeRate/find");
            HttpEntity<?> entity = new HttpEntity<>(findCurrencyExchangeRate, headers);
            ResponseEntity<CurrencyExchangeRate[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CurrencyExchangeRate[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //=================================================OpStatus=================================================
    // Get All OpStatus Details
    public OpStatus[] getOpStatuses(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "opStatus");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<OpStatus[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, OpStatus[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get OpStatus
    public OpStatus getOpStatus(String companyId, String languageId, String statusCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "opStatus/" + statusCode)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<OpStatus> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, OpStatus.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create OpStatus
    public OpStatus createOpStatus(AddOpStatus newOpStatus, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "opStatus")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newOpStatus, headers);
            ResponseEntity<OpStatus> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, OpStatus.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update OpStatus
    public OpStatus updateOpStatus(String companyId, String languageId, String statusCode, String loginUserID,
                                   UpdateOpStatus updateOpStatus, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateOpStatus, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "opStatus/" + statusCode)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<OpStatus> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, OpStatus.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete OpStatus
    public boolean deleteOpStatus(String companyId, String languageId, String statusCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "opStatus/" + statusCode)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find OpStatus
    public OpStatus[] findOpStatus(FindOpStatus findOpStatus, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "opStatus/find");
            HttpEntity<?> entity = new HttpEntity<>(findOpStatus, headers);
            ResponseEntity<OpStatus[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, OpStatus[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    //=====================================================Event========================================================
    // Get All Event Details
    public Event[] getAllEvents(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "event");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Event[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Event[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Event
    public Event getEvent(String languageId, String companyId, String eventCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "event/" + eventCode)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Event> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Event.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Event
    public Event createEvent(AddEvent addEvent, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "event")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addEvent, headers);
            ResponseEntity<Event> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Event.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Event
    public Event updateEvent(String languageId, String companyId, String eventCode,
                             UpdateEvent updateEvent, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateEvent, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "event/" + eventCode)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Event> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Event.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Event
    public boolean deleteEvent(String languageId, String companyId, String eventCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "event/" + eventCode)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Event
    public Event[] findEvent(FindEvent findEvent, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "event/find");
            HttpEntity<?> entity = new HttpEntity<>(findEvent, headers);
            ResponseEntity<Event[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Event[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // ========================================Iata==============================================

    // Get All Iata Details
    public Iata[] getAllIata(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "iata");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Iata[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Iata[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // Get Iata
    public Iata getIata(String companyId, String languageId, String origin, String originCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "iata/" + originCode)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("origin", origin);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Iata> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Iata.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Iata
    public Iata createIata(AddIata addIata, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "iata")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addIata, headers);
            ResponseEntity<Iata> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Iata.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Iata
    public Iata updateIata(String companyId, String languageId, String origin, String originCode,
                           UpdateIata updateIata, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateIata, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "iata/" + originCode)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("origin", origin)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Iata> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Iata.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Iata
    public boolean deleteIata(String companyId, String origin, String originCode, String languageId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "iata/" + originCode)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("origin", origin)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Iata
    public Iata[] findIata(FindIata findIata, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "iata/find");
            HttpEntity<?> entity = new HttpEntity<>(findIata, headers);
            ResponseEntity<Iata[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Iata[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //----------------------------------------------------Module---------------------------------------------------------
    // Get All Module Details
    public Module[] getAllModule(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "module");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Module[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Module[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ModuleList
    public Module[] getModuleList(String companyId, String languageId, String moduleId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "module/modulelist/" + moduleId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Module[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Module[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Module
    public Module getModule(Long menuId, String companyId, String languageId, String moduleId, Long subMenuId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "module/" + moduleId)
                    .queryParam("companyId", companyId)
                    .queryParam("menuId", menuId)
                    .queryParam("subMenuId", subMenuId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Module> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Module.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // create module
    public Module[] createModule(List<AddModule> newModule, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "module")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newModule, headers);
            ResponseEntity<Module[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Module[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Patch Module
    public Module[] updateModule(String moduleId, String companyId, String languageId,
                                 String loginUserID, List<UpdateModule> updateModule, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateModule, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "module/" + moduleId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Module[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Module[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Module
    public boolean deleteModule(String moduleId, String companyId,
                                String languageId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "module/" + moduleId)
                    .queryParam("moduleId", moduleId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Module
    public Module[] findModule(FindModule findModule, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "module/find");
            HttpEntity<?> entity = new HttpEntity<>(findModule, headers);
            ResponseEntity<Module[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Module[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================SpecialApproval====================================================
    // Get All SpecialApproval Details
    public SpecialApproval[] getAllSpecialApproval(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "specialApproval");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<SpecialApproval[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, SpecialApproval[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get SpecialApproval
    public SpecialApproval getSpecialApproval(String companyId, String languageId, String specialApprovalId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "specialApproval/" + specialApprovalId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<SpecialApproval> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, SpecialApproval.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create SpecialApproval
    public SpecialApproval createSpecialApproval(AddSpecialApproval addSpecialApproval, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "specialApproval")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addSpecialApproval, headers);
            ResponseEntity<SpecialApproval> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, SpecialApproval.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update SpecialApproval
    public SpecialApproval updateSpecialApproval(String companyId, String languageId, String specialApprovalId, String loginUserID,
                                                 UpdateSpecialApproval updateSpecialApproval, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateSpecialApproval, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "specialApproval/" + specialApprovalId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<SpecialApproval> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, SpecialApproval.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete SpecialApproval
    public boolean deleteSpecialApproval(String companyId, String languageId, String specialApprovalId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "specialApproval/" + specialApprovalId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find SpecialApproval
    public SpecialApproval[] findSpecialApproval(FindSpecialApproval findSpecialApproval, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "specialApproval/find");
            HttpEntity<?> entity = new HttpEntity<>(findSpecialApproval, headers);
            ResponseEntity<SpecialApproval[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, SpecialApproval[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //==================================================RoleAccess====================================================
    // Get All RoleAccess Details

    public RoleAccess[] getAllRoleAccess(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "roleAccess");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RoleAccess[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RoleAccess[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get RoleAccess
    public RoleAccess getRoleAccess(String companyId, String languageId, Long roleId, Long menuId, Long subMenuId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "roleAccess/" + roleId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("menuId", menuId)
                    .queryParam("subMenuId", subMenuId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RoleAccess> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RoleAccess.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get RoleAccess List
    public RoleAccess[] getRoleAccessList(String companyId, String languageId, Long roleId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "roleAccess/" + roleId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RoleAccess[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RoleAccess[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create RoleAccess
    public RoleAccess[] createRoleAccess(List<AddRoleAccess> newRoleAccess, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "roleAccess")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newRoleAccess, headers);
            ResponseEntity<RoleAccess[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RoleAccess[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update RoleAccess
    public RoleAccess[] updateRoleAccess(String companyId, String languageId, Long roleId, String loginUserID,
                                         AddRoleAccess[] updateRoleAccess, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateRoleAccess, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "roleAccess/" + roleId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<RoleAccess[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, RoleAccess[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete RoleAccess
    public boolean deleteRoleAccess(String languageId, String companyId, Long roleId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "roleAccess/" + roleId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find RoleAccess
    public RoleAccess[] findRoleAccess(FindRoleAccess findRoleAccess, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "roleAccess/find");
            HttpEntity<?> entity = new HttpEntity<>(findRoleAccess, headers);
            ResponseEntity<RoleAccess[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RoleAccess[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================UserType====================================================
    // Get All UserType Details
    public UserType[] getAllUserType(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "userType");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<UserType[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, UserType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get UserType
    public UserType getUserType(Long userTypeId, String companyId, String languageId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "userType/" + userTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<UserType> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, UserType.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create UserType
    public UserType createUserType(AddUserType addUserType, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "userType")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addUserType, headers);
            ResponseEntity<UserType> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UserType.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update UserType
    public UserType updateUserType(String companyId, String languageId, Long userTypeId, String loginUserID,
                                   UpdateUserType updateUserType, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateUserType, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "userType/" + userTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<UserType> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, UserType.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete UserType
    public boolean deleteUserType(String companyId, String languageId, Long userTypeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "userType/" + userTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find UserType
    public UserType[] findUserType(FindUserType findUserType, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "userType/find");
            HttpEntity<?> entity = new HttpEntity<>(findUserType, headers);
            ResponseEntity<UserType[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UserType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    /*--------------------------------------------UserManagement--------------------------------------*/

    // GET - /login/validate User
    public UserManagement validateUserID(String userID, String password, String authToken, String version) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "login")
                    .queryParam("userID", userID)
                    .queryParam("password", password)
                    .queryParam("version", version);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<UserManagement> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, UserManagement.class);
            return result.getBody();
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }
    }

    // GET ALL UserManagements
    public UserManagement[] getAllUserManagements(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "usermanagement");
            ResponseEntity<UserManagement[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, UserManagement[].class);
//			log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET UserManagement
    public UserManagement getUserManagement(String userId, String companyId, String languageId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "usermanagement/" + userId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<UserManagement> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, UserManagement.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // POST
    public UserManagement createUserManagement(AddUserManagement addUserManagement, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "usermanagement")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addUserManagement, headers);
            ResponseEntity<UserManagement> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UserManagement.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    // PATCH UserManagement
    public UserManagement updateUserManagement(String userId, String loginUserID, String companyId, String languageId,
                                               UpdateUserManagement updateUserManagement, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateUserManagement, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "usermanagement/" + userId)
                    .queryParam("loginUserID", loginUserID)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            ResponseEntity<UserManagement> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, UserManagement.class);
            return result.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    // DELETE UserManagement
    public boolean deleteUserManagement(String userId, String companyId, String languageId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "usermanagement/" + userId)
                    .queryParam("loginUserID", loginUserID)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    // Find UserManagements
    public UserManagement[] findUserManagement(FindUserManagement findUserManagement, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "usermanagement/find");
            HttpEntity<?> entity = new HttpEntity<>(findUserManagement, headers);
            ResponseEntity<UserManagement[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UserManagement[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //==================================================Notification===================================================
    //Get All Notifications
    public Notification[] getAllNotification(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "notification");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Notification[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Notification[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Notification
    public Notification getNotification(String languageId, String companyId, String notificationId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "notification/" + notificationId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Notification> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Notification.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Notification
    public Notification createNotification(AddNotification addNotification, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "notification")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addNotification, headers);
            ResponseEntity<Notification> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Notification.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Notification
    public Notification updateNotification(String languageId, String companyId, String notificationId,
                                           UpdateNotification updateNotification, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateNotification, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "notification/" + notificationId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Notification> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Notification.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Notification
    public boolean deleteNotification(String languageId, String companyId, String notificationId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "notification/" + notificationId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Notification
    public Notification[] findNotification(FindNotification findNotification, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "notification/find");
            HttpEntity<?> entity = new HttpEntity<>(findNotification, headers);
            ResponseEntity<Notification[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Notification[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================AirportCode===================================================
    //Get All AirportCodes
    public AirportCode[] getAllAirportCode(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "airportCode");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<AirportCode[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, AirportCode[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get AirportCode
    public AirportCode getAirportCode(String languageId, String companyId, String airportCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "airportCode/" + airportCode)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<AirportCode> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, AirportCode.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create AirportCode
    public AirportCode createAirportCode(AddAirportCode addAirportCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "airportCode")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addAirportCode, headers);
            ResponseEntity<AirportCode> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, AirportCode.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update AirportCode
    public AirportCode updateAirportCode(String languageId, String companyId, String airportCode,
                                         UpdateAirportCode updateAirportCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateAirportCode, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "airportCode/" + airportCode)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<AirportCode> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, AirportCode.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete AirportCode
    public boolean deleteAirportCode(String languageId, String companyId, String airportCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "airportCode/" + airportCode)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find AirportCode
    public AirportCode[] findAirportCode(FindAirportCode findAirportCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "airportCode/find");
            HttpEntity<?> entity = new HttpEntity<>(findAirportCode, headers);
            ResponseEntity<AirportCode[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, AirportCode[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================StatusEvent===================================================
    //Get All StatusEvent
    public StatusEvent[] getAllStatusEvent(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "statusevent");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<StatusEvent[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, StatusEvent[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get StatusEvent
    public StatusEvent getStatusEvent(String companyId, String languageId, String typeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "statusevent/" + typeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<StatusEvent> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, StatusEvent.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create StatusEvent
    public StatusEvent createStatusEvent(AddStatusEvent addStatusEvent, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "statusevent")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addStatusEvent, headers);
            ResponseEntity<StatusEvent> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, StatusEvent.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update StatusEvent
    public StatusEvent updateStatusEvent(String companyId, String languageId, String typeId,
                                         UpdateStatusEvent updateStatusEvent, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateStatusEvent, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "statusevent/" + typeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<StatusEvent> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, StatusEvent.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete StatusEvent
    public boolean deleteStatusEvent(String companyId, String languageId, String typeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "statusevent/" + typeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find StatusEvent
    public StatusEvent[] findStatusEvent(FindStatusEvent findStatusEvent, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "statusevent/find");
            HttpEntity<?> entity = new HttpEntity<>(findStatusEvent, headers);
            ResponseEntity<StatusEvent[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, StatusEvent[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //==================================================UOM===================================================
    //Get All UOM
    public Uom[] getAllUom(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "uom");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Uom[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Uom[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get UOM
    public Uom getUom(String companyId, String languageId, String uomId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "uom/" + uomId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Uom> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Uom.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create UOM
    public Uom createUom(AddUom addUom, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "uom")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addUom, headers);
            ResponseEntity<Uom> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Uom.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update UOM
    public Uom updateUom(String companyId, String languageId, String uomId,
                         UpdateUom updateUom, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateUom, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "uom/" + uomId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Uom> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Uom.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete UOM
    public boolean deleteUom(String companyId, String languageId, String uomId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "uom/" + uomId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find UOM
    public Uom[] findUom(FindUom findUom, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "uom/find");
            HttpEntity<?> entity = new HttpEntity<>(findUom, headers);
            ResponseEntity<Uom[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Uom[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //=================================================AppUser=================================================
    // Get All AppUser Details
    public AppUser[] getAppUsers(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "appUser");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<AppUser[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, AppUser[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get AppUser
    public AppUser getAppUser(String companyId, String languageId, String appUserId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "appUser/" + appUserId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<AppUser> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, AppUser.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create AppUser
    public AppUser createAppUser(AddAppUser newAppUser, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "appUser")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newAppUser, headers);
            ResponseEntity<AppUser> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, AppUser.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update AppUser
    public AppUser updateAppUser(String companyId, String languageId, String appUserId, String loginUserID,
                                 UpdateAppUser updateAppUser, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateAppUser, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "appUser/" + appUserId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<AppUser> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, AppUser.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete AppUser
    public boolean deleteAppUser(String companyId, String languageId, String appUserId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "appUser/" + appUserId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find AppUser
    public AppUser[] findAppUser(FindAppUser findAppUser, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "appUser/find");
            HttpEntity<?> entity = new HttpEntity<>(findAppUser, headers);
            ResponseEntity<AppUser[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, AppUser[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    // GET - /login/mobile AppUser
    public AppUser validateMobileUserID(String appUserId, String password, String authToken, String version, String appUserType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "login/mobile")
                    .queryParam("appUserId", appUserId)
                    .queryParam("password", password)
                    .queryParam("version", version)
                    .queryParam("appUserType", appUserType);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<AppUser> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, AppUser.class);
            return result.getBody();
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }
    }

    /**
     * @param newHhtNotification
     * @param loginUserID
     * @param authToken
     * @return
     */
    public HhtNotification createHhtNotification(HhtNotification newHhtNotification, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hhtnotification")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newHhtNotification, headers);
            ResponseEntity<HhtNotification> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, HhtNotification.class);
//			log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param languageId
     * @param deviceId
     * @param userId
     * @param tokenId
     * @param authToken
     * @return
     */
    public HhtNotification getHhtNotification(String companyId, String languageId,
                                              String deviceId, String userId, String tokenId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hhtnotification")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("deviceId", deviceId)
                    .queryParam("userId", userId)
                    .queryParam("tokenId", tokenId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<HhtNotification> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, HhtNotification.class);
//			log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    // Update HhtNotification
    public HhtNotification updateHhtNotification(HhtNotification newHhtNotification, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(newHhtNotification, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hhtnotification/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<HhtNotification> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, HhtNotification.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find HhtNotifications
    public HhtNotification[] findHhtNotifications(FindHhtNotification findHhtNotification, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hhtnotification/find");
            HttpEntity<?> entity = new HttpEntity<>(findHhtNotification, headers);
            ResponseEntity<HhtNotification[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, HhtNotification[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    // Get All HhtNotifications
    public HhtNotification[] getAllHhtNotifications(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hhtnotification/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<HhtNotification[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, HhtNotification[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //==================================================Route===================================================
    //Get All Route
    public Route[] getAllRoute(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "route");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Route[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Route[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Route
    public Route getRoute(String companyId, String languageId, String routeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "route/" + routeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Route> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Route.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Route
    public Route createRoute(AddRoute addRoute, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "route")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addRoute, headers);
            ResponseEntity<Route> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Route.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Route
    public Route updateRoute(String companyId, String languageId, String routeId,
                             UpdateRoute updateRoute, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateRoute, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "route/" + routeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Route> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Route.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Route
    public boolean deleteRoute(String companyId, String languageId, String routeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "route/" + routeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Route
    public Route[] findRoute(FindRoute findRoute, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "route/find");
            HttpEntity<?> entity = new HttpEntity<>(findRoute, headers);
            ResponseEntity<Route[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Route[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================BillMode===================================================
    //Get All BillMode
    public BillMode[] getAllBillMode(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billmode");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<BillMode[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BillMode[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get BillMode
    public BillMode getBillMode(String companyId, String languageId, String billModeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billmode/" + billModeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<BillMode> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BillMode.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create BillMode
    public BillMode createBillMode(AddBillMode addBillMode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billmode")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addBillMode, headers);
            ResponseEntity<BillMode> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BillMode.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update BillMode
    public BillMode updateBillMode(String companyId, String languageId, String billModeId,
                                   UpdateBillMode updateBillMode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateBillMode, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billmode/" + billModeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<BillMode> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, BillMode.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete BillMode
    public boolean deleteBillMode(String companyId, String languageId, String billModeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billmode/" + billModeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find BillMode
    public BillMode[] findBillMode(FindBillMode findBillMode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billmode/find");
            HttpEntity<?> entity = new HttpEntity<>(findBillMode, headers);
            ResponseEntity<BillMode[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BillMode[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================Vehicle====================================================
    // Get All Vehicle Details
    public Vehicle[] getAllVehicles(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "vehicle");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Vehicle[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Vehicle[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Vehicle
    public Vehicle getVehicle(String companyId, String languageId, String vehicleRegNumber, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "vehicle/" + vehicleRegNumber)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Vehicle> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Vehicle.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Vehicle
    public Vehicle createVehicle(AddVehicle newVehicle, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "vehicle")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newVehicle, headers);
            ResponseEntity<Vehicle> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Vehicle.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Vehicle
    public Vehicle updateVehicle(String companyId, String languageId, String vehicleRegNumber, String loginUserID,
                                 UpdateVehicle updateVehicle, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateVehicle, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "vehicle/" + vehicleRegNumber)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Vehicle> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Vehicle.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Vehicle
    public boolean deleteVehicle(String companyId, String languageId, String vehicleRegNumber, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "vehicle/" + vehicleRegNumber)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Vehicle
    public Vehicle[] findVehicle(FindVehicle findVehicle, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "vehicle/find");
            HttpEntity<?> entity = new HttpEntity<>(findVehicle, headers);
            ResponseEntity<Vehicle[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Vehicle[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================DriverRouteAssignment====================================================
    // Get All DriverRouteAssignment Details
    public DriverRouteAssignment[] getAllDriverRouteAssignments(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "driverRouteAssignment");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<DriverRouteAssignment[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, DriverRouteAssignment[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get DriverRouteAssignment
    public DriverRouteAssignment getDriverRouteAssignment(String companyId, String languageId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "driverRouteAssignment/" + courierId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("routeId", routeId)
                    .queryParam("vehicleRegNumber", vehicleRegNumber)
                    .queryParam("assignedHubCode", assignedHubCode);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<DriverRouteAssignment> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, DriverRouteAssignment.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create DriverRouteAssignment
    public DriverRouteAssignment createDriverRouteAssignment(AddDriverRouteAssignment newDriverRouteAssignment, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "driverRouteAssignment")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newDriverRouteAssignment, headers);
            ResponseEntity<DriverRouteAssignment> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, DriverRouteAssignment.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update DriverRouteAssignment
    public DriverRouteAssignment updateDriverRouteAssignment(String companyId, String languageId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, String loginUserID,
                                                             UpdateDriverRouteAssignment updateDriverRouteAssignment, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateDriverRouteAssignment, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "driverRouteAssignment/" + courierId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("routeId", routeId)
                    .queryParam("vehicleRegNumber", vehicleRegNumber)
                    .queryParam("assignedHubCode", assignedHubCode)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<DriverRouteAssignment> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, DriverRouteAssignment.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete DriverRouteAssignment
    public boolean deleteDriverRouteAssignment(String companyId, String languageId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "driverRouteAssignment/" + courierId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("routeId", routeId)
                    .queryParam("vehicleRegNumber", vehicleRegNumber)
                    .queryParam("assignedHubCode", assignedHubCode)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find DriverRouteAssignment
    public DriverRouteAssignment[] findDriverRouteAssignment(FindDriverRouteAssignment findDriverRouteAssignment, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "driverRouteAssignment/find");
            HttpEntity<?> entity = new HttpEntity<>(findDriverRouteAssignment, headers);
            ResponseEntity<DriverRouteAssignment[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, DriverRouteAssignment[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================ZoneMaster===================================================
    //Get All ZoneMaster
    public ZoneMaster[] getAllZoneMaster(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zonemaster");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ZoneMaster[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ZoneMaster[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ZoneMaster
    public ZoneMaster getZoneMaster(String companyId, String languageId, String zoneId, String zoneType, String hubCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zonemaster/" + zoneId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("hubCode", hubCode)
                    .queryParam("zoneType", zoneType);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ZoneMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ZoneMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ZoneMaster
    public ZoneMaster createZoneMaster(AddZoneMaster addZoneMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zonemaster")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addZoneMaster, headers);
            ResponseEntity<ZoneMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ZoneMaster.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update ZoneMaster
    public ZoneMaster updateZoneMaster(String companyId, String languageId, String zoneId, String zoneType, String hubCode,
                                       UpdateZoneMaster updateZoneMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateZoneMaster, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zonemaster/" + zoneId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("zoneType", zoneType)
                    .queryParam("hubCode", hubCode)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ZoneMaster> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ZoneMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ZoneMaster
    public boolean deleteZoneMaster(String companyId, String languageId, String zoneId, String zoneType, String hubCode, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zonemaster/" + zoneId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("hubCode", hubCode)
                    .queryParam("zoneType", zoneType)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ZoneMaster
    public ZoneMaster[] findZoneMaster(FindZoneMaster findZoneMaster, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zonemaster/find");
            HttpEntity<?> entity = new HttpEntity<>(findZoneMaster, headers);
            ResponseEntity<ZoneMaster[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ZoneMaster[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================CourierPartner===================================================
    //Get All CourierPartner
    public CourierPartner[] getAllCourierPartner(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "courierPartner");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CourierPartner[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CourierPartner[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get CourierPartner
    public CourierPartner getCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "courierPartner/" + courierPartnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CourierPartner> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CourierPartner.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create CourierPartner
    public CourierPartner createCourierPartner(AddCourierPartner addCourierPartner, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "courierPartner")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCourierPartner, headers);
            ResponseEntity<CourierPartner> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CourierPartner.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update CourierPartner
    public CourierPartner updateCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId,
                                               UpdateCourierPartner updateCourierPartner, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCourierPartner, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "courierPartner/" + courierPartnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CourierPartner> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CourierPartner.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete CourierPartner
    public boolean deleteCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "courierPartner/" + courierPartnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CourierPartner
    public CourierPartner[] findCourierPartner(FindCourierPartner findCourierPartner, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "courierPartner/find");
            HttpEntity<?> entity = new HttpEntity<>(findCourierPartner, headers);
            ResponseEntity<CourierPartner[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CourierPartner[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // GET ALL
    public NotificationMessage[] getAllNotificationMessage(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("ClientGeneral-Agent", "MNRClara RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(getIDMasterServiceApiUrl() + "notificationmessage");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<NotificationMessage[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET,
                    entity, NotificationMessage[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Notification
    public NotificationMessage[] findNotificationMessage(FindNotificationMessage findNotificationMessage, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("ClientNote-Agent", "MNRClara RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(getIDMasterServiceApiUrl() + "notificationmessage/findNotification");
            HttpEntity<?> entity = new HttpEntity<>(findNotificationMessage, headers);
            ResponseEntity<NotificationMessage[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST,
                    entity, NotificationMessage[].class);
            log.info("result : " + result.getBody());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update NotificationMessage
    public NotificationMessage[] updateNotificationMessage(List<NotificationMessage> updateNotifications,
                                                           String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("ClientGeneral-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(updateNotifications, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(getIDMasterServiceApiUrl() + "notificationmessage/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<NotificationMessage[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH,
                    entity, NotificationMessage[].class);
            log.info("result : " + result.getBody());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // DeleteNotificationMessage

    /**
     * @param notificationId
     * @param companyId
     * @param languageId
     * @param houseAirwayBill
     * @param loginUserID
     * @param authToken
     * @return
     */
    public NotificationMessage deleteNotificationMessage(Long notificationId, String companyId, String languageId,
                                                         String houseAirwayBill, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(getIDMasterServiceApiUrl() + "notificationmessage/delete")
                    .queryParam("notificationId", notificationId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<NotificationMessage> result = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE,
                    entity, NotificationMessage.class);
            log.info("result : " + result.getBody());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================StorageTypeMaster===================================================
    //Get All StorageTypeMaster
    public StorageTypeMaster[] getAllStorageTypeMaster(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "storageTypeMaster");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<StorageTypeMaster[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, StorageTypeMaster[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get StorageTypeMaster
    public StorageTypeMaster getStorageTypeMaster(String companyId, String languageId, String storageTypeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "storageTypeMaster/" + storageTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<StorageTypeMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, StorageTypeMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create StorageTypeMaster
    public StorageTypeMaster createStorageTypeMaster(AddStorageTypeMaster addStorageTypeMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "storageTypeMaster")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addStorageTypeMaster, headers);
            ResponseEntity<StorageTypeMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, StorageTypeMaster.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update StorageTypeMaster
    public StorageTypeMaster updateStorageTypeMaster(String companyId, String languageId, String storageTypeId,
                                                     UpdateStorageTypeMaster updateStorageTypeMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateStorageTypeMaster, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "storageTypeMaster/" + storageTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<StorageTypeMaster> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, StorageTypeMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete StorageTypeMaster
    public boolean deleteStorageTypeMaster(String companyId, String languageId, String storageTypeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "storageTypeMaster/" + storageTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find StorageTypeMaster
    public StorageTypeMaster[] findStorageTypeMaster(FindStorageTypeMaster findStorageTypeMaster, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "storageTypeMaster/find");
            HttpEntity<?> entity = new HttpEntity<>(findStorageTypeMaster, headers);
            ResponseEntity<StorageTypeMaster[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, StorageTypeMaster[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //==================================================ZONE_TYPE_MASTER===================================================
    //Get All ZoneTypeMaster
    public ZoneTypeMaster[] getAllZoneTypeMaster(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zoneTypeMaster");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ZoneTypeMaster[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ZoneTypeMaster[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ZoneTypeMaster
    public ZoneTypeMaster getZoneTypeMaster(String companyId, String languageId, String zoneTypeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zoneTypeMaster/" + zoneTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ZoneTypeMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ZoneTypeMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ZoneTypeMaster
    public ZoneTypeMaster createZoneTypeMaster(AddZoneTypeMaster addZoneTypeMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zoneTypeMaster")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addZoneTypeMaster, headers);
            ResponseEntity<ZoneTypeMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ZoneTypeMaster.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update ZoneTypeMaster
    public ZoneTypeMaster updateZoneTypeMaster(String companyId, String languageId, String zoneTypeId,
                                               UpdateZoneTypeMaster updateZoneTypeMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateZoneTypeMaster, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zoneTypeMaster/" + zoneTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ZoneTypeMaster> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ZoneTypeMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ZoneTypeMaster
    public boolean deleteZoneTypeMaster(String companyId, String languageId, String zoneTypeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zoneTypeMaster/" + zoneTypeId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ZoneTypeMaster
    public ZoneTypeMaster[] findZoneTypeMaster
    (FindZoneTypeMaster findZoneTypeMaster, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "zoneTypeMaster/find");
            HttpEntity<?> entity = new HttpEntity<>(findZoneTypeMaster, headers);
            ResponseEntity<ZoneTypeMaster[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ZoneTypeMaster[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================LogicMaster===================================================
    //Get All LogicMaster
    public LogicMaster[] getAllLogicMaster(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "logicMaster");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<LogicMaster[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, LogicMaster[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get LogicMaster
    public LogicMaster getLogicMaster(String companyId, String languageId, String consoleCountId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "logicMaster/" + consoleCountId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<LogicMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, LogicMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create LogicMaster
    public LogicMaster createLogicMaster(AddLogicMaster addLogicMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "logicMaster")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addLogicMaster, headers);
            ResponseEntity<LogicMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, LogicMaster.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update LogicMaster
    public LogicMaster updateLogicMaster(String companyId, String languageId, String consoleCountId,
                                         UpdateLogicMaster updateLogicMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateLogicMaster, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "logicMaster/" + consoleCountId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<LogicMaster> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, LogicMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete LogicMaster
    public boolean deleteLogicMaster(String companyId, String languageId, String consoleCountId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "logicMaster/" + consoleCountId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find LogicMaster
    public LogicMaster[] findLogicMaster(FindLogicMaster findLogicMaster, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "logicMaster/find");
            HttpEntity<?> entity = new HttpEntity<>(findLogicMaster, headers);
            ResponseEntity<LogicMaster[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, LogicMaster[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Sort Master
    public SortingMaster[] createSortMaster(List<AddSortMaster> sortingMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "/sortmaster/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(sortingMaster, headers);
            ResponseEntity<SortingMaster[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, SortingMaster[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public SortingMaster[] updateSortMaster(List<UpdateSortMaster> sortingMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(sortingMaster, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "sortmaster/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<SortingMaster[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, SortingMaster[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public SortingMaster[] getAllSortMaster(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "sortmaster");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<SortingMaster[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, SortingMaster[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public boolean deleteSortMaster(List<SortMasterDeleteInput> sortingMasters, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(sortingMasters, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "sortmaster/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public CustomCharges[] createCustomCharge(List<AddCustomCharge> customCharges, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "/customCharge/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(customCharges, headers);
            ResponseEntity<CustomCharges[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomCharges[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public CustomCharges[] updateCustomCharge(List<UpdateCustomCharge> customCharges, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(customCharges, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customCharge/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CustomCharges[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CustomCharges[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public CustomCharges[] getAllCustomCharge(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customCharge");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CustomCharges[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CustomCharges[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteCustomCharge(List<CustomCharges> customCharges, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(customCharges, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customCharge/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public SortingMaster getSortingMaster(String languageId, String companyId, String sortingId, String zoneType, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "sortmaster/" + sortingId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("zoneType", zoneType);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<SortingMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, SortingMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public SortingMaster createSingleSortMaster(AddSortMaster addSortMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "sortmaster")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addSortMaster, headers);
            ResponseEntity<SortingMaster> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, SortingMaster.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public SortingMaster updateOneSortMaster(String languageId, String companyId, String zoneType, String sortingId, UpdateSortMaster updateSortMaster, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateSortMaster, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "sortmaster/" + sortingId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("zoneType", zoneType)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<SortingMaster> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, SortingMaster.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteOneSortMaster(String languageId, String companyId, String zoneType, String sortingId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "sortmaster/" + sortingId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("zoneType", zoneType)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * @param findSortMaster
     * @param authToken
     * @return
     */
    public SortingMaster[] findSortMaster(FindSortMaster findSortMaster, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "sortmaster/find");
            HttpEntity<?> entity = new HttpEntity<>(findSortMaster, headers);
            ResponseEntity<SortingMaster[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, SortingMaster[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param addCustomCharge
     * @param loginUserID
     * @param authToken
     * @return
     */
    public CustomCharges createCustom(AddCustomCharge addCustomCharge, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customCharge")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCustomCharge, headers);
            ResponseEntity<CustomCharges> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomCharges.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public CustomCharges updateCustom(String languageId, String companyId, UpdateCustomCharge updateCustomCharge, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCustomCharge, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customCharge")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CustomCharges> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CustomCharges.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public CustomCharges[] findCustomCharge(FindCustomCharge findCustomCharge, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customCharge/find");
            HttpEntity<?> entity = new HttpEntity<>(findCustomCharge, headers);
            ResponseEntity<CustomCharges[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomCharges[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================ReasonsListPickup====================================================
    // Get All ReasonsListPickup Details
    public ReasonsListPickup[] getAllReasonsListPickups(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListPickup");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ReasonsListPickup[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ReasonsListPickup[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ReasonsListPickup
    public ReasonsListPickup getReasonsListPickup(String companyId, String languageId, String reasonsId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListPickup/" + reasonsId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ReasonsListPickup> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ReasonsListPickup.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ReasonsListPickup
    public ReasonsListPickup createReasonsListPickup(AddReasonsListPickup newReasonsListPickup, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListPickup")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newReasonsListPickup, headers);
            ResponseEntity<ReasonsListPickup> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ReasonsListPickup.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update ReasonsListPickup
    public ReasonsListPickup updateReasonsListPickup(String companyId, String languageId, String reasonsId, String loginUserID,
                                                     UpdateReasonsListPickup updateReasonsListPickup, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateReasonsListPickup, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListPickup/" + reasonsId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ReasonsListPickup> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ReasonsListPickup.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ReasonsListPickup
    public boolean deleteReasonsListPickup(String companyId, String languageId, String reasonsId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListPickup/" + reasonsId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ReasonsListPickup
    public ReasonsListPickup[] findReasonsListPickup(FindReasonsListPickup findReasonsListPickup, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListPickup/find");
            HttpEntity<?> entity = new HttpEntity<>(findReasonsListPickup, headers);
            ResponseEntity<ReasonsListPickup[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ReasonsListPickup[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================ReasonsListDelivery====================================================
    // Get All ReasonsListDelivery Details
    public ReasonsListDelivery[] getAllReasonsListDeliveries(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListDelivery");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ReasonsListDelivery[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ReasonsListDelivery[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ReasonsListDelivery
    public ReasonsListDelivery getReasonsListDelivery(String companyId, String languageId, String reasonsId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListDelivery/" + reasonsId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ReasonsListDelivery> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ReasonsListDelivery.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ReasonsListDelivery
    public ReasonsListDelivery createReasonsListDelivery(AddReasonsListDelivery newReasonsListDelivery, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListDelivery")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(newReasonsListDelivery, headers);
            ResponseEntity<ReasonsListDelivery> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ReasonsListDelivery.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update ReasonsListDelivery
    public ReasonsListDelivery updateReasonsListDelivery(String companyId, String languageId, String reasonsId, String loginUserID,
                                                         UpdateReasonsListDelivery updateReasonsListDelivery, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateReasonsListDelivery, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListDelivery/" + reasonsId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ReasonsListDelivery> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ReasonsListDelivery.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ReasonsListDelivery
    public boolean deleteReasonsListDelivery(String companyId, String languageId, String reasonsId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListDelivery/" + reasonsId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ReasonsListDelivery
    public ReasonsListDelivery[] findReasonsListDelivery(FindReasonsListDelivery findReasonsListDelivery, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "reasonsListDelivery/find");
            HttpEntity<?> entity = new HttpEntity<>(findReasonsListDelivery, headers);
            ResponseEntity<ReasonsListDelivery[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ReasonsListDelivery[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Create List of HsCodes
    public HSCode[] createHSCodeBulk(List<AddHSCode> addHSCodes, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hsCode/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addHSCodes, headers);
            ResponseEntity<HSCode[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, HSCode[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Update List of HsCodes
    public HSCode[] updateHsCodeBulk(String hsCode, List<UpdateHSCode> updateHsCodeList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateHsCodeList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hsCode/update/list/" + hsCode)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<HSCode[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, HSCode[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Delete List of HsCodes
    public boolean deleteHsCodeBulk(String hsCode, List<HsCodeDeleteInput> hsCodeDeleteInputList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(hsCodeDeleteInputList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "hsCode/delete/list/" + hsCode)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public TimeSlot[] createTimeslot(List<AddTimeSlot> addTimeSlot, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "timeSlot/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addTimeSlot, headers);
            ResponseEntity<TimeSlot[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TimeSlot[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public TimeSlot[] updateTimeslot(List<AddTimeSlot> updateTimeslot, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateTimeslot, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "timeSlot/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<TimeSlot[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, TimeSlot[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteTimeslotList(List<TimeSlot> deleteTimeslotList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteTimeslotList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "timeSlot/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public TimeSlot[] getAllTimeslot(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "timeSlot/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<TimeSlot[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TimeSlot[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public TimeSlot getReplicaTimeslot(String languageId, String companyId, String timeslotId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "timeSlot/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("timeslotId", timeslotId);
            ResponseEntity<TimeSlot> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TimeSlot.class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public TimeSlot[] findTimeSlot(FindTimeSlot findTimeSlot, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "timeSlot/find");
            HttpEntity<?> entity = new HttpEntity<>(findTimeSlot, headers);
            ResponseEntity<TimeSlot[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TimeSlot[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public PaymentType[] createPaymentType(List<AddPaymentType> addPaymentTypes, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "paymentType/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addPaymentTypes, headers);
            ResponseEntity<PaymentType[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PaymentType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public PaymentType[] updatePaymentType(List<AddPaymentType> updatePaymentType, String loginUserID, String authToken) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updatePaymentType, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "paymentType/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<PaymentType[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, PaymentType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deletePaymentTypeList(List<PaymentType> deletePaymentList, String loginUserID, String authToken) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deletePaymentList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "paymentType/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public PaymentType[] getAllPaymentTypes(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "paymentType/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PaymentType[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PaymentType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public PaymentType getPaymentTypeid(String languageId, String companyId, String paymentTypeId, String authToken) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "paymentType/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("paymentTypeId", paymentTypeId);
            ResponseEntity<PaymentType> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PaymentType.class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public PaymentType[] findPaymentType(FindPaymentType findPaymentType, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "paymentType/find");
            HttpEntity<?> entity = new HttpEntity<>(findPaymentType, headers);
            ResponseEntity<PaymentType[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PaymentType[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RetailPrice[] createRetailPrice(List<AddRetailPrice> addRetailPrices, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "retailPrice/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addRetailPrices, headers);
            ResponseEntity<RetailPrice[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RetailPrice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RetailPrice[] updateRetailPrice(List<AddRetailPrice> updateRetailPrice, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateRetailPrice, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "retailPrice/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<RetailPrice[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, RetailPrice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteRetailPrice(List<RetailPrice> deleteRetailPrice, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteRetailPrice, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "retailPrice/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RetailPrice[] getAllRetailList(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "retailPrice/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RetailPrice[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RetailPrice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RetailPrice getRetailPriceId(String languageId, String companyId, String partnerId, Long lineNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "retailPrice/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("lineNo", lineNo);
            ResponseEntity<RetailPrice> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RetailPrice.class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RetailPrice[] findRetail(FindRetailPrice findRetailPrice, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "retailPrice/find");
            HttpEntity<?> entity = new HttpEntity<>(findRetailPrice, headers);
            ResponseEntity<RetailPrice[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RetailPrice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===============================================CodPriceList===================================================
    // Get All CodPriceList Details
    public CodPriceList[] getAllCodPriceList(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "/codPriceList/getall");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CodPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CodPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create CodPriceList List
    public CodPriceList[] createCodPriceList(List<AddCodPriceList> codPriceList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "/codPriceList/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(codPriceList, headers);
            ResponseEntity<CodPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CodPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update CodPriceList List
    public CodPriceList[] updateCodPriceList(List<CodPriceList> updateCodPriceList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCodPriceList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "codPriceList/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CodPriceList[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CodPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete CodPriceList List
    public boolean deleteCodPriceList(List<CodPriceList> codPriceList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(codPriceList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "codPriceList/delete/list")
                    .queryParam("codPriceList", codPriceList)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get CodPriceList
    public CodPriceList getCodPriceList(String languageId, String companyId, String partnerId, Long lineNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "codPriceList/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("lineNo", lineNo);
            ResponseEntity<CodPriceList> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CodPriceList.class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CodPriceList
    public CodPriceList[] findCodPriceList(FindCodPriceList findCodPriceList, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "codPriceList/find");
            HttpEntity<?> entity = new HttpEntity<>(findCodPriceList, headers);
            ResponseEntity<CodPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CodPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FulfillmentPrice[] createFulfillmentPrice(List<AddFulfillmentPrice> addFulfillmentPrices, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "fulfillmentPrice/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addFulfillmentPrices, headers);
            ResponseEntity<FulfillmentPrice[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FulfillmentPrice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FulfillmentPrice[] updateFulfillment(List<AddFulfillmentPrice> updateFulfillment, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateFulfillment, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "fulfillmentPrice/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<FulfillmentPrice[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, FulfillmentPrice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteFulfillmentList(List<FulfillmentPrice> deleteFulfillment, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteFulfillment, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "fulfillmentPrice/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FulfillmentPrice[] getAllFulfillment(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "fulfillmentPrice/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<FulfillmentPrice[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, FulfillmentPrice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FulfillmentPrice getFulfillmentid(String languageId, String companyId, String partnerId, Long lineNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "fulfillmentPrice/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("lineNo", lineNo);
            ResponseEntity<FulfillmentPrice> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, FulfillmentPrice.class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FulfillmentPrice[] findFulfillmentList(FindFulfillmentPrice findFulfillmentPrice, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "fulfillmentPrice/find");
            HttpEntity<?> entity = new HttpEntity<>(findFulfillmentPrice, headers);
            ResponseEntity<FulfillmentPrice[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FulfillmentPrice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RtoPriceList[] createRtoPriceList(List<AddRtoPrice> addRtoPrices, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rtoPriceList/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addRtoPrices, headers);
            ResponseEntity<RtoPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RtoPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RtoPriceList[] updateRtoPriceList(List<AddRtoPrice> updateRto, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateRto, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rtoPriceList/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<RtoPriceList[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, RtoPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteRtoPrice(List<RtoPriceList> deleteRto, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteRto, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rtoPriceList/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RtoPriceList[] getAllRtoPrice(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rtoPriceList/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RtoPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RtoPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RtoPriceList getRtoPrice(String languageId, String companyId, String partnerId, Long lineNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rtoPriceList/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("lineNo", lineNo);
            ResponseEntity<RtoPriceList> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RtoPriceList.class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RtoPriceList[] findRtoPriceList(FindRtoPrice findRtoPrice, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "rtoPriceList/find");
            HttpEntity<?> entity = new HttpEntity<>(findRtoPrice, headers);
            ResponseEntity<RtoPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RtoPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public AsrPriceList[] createAsrPriceList(List<AddAsrPriceList> addAsrPrices, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "asrPriceList/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addAsrPrices, headers);
            ResponseEntity<AsrPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, AsrPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public AsrPriceList[] updateAsrPriceList(List<AddAsrPriceList> updateAsr, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateAsr, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "asrPriceList/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<AsrPriceList[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, AsrPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteAsrPrice(List<AsrPriceList> deleteAsr, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteAsr, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "asrPriceList/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public AsrPriceList[] getAllAsrPrice(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "asrPriceList/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<AsrPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, AsrPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public AsrPriceList getAsrPrice(String languageId, String companyId, String partnerId, Long lineNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "asrPriceList/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("lineNo", lineNo);
            ResponseEntity<AsrPriceList> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, AsrPriceList.class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public AsrPriceList[] findAsrPriceList(FindAsrPriceList findAsrPriceList, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "asrPriceList/find");
            HttpEntity<?> entity = new HttpEntity<>(findAsrPriceList, headers);
            ResponseEntity<AsrPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, AsrPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public MovingShipmentPriceList[] createMovingShipmentPriceList(List<AddMovingShipmentPriceList> addMovingShipmentPriceLists, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "movingShipmentPriceList/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addMovingShipmentPriceLists, headers);
            ResponseEntity<MovingShipmentPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, MovingShipmentPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public MovingShipmentPriceList[] updateMovingShipmentPriceList(List<AddMovingShipmentPriceList> updateMovingShipmentPriceList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateMovingShipmentPriceList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "movingShipmentPriceList/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<MovingShipmentPriceList[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, MovingShipmentPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteMovingShipmentPriceList(List<MovingShipmentPriceList> deleteMovingShipmentPriceList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteMovingShipmentPriceList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "movingShipmentPriceList/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public MovingShipmentPriceList[] getAllMovingShipmentPriceList(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "movingShipmentPriceList/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<MovingShipmentPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, MovingShipmentPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public MovingShipmentPriceList getMovingShipmentPriceList(String languageId, String companyId, String partnerId, Long lineNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "movingShipmentPriceList/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("lineNo", lineNo);
            ResponseEntity<MovingShipmentPriceList> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, MovingShipmentPriceList.class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public MovingShipmentPriceList[] findMovingShipmentPriceList(FindMovingShipmentPriceList findMovingShipmentPriceList, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "movingShipmentPriceList/find");
            HttpEntity<?> entity = new HttpEntity<>(findMovingShipmentPriceList, headers);
            ResponseEntity<MovingShipmentPriceList[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, MovingShipmentPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public BillingFrequency[] createBillingFrequency(List<AddBillingFrequency> addBillingFrequencyLists, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billingFrequency/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addBillingFrequencyLists, headers);
            ResponseEntity<BillingFrequency[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BillingFrequency[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public BillingFrequency[] updateBillingFrequency(List<AddBillingFrequency> updateBillingFrequencyList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateBillingFrequencyList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billingFrequency/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<BillingFrequency[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, BillingFrequency[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteBillingFrequency(List<BillingFrequency> deleteBillingFrequencyList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteBillingFrequencyList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billingFrequency/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public BillingFrequency[] getAllBillingFrequency(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billingFrequency/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<BillingFrequency[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BillingFrequency[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public BillingFrequency getBillingFrequency(String languageId, String companyId, String billingFrequencyId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billingFrequency/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("billingFrequencyId", billingFrequencyId);
            ResponseEntity<BillingFrequency> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BillingFrequency.class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public BillingFrequency[] findBillingFrequency(FindBillingFrequency findBillingFrequency, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "billingFrequency/find");
            HttpEntity<?> entity = new HttpEntity<>(findBillingFrequency, headers);
            ResponseEntity<BillingFrequency[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BillingFrequency[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //==================================================CustomerCourierPartner===================================================
    //Get All CustomerCourierPartner
    public CustomerCourierPartner[] getAllCustomerCourierPartner(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customerCourierPartner");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CustomerCourierPartner[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CustomerCourierPartner[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get CustomerCourierPartner
    public CustomerCourierPartner getCustomerCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customerCourierPartner/" + courierPartnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CustomerCourierPartner> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CustomerCourierPartner.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create CustomerCourierPartner
    public CustomerCourierPartner createCustomerCourierPartner(AddCustomerCourierPartner addCustomerCourierPartner, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customerCourierPartner")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCustomerCourierPartner, headers);
            ResponseEntity<CustomerCourierPartner> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomerCourierPartner.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update CustomerCourierPartner
    public CustomerCourierPartner updateCustomerCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId,
                                               UpdateCustomerCourierPartner updateCustomerCourierPartner, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCustomerCourierPartner, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customerCourierPartner/" + courierPartnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CustomerCourierPartner> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CustomerCourierPartner.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete CustomerCourierPartner
    public boolean deleteCustomerCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customerCourierPartner/" + courierPartnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CustomerCourierPartner
    public CustomerCourierPartner[] findCustomerCourierPartner(FindCustomerCourierPartner findCustomerCourierPartner, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() + "customerCourierPartner/find");
            HttpEntity<?> entity = new HttpEntity<>(findCustomerCourierPartner, headers);
            ResponseEntity<CustomerCourierPartner[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomerCourierPartner[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}

