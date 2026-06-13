package com.courier.overc360.api.service;

import com.courier.overc360.api.batch.dto.ConsignmentDto;
import com.courier.overc360.api.batch.dto.PieceDto;
import com.courier.overc360.api.config.PropertiesConfig;
import com.courier.overc360.api.model.auth.AuthToken;
import com.courier.overc360.api.model.idmaster.NotificationMessage;
import com.courier.overc360.api.model.lastmile.PickupEntity;
import com.courier.overc360.api.model.lastmile.UpdateFinance;
import com.courier.overc360.api.model.transaction.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MidMileService {


    @Autowired
    PropertiesConfig propertiesConfig;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    private String getMidMileServiceUrl() {
        return propertiesConfig.getMidMileServiceUrl();
    }

    @Autowired
    AuthTokenService authTokenService;

    /*-----------------------------------------------------------------------------------------------------------------------------------*/

    // GetALl
    public ConsignmentEntity[] getAllConsignment(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ConsignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ConsignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // Create new Consignment
    public ConsignmentEntity[] createConsignment(AddConsignment[] addConsignment, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addConsignment, headers);
        ResponseEntity<ConsignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentEntity[].class);
        return result.getBody();
    }

    // Update Consignment
    public ConsignmentEntity[] updateConsignment(List<UpdateConsignment> updateConsignment, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateConsignment, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/updateConsignment")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ConsignmentEntity[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ConsignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ConsignmentEntity
    public ConsignmentEntity[] findConsignmentEntity(FindConsignment findConsignment, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find");
            HttpEntity<?> entity = new HttpEntity<>(findConsignment, headers);
            ResponseEntity<ConsignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Consignments - MobileApp
    public ConsignmentEntity[] findConsignmentMobileApp(List<FindConsignmentMobileApp> findConsignments, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/mobileApp");
            HttpEntity<?> entity = new HttpEntity<>(findConsignments, headers);
            ResponseEntity<ConsignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Consignments - MobileApp OutScan
    public FindConsignmentOutScanResponse[] findConsignmentOutScanResponses(FindConsignmentOutScanMobApp findConsignments, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/outscan/storage/mobileApp");
            HttpEntity<?> entity = new HttpEntity<>(findConsignments, headers);
            ResponseEntity<FindConsignmentOutScanResponse[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FindConsignmentOutScanResponse[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find IConsignmentEntity - preAlertValidationIndicator
    public IConsignment[] findIConsignmentEntity(FindIConsignment findConsignment, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/v2");
            HttpEntity<?> entity = new HttpEntity<>(findConsignment, headers);
            ResponseEntity<IConsignment[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, IConsignment[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find PreAlertManifest - based on consignment details
    public ConsignmentEntity[] findPreAlertManifest(FindPreAlertManifest findPreAlertManifest, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/findPreAlertManifest");
            HttpEntity<?> entity = new HttpEntity<>(findPreAlertManifest, headers);
            ResponseEntity<ConsignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find PreAlertManifest - based on Item details
    public PreAlertManifest[] findPreAlertManifestV2(FindPreAlertManifest findPreAlertManifest, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "itemDetails/findPreAlertManifest");
            HttpEntity<?> entity = new HttpEntity<>(findPreAlertManifest, headers);
            ResponseEntity<PreAlertManifest[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PreAlertManifest[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * DeleteConsignment
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @param loginUserID
     * @param authToken
     * @return
     */
    public boolean deleteConsignment(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
                                     String pieceId, String pieceItemId, String loginUserID, String imageRefId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("imageRefId", imageRefId)
                    .queryParam("pieceItemId", pieceItemId)
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
     * @param consignmentDeletes
     * @param loginUserID
     * @param authToken
     * @return
     */
    public boolean deleteConsignmentMultiple(List<ConsignmentDelete> consignmentDeletes, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(consignmentDeletes, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //=====================================================ImageReference========================================================
    // Get All ImageReference Details
    public ImageReference[] getAllImageReferences(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "imageReference");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ImageReference[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ImageReference[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ImageReference
    public ImageReference getImageReference(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, String pieceItemId, String imageRefId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "imageReference/" + imageRefId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("pieceItemId", pieceItemId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ImageReference> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ImageReference.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ImageReference
    public ImageReference createImageReference(AddImageReference addImageReference, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "imageReference")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addImageReference, headers);
        ResponseEntity<ImageReference> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ImageReference.class);
        return result.getBody();
    }

    // Update ImageReference
    public ImageReference updateImageReference(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId,
                                               String pieceItemId, String imageRefId, UpdateImageReference updateImageReference, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateImageReference, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "imageReference/" + imageRefId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("pieceItemId", pieceItemId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ImageReference> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ImageReference.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ImageReference
    public boolean deleteImageReference(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, String pieceItemId, String imageRefId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "imageReference/" + imageRefId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("pieceItemId", pieceItemId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ImageReference
    public ImageReference[] findImageReference(FindImageReference findImageReference, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "imageReference/find");
            HttpEntity<?> entity = new HttpEntity<>(findImageReference, headers);
            ResponseEntity<ImageReference[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ImageReference[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================ItemDetails====================================================

    //Get All ItemDetails

    public ItemDetails[] getAllItemDetails(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "itemDetails");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ItemDetails[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ItemDetails[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ItemDetails
    public ItemDetails getItemDetails(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
                                      String pieceId, String pieceItemId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "itemDetails/" + pieceItemId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("houseAirwayBill", houseAirwayBill);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ItemDetails> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ItemDetails.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ItemDetails
    public ItemDetails createItemDetails(AddItemDetails addItemDetails, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "itemDetails")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addItemDetails, headers);
        ResponseEntity<ItemDetails> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ItemDetails.class);
        return result.getBody();
    }

    // Update ItemDetails
    public ItemDetails updateItemDetails(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
                                         String pieceId, String pieceItemId, UpdateItemDetails updateItemDetails, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateItemDetails, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "itemDetails/" + pieceItemId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ItemDetails> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ItemDetails.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ItemDetails
    public boolean deleteItemDetails(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
                                     String pieceId, String pieceItemId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "itemDetails/" + pieceItemId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ItemDetails
    public ItemDetails[] findItemDetails(FindItemDetails findItemDetails, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "itemDetails/find");
            HttpEntity<?> entity = new HttpEntity<>(findItemDetails, headers);
            ResponseEntity<ItemDetails[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ItemDetails[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //==================================================PieceDetails====================================================

    //Get All PieceDetails

    public PieceDetails[] getAllPieceDetails(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "piecedetails");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PieceDetails[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PieceDetails[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get PieceDetails
    public PieceDetails getPieceDetails(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                        String houseAirwayBill, String pieceId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "piecedetails/" + pieceId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PieceDetails> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PieceDetails.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create PieceDetails
    public PieceDetails createPieceDetails(AddPieceDetails addPieceDetails, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "piecedetails")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addPieceDetails, headers);
        ResponseEntity<PieceDetails> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PieceDetails.class);
        return result.getBody();
    }

    // Update PieceDetails
    public PieceDetails updatePieceDetails(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
                                           String pieceId, UpdatePieceDetails updatePieceDetails, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updatePieceDetails, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "piecedetails/" + pieceId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<PieceDetails> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, PieceDetails.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete PieceDetails
    public boolean deletePieceDetails(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                      String houseAirwayBill, String pieceId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "piecedetails/" + pieceId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Find PieceDetails
    public PieceDetails[] findPieceDetails(FindPieceDetails findPieceDetails, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "piecedetails/find");
            HttpEntity<?> entity = new HttpEntity<>(findPieceDetails, headers);
            ResponseEntity<PieceDetails[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PieceDetails[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===============================================BondedManifest====================================================
    // Get All BondedManifest Details
    public BondedManifest[] getAllBondedManifest(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bondedManifest");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<BondedManifest[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BondedManifest[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // Get BondedManifest
    public BondedManifest getBondedManifest(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                            String houseAirwayBill, String bondedId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bondedManifest/" + bondedId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("masterAirwayBill", masterAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<BondedManifest> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BondedManifest.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create new BondedManifest
    public BondedManifest[] createBondedManifest(List<AddBondedManifest> addBondedManifest, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bondedManifest/create/list")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addBondedManifest, headers);
        ResponseEntity<BondedManifest[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BondedManifest[].class);
        return result.getBody();
    }

//    // Create new BondedManifest
//    public BondedManifest[] createBondedManifestBasedOnConsignment(List<ConsignmentEntity> addConsignments, String loginUserID, String authToken) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.add("User-Agent", "RestTemplate");
//        headers.add("Authorization", "Bearer " + authToken);
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bondedManifest/bondedmanifest/create")
//                .queryParam("loginUserID", loginUserID);
//        HttpEntity<?> entity = new HttpEntity<>(addConsignments, headers);
//        ResponseEntity<BondedManifest[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BondedManifest[].class);
//        return result.getBody();
//    }

    // Create new BondedManifests based on PreAlert Input
    public BondedManifest[] createBondedManifestListsOnPreAlertInput(List<PreAlert> preAlertList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bondedManifest/create/preAlert")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(preAlertList, headers);
            ResponseEntity<BondedManifest[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BondedManifest[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public BondedManifest[] updateBondedManifest(List<UpdateBondedManifest> updateBondedManifest, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateBondedManifest, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bondedManifest/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<BondedManifest[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, BondedManifest[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete BondedManifest
    public boolean deleteBondedManifest(List<BondedManifestDeleteInput> deleteInputList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInputList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bondedManifest/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find BondedManifest
    public BondedManifest[] findBondedManifest(FindBondedManifest findBondedManifest, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bondedManifest/findBondedManifest");
            HttpEntity<?> entity = new HttpEntity<>(findBondedManifest, headers);
            ResponseEntity<BondedManifest[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BondedManifest[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //===============================================Ccr====================================================
    // Get All Ccr Details
    public Ccr[] getAllCcr(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "ccr");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Ccr[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Ccr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // Get Ccr
    public Ccr getCcr(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                      String partnerHouseAirwayBill, String consoleId, String ccrId, String pieceId, String pieceItemId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "ccr/" + ccrId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("partnerMasterAirwayBill", partnerMasterAirwayBill)
                    .queryParam("partnerHouseAirwayBill", partnerHouseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("pieceItemId", pieceItemId)
                    .queryParam("consoleId", consoleId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Ccr> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Ccr.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create new Ccr
    public Ccr[] createCcr(List<AddCcr> addCcr, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "ccr/create/list")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addCcr, headers);
        ResponseEntity<Ccr[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Ccr[].class);
        return result.getBody();
    }

    //update Ccr
    public Ccr[] updateCcr(List<UpdateCcr> updateCcr, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCcr, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "ccr/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Ccr[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Ccr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Ccr
    public boolean deleteCcr(List<CcrDeleteInput> deleteInputList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInputList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "ccr/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Ccr
    public Ccr[] findCcr(FindCcr findCcr, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "ccr/findCcr");
            HttpEntity<?> entity = new HttpEntity<>(findCcr, headers);
            ResponseEntity<Ccr[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Ccr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //===============================================Console====================================================
    // Get All Console Details
    public Console[] getAllConsole(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Console[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Console[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Console
    public Console getConsole(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                              String partnerHouseAirwayBill, String consoleId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/" + consoleId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("partnerMasterAirwayBill", partnerMasterAirwayBill)
                    .queryParam("partnerHouseAirwayBill", partnerHouseAirwayBill);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Console> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Console.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create new Console
    public Console[] createConsole(List<AddConsole> addConsole, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/create/list")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addConsole, headers);
        ResponseEntity<Console[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Console[].class);
        return result.getBody();
    }

    // Update Console
    public Console[] updateConsole(List<UpdateConsole> updateConsole, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateConsole, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Console[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Console[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Console for Mobile App
    public Console[] updateConsoleForMobileApp(List<UpdateConsole> updateConsole, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateConsole, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/update/list/mobile")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Console[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Console[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param updateConsoleList
     * @param loginUserID
     * @param authToken
     * @return
     */
    public Console[] updateConsoleNormal(List<UpdateConsole> updateConsoleList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateConsoleList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/update/list/normal")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Console[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Console[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }


    // Delete Console
    public boolean deleteConsole(List<ConsoleDeleteInput> deleteInputList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInputList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Consoles - normal
    public Console[] findConsole(FindConsole findConsole, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/findConsole");
            HttpEntity<?> entity = new HttpEntity<>(findConsole, headers);
            ResponseEntity<Console[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Console[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Consoles - MobileApp
    public Console[] findConsoleMobileApp(FindConsole findConsole, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/findConsole/mobileApp");
            HttpEntity<?> entity = new HttpEntity<>(findConsole, headers);
            ResponseEntity<Console[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Console[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Consoles By Pagination
    public PaginatedResponse<Console> findConsolesByPagination(FindConsole findConsole, Integer pageNo, Integer pageSize,
                                                               String sortBy, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/findConsole/pagination")
                    .queryParam("pageNo", pageNo)
                    .queryParam("pageSize", pageSize)
                    .queryParam("sortBy", sortBy);
            HttpEntity<?> entity = new HttpEntity<>(findConsole, headers);
            ParameterizedTypeReference<PaginatedResponse<Console>> responseType = new ParameterizedTypeReference<PaginatedResponse<Console>>() {
            };
            ResponseEntity<PaginatedResponse<Console>> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, responseType);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

//    /**
//     *
//     * @param addConsignments
//     * @param loginUserID
//     * @param authToken
//     * @return
//     */
//    public Console[] createConsoleConsignmentInput(List<ConsignmentEntity> addConsignments, String loginUserID, String authToken){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.add("User-Agent", "RestTemplate");
//        headers.add("Authorization", " Bearer " + authToken);
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/create/list/con")
//                .queryParam("loginUserID", loginUserID);
//        HttpEntity<?> entity = new HttpEntity<>(addConsignments, headers);
//        ResponseEntity<Console[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Console[].class);
//        return result.getBody();
//    }

    /**
     * @param addPreAlert
     * @param loginUserID
     * @param authToken
     * @return
     */
    public Console[] createConsoleBasedOnPreAlertResponse(List<PreAlert> addPreAlert, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", " Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/create/list/con")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addPreAlert, headers);
        ResponseEntity<Console[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Console[].class);
        return result.getBody();
    }

    /**
     * @param transferConsole
     * @param loginUserID
     * @param authToken
     * @return
     */
    public Console[] transferConsole(List<TransferConsole> transferConsole, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/transfer")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(transferConsole, headers);
            ResponseEntity<Console[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Console[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================ConsignmentStatus==============================================
    // Get All ConsignmentStatus Details
    public ConsignmentStatus[] getAllConsignmentStatus(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignmentStatus");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ConsignmentStatus[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ConsignmentStatus[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ConsignmentStatus
    public ConsignmentStatus getConsignmentStatus(String languageId, String companyId, String houseAirwayBill,
                                                  String pieceId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignmentStatus/get")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("pieceId", pieceId)
                    .queryParam("houseAirwayBill", houseAirwayBill);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ConsignmentStatus> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ConsignmentStatus.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ConsignmentStatus
    public ConsignmentStatus createConsignmentStatus(AddConsignmentStatus addConsignmentStatus, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignmentStatus")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addConsignmentStatus, headers);
            ResponseEntity<ConsignmentStatus> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentStatus.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update ConsignmentStatus
    public ConsignmentStatus updateConsignmentStatus(String languageId, String companyId, String houseAirwayBill, String pieceId,
                                                     UpdateConsignmentStatus updateConsignmentStatus, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateConsignmentStatus, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignmentStatus/update")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ConsignmentStatus> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ConsignmentStatus.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ConsignmentStatus
    public boolean deleteConsignmentStatus(String languageId, String companyId, String houseAirwayBill,
                                           String pieceId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignmentStatus/delete")
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ConsignmentStatus
    public ConsignmentStatus[] findConsignmentStatus(FindConsignmentStatus findConsignmentStatus, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignmentStatus/find");
            HttpEntity<?> entity = new HttpEntity<>(findConsignmentStatus, headers);
            ResponseEntity<ConsignmentStatus[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentStatus[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //----------------------Upload------------------------------------------------------------------

    // POST - Consingment Upload
    public UploadApiResponse[] postConsignmentUpload(List<AddConsignment> consignmentList, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/upload")
                        .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(consignmentList, headers);
        ResponseEntity<UploadApiResponse[]> result =
                getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UploadApiResponse[].class);
        return result.getBody();
    }

    //----------------------getPdfLabelForm------------------------------------------------------------------
    // get pdf label form
    public PdfLabelFormOutput[] getPdfLabelForm(LabelFormInput labelFormInput, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "piecedetails/pdfLabel");
            HttpEntity<?> entity = new HttpEntity<>(labelFormInput, headers);
            ResponseEntity<PdfLabelFormOutput[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PdfLabelFormOutput[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ConsignmentInvoice
//    public ConsignmentInvoice[] findConsignmentInvoice(FindConsignmentInvoice findConsignmentInvoice, String authToken) throws Exception {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//            headers.add("User-Agent", "RestTemplate");
//            headers.add("Authorization", "Bearer " + authToken);
//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/findConsignmentInvoice");
//            HttpEntity<?> entity = new HttpEntity<>(findConsignmentInvoice, headers);
//            ResponseEntity<ConsignmentInvoice[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentInvoice[].class);
//            log.info("result : " + result.getStatusCode());
//            return result.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    // Find ConsignmentInvoice
    public InvoiceForm[] findConsignmentInvoice(FindConsignmentInvoice findConsignmentInvoice, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/consignmentInvoiceGenerate");
            HttpEntity<?> entity = new HttpEntity<>(findConsignmentInvoice, headers);
            ResponseEntity<InvoiceForm[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, InvoiceForm[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create New PreAlert

    /**
     * @param preAlerts
     * @param loginUserID
     * @param authToken
     * @return
     */
    public UploadApiResponse[] createPreAlert(List<PreAlert> preAlerts, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/post/prealert")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(preAlerts, headers);
        ResponseEntity<UploadApiResponse[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UploadApiResponse[].class);
        return result.getBody();
    }

    //    /**
//     *
//     * @param findPreAlert
//     * @param authToken
//     * @return
//     */
//    public PreAlert[] findPreAlert(FindPreAlert findPreAlert, String authToken) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//            headers.add("User-Agent", "RestTemplate");
//            headers.add("Authorization", "Bearer " + authToken);
//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/prealert");
//            HttpEntity<?> entity = new HttpEntity<>(findPreAlert, headers);
//            ResponseEntity<PreAlert[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PreAlert[].class);
//            return result.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    //==================================================PreAlert====================================================

    // Get All PreAlert Details
    public PreAlert[] getAllPreAlert(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "prealert");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PreAlert[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PreAlert[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Get PreAlert
    public PreAlert getPreAlert(String languageId, String companyId, String partnerId, String partnerHouseAirwayBill,
                                String partnerMasterAirwayBill, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "prealert/" + partnerId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerHouseAirwayBill", partnerHouseAirwayBill)
                    .queryParam("partnerMasterAirwayBill", partnerMasterAirwayBill);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PreAlert> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PreAlert.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Create PreAlert
    public PreAlert[] createPreAlerts(List<PreAlert> addPreAlert, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", " Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "prealert/post/list")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(addPreAlert, headers);
        ResponseEntity<PreAlert[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PreAlert[].class);
        return result.getBody();
    }

    // Update PreAlert
    public PreAlert[] updatePreAlert(List<UpdatePreAlert> updatePreAlert, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updatePreAlert, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "prealert/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<PreAlert[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, PreAlert[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete PreAlert
    public boolean deletePreAlert(List<PreAlertDeleteInput> deleteInputList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInputList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "prealert/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find PreAlert
    public PreAlert[] findPreAlerts(FindPreAlert findPreAlert, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "prealert/findPrealert");
            HttpEntity<?> entity = new HttpEntity<>(findPreAlert, headers);
            ResponseEntity<PreAlert[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PreAlert[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Mobile-App
     *
     * @param authToken
     * @return
     */
    public MobileApp[] getAllMobileApp(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/find/mobileapp");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<MobileApp[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, MobileApp[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find PreAlert
    public Console[] updateConsoleStatus(ConsoleStatus[] consoleStatus, String loginUserID, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/update/status")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(consoleStatus, headers);
            ResponseEntity<Console[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Console[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===============================================Unconsolidation===================================================
    // Get All Unconsolidation Details
    public Unconsolidation[] getAllUnconsolidations(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "unconsolidation");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Unconsolidation[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Unconsolidation[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Unconsolidation
    public Unconsolidation getUnconsolidation(String languageId, String companyId, String partnerId, String partnerHouseAirwayBill,
                                              String partnerMasterAirwayBill, String pieceId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "unconsolidation/get")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("pieceId", pieceId)
                    .queryParam("partnerHouseAirwayBill", partnerHouseAirwayBill)
                    .queryParam("partnerMasterAirwayBill", partnerMasterAirwayBill);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Unconsolidation> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Unconsolidation.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Unconsolidation
    public Unconsolidation createUnconsolidation(AddUnconsolidation addUnconsolidation, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", " Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "unconsolidation/create")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addUnconsolidation, headers);
            ResponseEntity<Unconsolidation> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Unconsolidation.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Unconsolidation
    public Unconsolidation updateUnconsolidation(UpdateUnconsolidation updateUnconsolidation, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateUnconsolidation, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "unconsolidation/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Unconsolidation> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Unconsolidation.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Unconsolidations - list
    public boolean deleteUnconsolidations(List<UnconsolidationDeleteInput> deleteInputList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInputList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "unconsolidation/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Unconsolidations
    public Unconsolidation[] findUnconsolidations(FindUnconsolidation findUnconsolidation, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "unconsolidation/find");
            HttpEntity<?> entity = new HttpEntity<>(findUnconsolidation, headers);
            ResponseEntity<Unconsolidation[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Unconsolidation[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //=====================================================Reports=====================================================
    // Get MobileDashboard - Console count
    public MobileDashboard getMobileDashboard(MobileDashboardRequest mobileDashboardRequest, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "ClassicWMS RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "reports/mobileDashboard");
            HttpEntity<?> entity = new HttpEntity<>(mobileDashboardRequest, headers);
            ResponseEntity<MobileDashboard> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, MobileDashboard.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Generate Location Sheet
    public LocationSheetOutput[] generateLocationSheet(List<LocationSheetInput> sheetInputs, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "reports/locationSheet")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(sheetInputs, headers);
            ResponseEntity<LocationSheetOutput[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, LocationSheetOutput[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Generate Console Tracking Report
    public ConsoleTrackingReportOutput[] generateConsoleTrackingReport(ConsoleTrackingReportInput sheetInputs, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "reports/consoleTrackingReport")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(sheetInputs, headers);
            ResponseEntity<ConsoleTrackingReportOutput[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsoleTrackingReportOutput[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Manual Console Create
     *
     * @param consoles
     * @param loginUserID
     * @param authToken
     * @return
     */
    public Console[] manualConsoleCreate(List<Console> consoles, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "/console/manual/create")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(consoles, headers);
            ResponseEntity<Console[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Console[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


//    // Generate Console Tracking Report - list screen
//    public ConsoleTrackingReportOutput[] generateConsoleTrackingReportListPage(ConsoleTrackingReportInput sheetInputs, String loginUserID, String authToken) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//            headers.add("User-Agent", "RestTemplate");
//            headers.add("Authorization", "Bearer " + authToken);
//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "reports/consoleTrackingReport/listScreen")
//                    .queryParam("loginUserID", loginUserID);
//            HttpEntity<?> entity = new HttpEntity<>(sheetInputs, headers);
//            ResponseEntity<ConsoleTrackingReportOutput[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsoleTrackingReportOutput[].class);
//            log.info("result : " + result.getStatusCode());
//            return result.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }


    //==================================================BagTracking==============================================
    // Get All BagTracking Details
    public BagTracking[] getAllBagTracking(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bagTracking");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<BagTracking[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BagTracking[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get BagTracking
    public BagTracking getBagTracking(String languageId, String companyId, String partnerId, Long consignmentBagId,
                                      String houseAirwayBill, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bagTracking/" + consignmentBagId)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("houseAirwayBill", houseAirwayBill);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<BagTracking> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, BagTracking.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create BagTracking
    public BagTracking[] createBagTracking(List<AddBagTracking> addBagTracking, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bagTracking")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addBagTracking, headers);
            ResponseEntity<BagTracking[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BagTracking[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create BagTracking from Pickup
    public BagTracking[] createBagTrackingFromPickup(List<PickupEntity> pickupEntities, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bagTracking/create/bagtracking/pickup")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(pickupEntities, headers);
            ResponseEntity<BagTracking[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BagTracking[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update BagTracking
    public BagTracking[] updateBagTracking(List<UpdateBagTracking> updateBagTracking, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateBagTracking, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bagTracking/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<BagTracking[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, BagTracking[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete BagTracking
    public boolean deleteBagTracking(List<BagTracking> deleteInput, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bagTracking/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find BagTracking
    public BagTracking[] findBagTracking(FindBagTracking findBagTracking, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bagTracking/find");
            HttpEntity<?> entity = new HttpEntity<>(findBagTracking, headers);
            ResponseEntity<BagTracking[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BagTracking[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //==================================================FuelTracking===================================================
    //Get All FuelTracking
    public FuelTracking[] getAllFuelTracking(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "fuelTracking");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<FuelTracking[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, FuelTracking[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get FuelTracking
    public FuelTracking getFuelTracking(String companyId, String languageId, String vehicleRegNumber, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "fuelTracking/" + vehicleRegNumber)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<FuelTracking> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, FuelTracking.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create FuelTracking
    public FuelTracking createFuelTracking(AddFuelTracking addFuelTracking, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "fuelTracking")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addFuelTracking, headers);
            ResponseEntity<FuelTracking> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FuelTracking.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update FuelTracking
    public FuelTracking updateFuelTracking(String companyId, String languageId, String vehicleRegNumber,
                                           UpdateFuelTracking updateFuelTracking, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateFuelTracking, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "fuelTracking/" + vehicleRegNumber)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<FuelTracking> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, FuelTracking.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete FuelTracking
    public boolean deleteFuelTracking(String companyId, String languageId, String vehicleRegNumber, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "fuelTracking/" + vehicleRegNumber)
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

    // Find FuelTracking
    public FuelTracking[] findFuelTracking(FindFuelTracking findFuelTracking, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "fuelTracking/find");
            HttpEntity<?> entity = new HttpEntity<>(findFuelTracking, headers);
            ResponseEntity<FuelTracking[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FuelTracking[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===============================================RiderAssignment===================================================
    // Get All RiderAssignment Details
    public RiderAssignmentEntity[] getAllRiderAssignments(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "riderAssignment");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RiderAssignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RiderAssignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create RiderAssignments
    public RiderAssignmentEntity[] createRiderConsignments(List<AddConsignment> consignmentList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "/riderAssignment")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(consignmentList, headers);
            ResponseEntity<RiderAssignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RiderAssignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update RiderAssignments
    public RiderAssignmentEntity[] updateRiderAssignments(List<RiderAssignmentEntity> updateRiderAssignmentList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateRiderAssignmentList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "riderAssignment/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<RiderAssignmentEntity[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, RiderAssignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete RiderAssignments
    public boolean deleteRiderAssignments(List<RiderAssignmentDeleteInput> deleteInputList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInputList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "riderAssignment/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get RiderAssignment
    public RiderAssignmentEntity getRiderAssignment(String languageId, String companyId, String partnerId, String houseAirwayBill,
                                                    String pickupId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "riderAssignment/get")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pickupId", pickupId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RiderAssignmentEntity> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RiderAssignmentEntity.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find RiderAssignments
    public RiderAssignmentEntity[] findRiderAssignments(FindRiderAssignment findRiderAssignment, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "riderAssignment/find");
            HttpEntity<?> entity = new HttpEntity<>(findRiderAssignment, headers);
            ResponseEntity<RiderAssignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RiderAssignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * CustomsCalculation Report
     *
     * @param authToken
     * @return
     */
    public CustomsCalculationReport[] findCustomsCalculationReport(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "reports/customsCalculation");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CustomsCalculationReport[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CustomsCalculationReport[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================Clearance_Charges===================================================
    //Get All ClearanceCharges
    public ClearanceCharges[] getAllClearanceCharges(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "clearanceCharges");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ClearanceCharges[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ClearanceCharges[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get ClearanceCharges
    public ClearanceCharges getClearanceCharges(Long clearanceChargesId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "clearanceCharges/" + clearanceChargesId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ClearanceCharges> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ClearanceCharges.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create ClearanceCharges
    public ClearanceCharges[] createClearanceCharges(List<AddClearanceCharges> addClearanceCharges, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "clearanceCharges")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addClearanceCharges, headers);
            ResponseEntity<ClearanceCharges[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ClearanceCharges[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update ClearanceCharges
    public ClearanceCharges[] updateClearanceCharges(List<ClearanceCharges> updateClearanceCharges, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateClearanceCharges, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "clearanceCharges/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ClearanceCharges[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ClearanceCharges[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete ClearanceCharges
    public boolean deleteClearanceCharges(List<ClearanceCharges> deleteInputList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInputList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "clearanceCharges/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ClearanceCharges
    public ClearanceCharges[] findClearanceCharges(FindClearanceCharges findClearanceCharges, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "clearanceCharges/find");
            HttpEntity<?> entity = new HttpEntity<>(findClearanceCharges, headers);
            ResponseEntity<ClearanceCharges[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ClearanceCharges[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //==================================================CustomsCosting===================================================
    //Get All CustomsCosting
    public CustomsCosting[] getAllCustomsCosting(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Rest Template");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CustomsCosting[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CustomsCosting[].class);
            log.info("result: " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get CustomsCosting
    public CustomsCosting getCustomsCosting(String companyId, String languageId, String customerId, String costCenter, Long lineNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/" + costCenter)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("customerId", customerId)
                    .queryParam("lineNo", lineNo);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CustomsCosting> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CustomsCosting.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create CustomsCosting
    public AddCustomsCosting[] createCustomsCosting(List<AddCustomsCosting> addCustomsCosting, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCustomsCosting, headers);
            ResponseEntity<AddCustomsCosting[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, AddCustomsCosting[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Create CustomsCosting
    public CustomsCosting[] createCustomsCostingForText(List<CustomsCosting> addCustomsCosting, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/cost/text")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCustomsCosting, headers);
            ResponseEntity<CustomsCosting[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomsCosting[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update CustomsCosting
    public CustomsCosting[] updateCustomsCosting(List<CustomsCosting> updateCustomsCosting, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCustomsCosting, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CustomsCosting[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CustomsCosting[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete CustomsCosting
    public boolean deleteCustomsCosting(List<CustomsCosting> deleteCustomsCostingList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteCustomsCostingList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete CustomsCosting
    public boolean deleteCustomsCostingMultiple(List<CustomsCosting> costCenter, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(costCenter, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/delete/multiple")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CustomsCosting
    public CustomsCosting[] findCustomsCosting(FindCustomsCosting findCustomsCosting, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/find");
            HttpEntity<?> entity = new HttpEntity<>(findCustomsCosting, headers);
            ResponseEntity<CustomsCosting[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomsCosting[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CustomsCosting
    public CustomsCostingInvoice[] findCustomsCostingGroupByCostCenter(FindCustomInvoice findCustomsCosting, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/findCustomsInvoice");
            HttpEntity<?> entity = new HttpEntity<>(findCustomsCosting, headers);
            ResponseEntity<CustomsCostingInvoice[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomsCostingInvoice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //=====================================================CustomsClearanceInvoice========================================================
    // Get All CustomsClearanceInvoice Details
    public CustomsClearanceInvoice[] getAllCustomsClearanceInvoices(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsClearanceInvoice/getall");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CustomsClearanceInvoice[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CustomsClearanceInvoice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get CustomsClearanceInvoice
    public CustomsClearanceInvoice getCustomsClearanceInvoice(String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoiceNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsClearanceInvoice/get")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerHouseAirwayBill", partnerHouseAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("invoiceNo", invoiceNo);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CustomsClearanceInvoice> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, CustomsClearanceInvoice.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create CustomsClearanceInvoice
    public CustomsClearanceInvoice createCustomsClearanceInvoice(AddCustomsClearanceInvoice addCustomsClearanceInvoice, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsClearanceInvoice/create")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addCustomsClearanceInvoice, headers);
            ResponseEntity<CustomsClearanceInvoice> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomsClearanceInvoice.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update CustomsClearanceInvoice
    public CustomsClearanceInvoice updateCustomsClearanceInvoice(String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoiceNo,
                                                                 UpdateCustomsClearanceInvoice updateCustomsClearanceInvoice, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateCustomsClearanceInvoice, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsClearanceInvoice/update")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerHouseAirwayBill", partnerHouseAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("invoiceNo", invoiceNo)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<CustomsClearanceInvoice> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, CustomsClearanceInvoice.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete CustomsClearanceInvoice
    public boolean deleteCustomsClearanceInvoice(String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoiceNo, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsClearanceInvoice/delete")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerHouseAirwayBill", partnerHouseAirwayBill)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("invoiceNo", invoiceNo)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CustomsClearanceInvoice
    public CustomsClearanceInvoice[] findCustomsClearanceInvoice(FindCustomsClearanceInvoice findCustomsClearanceInvoice, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsClearanceInvoice/find");
            HttpEntity<?> entity = new HttpEntity<>(findCustomsClearanceInvoice, headers);
            ResponseEntity<CustomsClearanceInvoice[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomsClearanceInvoice[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find CustomsClearanceInvoice
    public CustomClearanceInvoiceReport[] findCustomsClearanceInvoiceReport(FindInvoice findInvoice, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "reports/customInvoice");
            HttpEntity<?> entity = new HttpEntity<>(findInvoice, headers);
            ResponseEntity<CustomClearanceInvoiceReport[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomClearanceInvoiceReport[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //=====================================================Invoice========================================================
    // Get All Invoice Details
    public InvoiceHeader[] getAllInvoiceHeader(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "invoiceHeader");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<InvoiceHeader[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, InvoiceHeader[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get InvoiceHeader
    public InvoiceHeader getInvoiceHeader(String languageId, String companyId, String invoiceNo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "invoiceHeader/" + invoiceNo)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<InvoiceHeader> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, InvoiceHeader.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create InvoiceHeader
    public InvoiceHeader[] createInvoiceHeader(List<InvoiceHeader> invoiceHeaderList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "invoiceHeader")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(invoiceHeaderList, headers);
            ResponseEntity<InvoiceHeader[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, InvoiceHeader[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update InvoiceHeader
    public InvoiceHeader[] updateInvoiceHeader(List<InvoiceHeader> invoiceHeaderList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(invoiceHeaderList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "invoiceHeader/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<InvoiceHeader[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, InvoiceHeader[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete InvoiceHeader
    public boolean deleteInvoiceHeader(List<InvoiceHeader> deleteInvoiceHeader, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInvoiceHeader, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "invoiceHeader/delete")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find InvoiceHeader
    public InvoiceHeader[] findInvoiceHeader(FindInvoiceHeader findInvoiceHeader, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "invoiceHeader/find");
            HttpEntity<?> entity = new HttpEntity<>(findInvoiceHeader, headers);
            ResponseEntity<InvoiceHeader[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, InvoiceHeader[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public CustomCostingTotalResult[] findCustomCostingResPdf(CustomCostingTotal customCostingTotal, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/findCustomsCostingPdf");
            HttpEntity<?> entity = new HttpEntity<>(customCostingTotal, headers);
            ResponseEntity<CustomCostingTotalResult[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomCostingTotalResult[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // approve CustomsCosting
    public boolean approveCustomsCosting(String companyId, String languageId, String partnerMasterAirWayBill, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/approve/" + partnerMasterAirWayBill)
                    .queryParam("companyId", companyId)
                    .queryParam("languageId", languageId)
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Batch approve CustomsCosting
    public boolean batchApproveCustomsCosting(ApproveCustomCostingInput approveCustomCostingInput,
                                              String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/approve/batch")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(approveCustomCostingInput, headers);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //FinanceApproval-Batch approve CustomsCosting
    public boolean batchFinanceApproveCustomsCosting(ApproveCustomCostingInput approveCustomCostingInput,
                                                     String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "customsCosting/approve/batch/finance")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(approveCustomCostingInput, headers);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public HubCountResponse getHubDashboardCount(MobileDashboardRequest mobileDashboardRequest, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "ClassicWMS RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "reports/mobileDashboard/hub/count");
            HttpEntity<?> entity = new HttpEntity<>(mobileDashboardRequest, headers);
            ResponseEntity<HubCountResponse> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, HubCountResponse.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public FindInscanConsignment[] findInscanConsignments(String languageId, String companyId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/consignmentInScanList")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId);
            ResponseEntity<FindInscanConsignment[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FindInscanConsignment[].class);
            log.info("result : " + result);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ConsignmentEntity[] updateConsignmentInscanStatus(List<FindConsignmentScan> findConsignmentScans, String loginUserID, String authToken) {
        try {
            log.info("Consignment HubOps Input---------> " + findConsignmentScans);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(findConsignmentScans, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/update/hubOps/mobileApp")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ConsignmentEntity[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ConsignmentEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public UploadApiResponse[] updateInvoiceUrl(List<UpdateInvoice> updateInvoices, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateInvoices, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/update/invoiceurl")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<UploadApiResponse[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, UploadApiResponse[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FindConsignmentMobileResponse[] findReceivingConsignments(List<FindConsignmentMobileApp> findConsignments, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(findConsignments, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/receiving/mobileApp");
            ResponseEntity<FindConsignmentMobileResponse[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, FindConsignmentMobileResponse[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ConsignmentEntity updateReceivingStatus(String scanId, String statusId, String storageTypeId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/update/receivingStatus/mobileApp")
                    .queryParam("scanId", scanId)
                    .queryParam("statusId", statusId)
                    .queryParam("storageTypeId", storageTypeId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ConsignmentEntity> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ConsignmentEntity.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FindConsignmentMobileResponse[] findConsignmentHubOpsMobileApp(List<FindConsignmentMobileApp> findConsignments, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(findConsignments, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/outscan/mobileApp");
            ResponseEntity<FindConsignmentMobileResponse[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, FindConsignmentMobileResponse[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ConsignmentEntity updateOutScanStatus(String scanId, String statusId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/update/outscanStatus/mobileApp")
                    .queryParam("scanId", scanId)
                    .queryParam("statusId", statusId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ConsignmentEntity> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ConsignmentEntity.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // Update CustomsCosting
    public String updateBayanValue(List<BayanUpdate> bayanUpdates, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(bayanUpdates, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "ccr/update/bayan");
            ResponseEntity<String> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // Create new Consignment
    public ConsignmentEntity[] createPickupToConsignment(PickupEntity[] pickupEntities, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/pickup")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(pickupEntities, headers);
        ResponseEntity<ConsignmentEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentEntity[].class);
        return result.getBody();
    }

    public FindPieceRes[] findConsignmentPieceDetails(FindPieceReq findPieceReq, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "ClassicWMS RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/piece");
            HttpEntity<?> entity = new HttpEntity<>(findPieceReq, headers);
            ResponseEntity<FindPieceRes[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FindPieceRes[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find SpecialApproval
    public HsCode[] findSpecialApproval(ConsoleRequest consoleRequest, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "ClassicWMS RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/specialApproval");
            HttpEntity<?> entity = new HttpEntity<>(consoleRequest, headers);
            ResponseEntity<HsCode[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, HsCode[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FindPreAlertRes[] findPreAlertNew(FindPreAlert findPreAlert, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "prealert/findPrealert/new");
            HttpEntity<?> entity = new HttpEntity<>(findPreAlert, headers);
            ResponseEntity<FindPreAlertRes[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FindPreAlertRes[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public FindHouseAirwayBill[] findHouseAirwayBill(FindHAWB findHAWB, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(findHAWB, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/consignmentHAWBList");
            ResponseEntity<FindHouseAirwayBill[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, FindHouseAirwayBill[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ConsignmentStatus[] addConsignmentStatus(List<FindConsignmentNew> findConsignmentNew, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(findConsignmentNew, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignmentStatus/consignmentAdd")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ConsignmentStatus[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentStatus[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Console Update Upload
    public UploadApiResponse[] consoleUpload(List<Console> consoleList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/upload")
                            .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(consoleList, headers);
            ResponseEntity<UploadApiResponse[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UploadApiResponse[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Piece_Upload
    public UploadApiResponse[] pieceUpdateUpload(List<PieceDto> pieceDtoList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "piecedetails/upload")
                            .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(pieceDtoList, headers);
            ResponseEntity<UploadApiResponse[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UploadApiResponse[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Piece_Upload
    public UploadApiResponse[] consignmentUploadUpdate(List<ConsignmentDto> consignmentDtoList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/update/upload")
                            .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(consignmentDtoList, headers);
            ResponseEntity<UploadApiResponse[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UploadApiResponse[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public BagTracking[] createConsignmentToBagTrack(List<ConsignmentEntity> consignmentEntities, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "bagTracking/consignmentBagTrack")
                            .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(consignmentEntities, headers);
            ResponseEntity<BagTracking[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, BagTracking[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String sendNotificationForConsoleCreate(@RequestParam String companyId, @RequestParam String languageId,
                                                   @RequestParam String consoleId, @RequestParam String houseAirwayBill,
                                                   @RequestParam String consoleGroupName, @RequestParam String consoleName,
                                                   @RequestParam String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "console/send/notification")
                            .queryParam("companyId", companyId)
                            .queryParam("languageId", languageId)
                            .queryParam("consoleId", consoleId)
                            .queryParam("houseAirwayBill", houseAirwayBill)
                            .queryParam("consoleGroupName", consoleGroupName)
                            .queryParam("consoleName", consoleName);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update PreAlert
    public ReadMessages[] updateNotificationMessage(NotificationMessage[] notificationMessages, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(notificationMessages, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "notification/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ReadMessages[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ReadMessages[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // UpdateFinance
    public UpdateFinance updateFinance(UpdateFinance updateFinance, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateFinance, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/update/finance")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<UpdateFinance> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, UpdateFinance.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Finance
    public Finance[] findFinance(FindConsignment findConsignment, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(findConsignment, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/finance");
            ResponseEntity<Finance[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Finance[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Finance
    public PickupPriceList[] findPickupPriceList(PickupFinance[] pickupFinance, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(pickupFinance, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/pickup/priceList");
            ResponseEntity<PickupPriceList[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, PickupPriceList[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find ConsignmentEntity
    public ConsignmentDetailsImpl[] findConsignmentDetails(FindConsignment findConsignment, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/find/consignment/details");
            HttpEntity<?> entity = new HttpEntity<>(findConsignment, headers);
            ResponseEntity<ConsignmentDetailsImpl[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentDetailsImpl[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

//    public Map<String, List<ConsignmentEntity>> groupConsignmentByCityWithStatus(String authToken, String statusId) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//            headers.add("User-Agent", "MNRClara's RestTemplate");
//            headers.add("Authorization", "Bearer " + authToken);
//            HttpEntity<?> entity = new HttpEntity<>(headers);
//            HttpClient client = HttpClients.createDefault();
//            RestTemplate restTemplate = getRestTemplate();
//            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getMidMileServiceUrl() + "consignment/grouped-consignments-by-city")
//                    .queryParam("statusId", statusId);
//            ResponseEntity<Map<String, List<ConsignmentEntity>>> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, List<ConsignmentEntity>>>() {});
//            log.info("result : " + result.getStatusCode());
//            return result.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }