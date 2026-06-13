package com.tekclover.wms.api.masters.transaction.service;

import com.tekclover.wms.api.masters.exception.BadRequestException;
import com.tekclover.wms.api.masters.transaction.config.TPropertiesConfig;
import com.tekclover.wms.api.masters.transaction.model.auth.AuthToken;
import com.tekclover.wms.api.masters.transaction.model.dto.*;
import com.tekclover.wms.api.masters.transaction.model.inbound.gr.StorageBinPutAway;
import com.tekclover.wms.api.masters.transaction.model.inbound.v2.InboundOrderCancelInput;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TMastersService {

    @Autowired
    TPropertiesConfig TPropertiesConfig;

    @Autowired
    TAuthTokenService TAuthTokenService;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    private String getMastersServiceApiUrl() {
        return TPropertiesConfig.getMastersServiceUrl();
    }


    //--------------------------------------------------------------------------------------------------------------------
    // GET ImBasicData1
    public TImBasicData1 getImBasicData1ByItemCode(String itemCode, String warehouseId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            String url = getMastersServiceApiUrl() + "imbasicdata1/" + itemCode + "?warehouseId=" + warehouseId;
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
            URI uri = builder.build(false).toUri();
            log.info("uri-----------> : " + uri);
            ResponseEntity<TImBasicData1> result = getRestTemplate().exchange(uri, HttpMethod.GET, entity, TImBasicData1.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param itemCode
     * @param languageId
     * @param companyCodeId
     * @param plantId
     * @param warehouseId
     * @param uomId
     * @param authToken
     * @return
     */
    public TImBasicData1 getImBasicData1ByItemCode(String itemCode, String languageId, String companyCodeId, String plantId,
                                                   String warehouseId, String uomId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "imbasicdata1/" + itemCode)
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("languageId", languageId)
                            .queryParam("companyCodeId", companyCodeId)
                            .queryParam("plantId", plantId)
                            .queryParam("uomId", uomId);
            ResponseEntity<TImBasicData1> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TImBasicData1.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param itemCode
     * @param languageId
     * @param companyCodeId
     * @param plantId
     * @param warehouseId
     * @param uomId
     * @param manufacturerPartNo
     * @param authToken
     * @return
     */
    public TImBasicData1 getImBasicData1ByItemCode(String itemCode, String languageId, String companyCodeId, String plantId,
                                                   String warehouseId, String uomId, String manufacturerPartNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "imbasicdata1/" + itemCode)
                            .queryParam("companyCodeId", companyCodeId)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("manufacturerPartNo", manufacturerPartNo)
                            .queryParam("uomId", uomId);
            ResponseEntity<TImBasicData1> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TImBasicData1.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param itemCode
     * @param languageId
     * @param companyCodeId
     * @param plantId
     * @param warehouseId
     * @param manufacturerPartNo
     * @param authToken
     * @return
     */
    public TImBasicData1 getImBasicData1ByItemCodeV2(String itemCode, String languageId, String companyCodeId, String plantId,
                                                     String warehouseId, String manufacturerPartNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "imbasicdata1/v2/" + itemCode)
                            .queryParam("companyCodeId", companyCodeId)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("manufacturerPartNo", manufacturerPartNo);
            ResponseEntity<TImBasicData1> result =
                    restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, TImBasicData1.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param imBasicData
     * @param authToken
     * @return
     */
    public TImBasicData1 getImBasicData1ByItemCodeV2(ImBasicData imBasicData, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(imBasicData, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "imbasicdata1/v2/itemMaster");
            ResponseEntity<TImBasicData1> result =
                    restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, TImBasicData1.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param itemCode
     * @param languageId
     * @param companyCodeId
     * @param plantId
     * @param warehouseId
     * @param authToken
     * @return
     */
    public TImBasicData1 getImBasicData1ByItemCode(String itemCode, String languageId, String companyCodeId, String plantId,
                                                   String warehouseId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "imbasicdata1/" + itemCode)
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("languageId", languageId)
                            .queryParam("companyCodeId", companyCodeId)
                            .queryParam("plantId", plantId);
            ResponseEntity<TImBasicData1> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TImBasicData1.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // POST - /imbasicdata1
    public TImBasicData1 createImBasicData1(TImBasicData1 newTImBasicData1, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(newTImBasicData1, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "imbasicdata1")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<TImBasicData1> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity,
                    TImBasicData1.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *
     * @param newImBasicData1
     * @param loginUserID
     * @param authToken
     * @return
     */
    public TImBasicData1V2 createImBasicData1V2(TImBasicData1V2 newImBasicData1, String loginUserID, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(newImBasicData1, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "imbasicdata1/v2")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<TImBasicData1V2> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity,
                    TImBasicData1V2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //----------------------BOMHeader---------------------------------------------------------------------------

    // GET BomHeader
    public BomHeader getBomHeader(String parentItemCode, String warehouseId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "bomheader/" + parentItemCode)
                            .queryParam("warehouseId", warehouseId);
            ResponseEntity<BomHeader> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BomHeader.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // GET BomHeader-with companyCode
    public BomHeader getBomHeader(String parentItemCode, String warehouseId,
                                  String companyCode, String plantId, String languageId,
                                  String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "bomheader/" + parentItemCode)
                            .queryParam("companyCode", companyCode)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("warehouseId", warehouseId);
            ResponseEntity<BomHeader> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BomHeader.class);
            return result.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // GET BomLine - /{bomNumber}/bomline
    public BomLine[] getBomLine(Long bomLineNo, String warehouseId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "bomline/" + bomLineNo + "/bomline")
                            .queryParam("warehouseId", warehouseId);
            ResponseEntity<BomLine[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BomLine[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
//			throw e;
            return null;
        }
    }

    // GET BomLine - /{bomNumber}/bomline - with CompanyCode,Plant and Language Id's
    public BomLine[] getBomLine(Long bomLineNo, String companyCode, String plantId, String languageId, String warehouseId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "bomline/" + bomLineNo + "/bomline")
                            .queryParam("companyCode", companyCode)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("warehouseId", warehouseId);
            ResponseEntity<BomLine[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BomLine[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
//			throw e;
            return null;
        }
    }

    //------------------------------StorageBin--------------------------------------------------------------
    // /{storageBin}
    // GET StorageBin - /{warehouseId}/bins/{binClassId}
    public TStorageBin getStorageBin(String storageBin, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/" + storageBin);
            ResponseEntity<TStorageBin> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TStorageBin.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBin - /{storageBin}/warehouseId
    public TStorageBin getStorageBin(String storageBin, String warehouseId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/" + storageBin + "/warehouseId")
                            .queryParam("warehouseId", warehouseId);

            ResponseEntity<TStorageBin> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TStorageBin.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBin - /{warehouseId}/bins/{binClassId}
    public TStorageBin getStorageBin(String warehouseId, Long binClassId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/" + warehouseId + "/bins/" + binClassId);
            ResponseEntity<TStorageBin> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TStorageBin.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBin - /{warehouseId}/bins/{binClassId} - v2
    public TTStorageBinV2 getStorageBin(String companyCode, String plantId,
                                        String languageId, String warehouseId, Long binClassId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/v2/" + warehouseId + "/bins/" + binClassId)
                            .queryParam("companyCode", companyCode)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId);
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBin - /{storageBin}/storageSections/{storageSectionId}
    public TStorageBin[] getStorageBin(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway");
            ResponseEntity<TStorageBin[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TStorageBin[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBin - /{storageBin}/warehouseId - V2
    public TTStorageBinV2 getStorageBinV2(String storageBin, String warehouseId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/v2/" + storageBin + "/warehouseId")
                            .queryParam("warehouseId", warehouseId);

            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBinV2 - /{storageBin}/storageSections/{storageSectionId}
    public TTStorageBinV2[] getStorageBinV2(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway/v2");
            ResponseEntity<TTStorageBinV2[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //CBM Proposed Bin
    public TTStorageBinV2 getStorageBinCBM(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway/cbm/v2");
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
//CBM Proposed Bin - Last Pick
    public TTStorageBinV2 getStorageBinCBMLastPick(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway/cbm/lastPicked/v2");
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
//CBM Proposed Bin - Last Pick
    public TTStorageBinV2 getStorageBinCBMPerQtyLastPick(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway/cbmPerQty/lastPicked/v2");
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Get a Bin
    public TTStorageBinV2 getaStorageBinV2(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/bin/v2");
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //CBMPerQty Proposed Bin
    public TTStorageBinV2 getStorageBinCbmPerQty(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway/cbmPerQty/v2");
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //NON CBM Proposed Bin
    public TTStorageBinV2 getStorageBinNonCbm(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway/nonCbm/v2");
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //NON CBM Proposed Bin - LastPicked Bin
    public TTStorageBinV2 getStorageBinNonCbmLastPicked(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway/nonCbm/lastPicked/v2");
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //getStorageBinBinClassId7 - Proposed Bin
    public TTStorageBinV2 getStorageBinBinClassId7(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway/binClass/v2");
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //NON CBM Proposed Existing Bin
    public TTStorageBinV2 getExistingStorageBinNonCbm(StorageBinPutAway storageBinPutAway, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(storageBinPutAway, headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/putaway/nonCbm/existing/v2");
            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBin - /{storageBin}/warehouseId - V2
    public TTStorageBinV2 getStorageBinV2(String storageBin, String warehouseId,
                                          String companyCode, String plantId,
                                          String languageId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/" + storageBin + "/warehouseId/v2")
                            .queryParam("companyCode", companyCode)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("warehouseId", warehouseId);

            ResponseEntity<TTStorageBinV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBin - /{warehouseId}/status
    public TStorageBin[] getStorageBinByStatus(String warehouseId, Long statusId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/" + warehouseId + "/status")
                            .queryParam("statusId", statusId);
            ResponseEntity<TStorageBin[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TStorageBin[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBin - /{warehouseId}/status - V2
    public TTStorageBinV2[] getStorageBinByStatusV2(String companyCode, String plantId, String languageId, String warehouseId, Long statusId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/" + warehouseId + "/status/v2")
                            .queryParam("companyCode", companyCode)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("statusId", statusId);
            ResponseEntity<TTStorageBinV2[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TTStorageBinV2[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GET StorageBin - /{warehouseId}/status
    public TStorageBin[] getStorageBinByStatusNotEqual(String warehouseId, Long statusId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/" + warehouseId + "/status-not-equal")
                            .queryParam("statusId", statusId);
            ResponseEntity<TStorageBin[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TStorageBin[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // GET By Storage Section Id - /sectionId
    public TStorageBin[] getStorageBinBySectionId(String warehouseId, List<String> stSectionIds, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/sectionId")
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("stSectionIds", stSectionIds);
            ResponseEntity<TStorageBin[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, TStorageBin[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // PATCH
    public TStorageBin updateStorageBin(String storageBin, TStorageBin modifiedTStorageBin, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(modifiedTStorageBin, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));

            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/" + storageBin)
                            .queryParam("loginUserID", loginUserID);

            ResponseEntity<TStorageBin> result =
                    restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, TStorageBin.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // PATCH - V2
    public TTStorageBinV2 updateStorageBinV2(String storageBin, TTStorageBinV2 modifiedStorageBin,
                                             String companyCodeId, String plantId,
                                             String languageId, String warehouseId,
                                             String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(modifiedStorageBin, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));

            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "storagebin/v2/" + storageBin)
                            .queryParam("companyCodeId", companyCodeId)
                            .queryParam("plantId", plantId)
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("languageId", languageId)
                            .queryParam("loginUserID", loginUserID);

            ResponseEntity<TTStorageBinV2> result =
                    restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, TTStorageBinV2.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //------------------------------HandlingEquipment--------------------------------------------------------------
    // GET HandlingEquipment - /{heBarcode}/barCode
    public HandlingEquipment getHandlingEquipment(String warehouseId, String heBarcode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "handlingequipment/" + heBarcode + "/barCode")
                            .queryParam("warehouseId", warehouseId);
            ResponseEntity<HandlingEquipment> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, HandlingEquipment.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //------------------------------Business Partner----------------------------------------------------------------
    public BusinessPartner getBusinessPartner(String partnerCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "businesspartner/" + partnerCode);
            ResponseEntity<BusinessPartner> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BusinessPartner.class);
            return result.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    public BusinessPartnerV2 getBusinessPartnerV2(String companyCodeId, String plantId, String languageId,
                                                  String warehouseId, Long businessPartnerType, String partnerCode, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "businesspartner/" + partnerCode)
                            .queryParam("companyCodeId", companyCodeId)
                            .queryParam("plantId", plantId)
                            .queryParam("languageId", languageId)
                            .queryParam("warehouseId", warehouseId)
                            .queryParam("businessPartnerType", businessPartnerType);
            ResponseEntity<BusinessPartnerV2> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BusinessPartnerV2.class);
            return result.getBody();
        } catch (Exception e) {
            throw e;
        }
    }
    // Send EMail
    public String sendMail(InboundOrderCancelInput inboundOrderCancelInput) {
        try {
            AuthToken authTokenForMasterService = TAuthTokenService.getMastersServiceAuthToken();
            String authToken = authTokenForMasterService.getAccess_token();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "email/sendMail");
            HttpEntity<?> entity = new HttpEntity<>(inboundOrderCancelInput, headers);
            ResponseEntity<String> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getLocalizedMessage());
        }
    }

    // Send EMail
    public String sendMailForNotification(InboundOrderCancelInput inboundOrderCancelInput) {
        try {
            AuthToken authTokenForMasterService = TAuthTokenService.getMastersServiceAuthToken();
            String authToken = authTokenForMasterService.getAccess_token();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMastersServiceApiUrl() + "email/sendMail/notification");
            HttpEntity<?> entity = new HttpEntity<>(inboundOrderCancelInput, headers);
            ResponseEntity<String> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getLocalizedMessage());
        }
    }
}