package com.tekclover.wms.api.enterprise.transaction.service;

import com.tekclover.wms.api.enterprise.transaction.config.PropertiesConfig;
import com.tekclover.wms.api.enterprise.transaction.model.IKeyValuePair;
import com.tekclover.wms.api.enterprise.transaction.model.KeyValuePair;
import com.tekclover.wms.api.enterprise.transaction.model.dto.ImBasicData1;
import com.tekclover.wms.api.enterprise.transaction.model.dto.ImBasicData1V2;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.preinbound.v2.PreInboundHeaderEntityV2;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.v2.InboundOrderProcess;
import com.tekclover.wms.api.enterprise.transaction.model.warehouse.inbound.WarehouseApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    PropertiesConfig propertiesConfig;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    private String getTransactionServiceApiUrl() {
        return propertiesConfig.getTransactionServiceUrl();
    }


    //--------------------------------------------------------------------------------------------------------------------
    // GET PreInboundHeader
    public PreInboundHeaderEntityV2 getPreInboundHeaderForDuplicateCheckV2(String companyCode, String plantId, String languageId,
                                                                           String warehouseId, String refDocNumber, Long orderTypeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "preinboundheader/v2/duplicateCheck/" + refDocNumber)
                            .queryParam("companyCode", companyCode)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("orderTypeId", orderTypeId);
            ResponseEntity<PreInboundHeaderEntityV2> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PreInboundHeaderEntityV2.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public WarehouseApiResponse postInboundOrder(InboundOrderProcess inboundOrderProcess, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(inboundOrderProcess, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "orderprocess/inbound");
            ResponseEntity<WarehouseApiResponse> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity,
                                                                                     WarehouseApiResponse.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public KeyValuePair getDescription(String companyCode, String plantId, String languageId, String warehouseId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "preinboundheader/v2/description")
                            .queryParam("companyCode", companyCode)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("warehouseId", warehouseId);
            ResponseEntity<KeyValuePair> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, KeyValuePair.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getStockTypeDescription(String companyCode, String plantId, String languageId, String warehouseId, Long stockTypeId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getTransactionServiceApiUrl() + "preinboundheader/v2/description")
                            .queryParam("companyCode", companyCode)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("stockTypeId", stockTypeId);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }
}