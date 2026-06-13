package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.config.PropertiesConfig;
import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.dto.ASNV2;
import com.tekclover.wms.api.mfg.model.dto.FetchImpl;
import com.tekclover.wms.api.mfg.model.dto.SalesOrderV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collections;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    AuthTokenService authTokenService;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    private String getIDMasterServiceApiUrl() {
        return propertiesConfig.getIdmasterServiceUrl();
    }

    private String getTransactionServiceApiUrl() {
        return propertiesConfig.getTransactionServiceUrl();
    }

    private String getTransactionAuthToken() {
        return authTokenService.getTransactionServiceAuthToken().getAccess_token();
    }

    //-------------------------------------------------------------------------------------------------------------------
    // GET
    public String getNextNumberRange(Long numberRangeCode, String warehouseId,
                                     String companyCodeId, String plantId,
                                     String languageId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getIDMasterServiceApiUrl() +
                                    "numberrange/nextNumberRange/" + numberRangeCode + "/v2")
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("companyCodeId", companyCodeId)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getLocalizedMessage());
        }
    }
    /**
     * @param fetch
     */
    public void createPutAwayHeader(FetchImpl fetch) {
        try {
            String authToken = getTransactionAuthToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "grline/v2/create");
            HttpEntity<?> entity = new HttpEntity<>(fetch, headers);
            ResponseEntity<String> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    //Post SalesOrderV2
    public void postSalesOrderV2(@Valid SalesOrderV2 salesOrderV2) {
        try {
            String authToken = getTransactionAuthToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "ClassicWMS RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(getTransactionServiceApiUrl() + "warehouse/outbound/salesorderv2");
            HttpEntity<?> entity = new HttpEntity<>(salesOrderV2, headers);
            ResponseEntity<String> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result: {}", result.getStatusCode());
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }

    // POST - SO
    public void postASNV2(@Valid ASNV2 asnv2) {
        try {
            String authToken = getTransactionAuthToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "ClassicWMS RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "warehouse/inbound/asn/v2");
            HttpEntity<?> entity = new HttpEntity<>(asnv2, headers);
            ResponseEntity<String> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : {}", result.getStatusCode());
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }
}