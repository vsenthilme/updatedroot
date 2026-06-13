package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.config.PropertiesConfig;
import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

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
}