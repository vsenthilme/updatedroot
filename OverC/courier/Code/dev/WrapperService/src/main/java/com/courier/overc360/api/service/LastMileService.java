package com.courier.overc360.api.service;

import com.courier.overc360.api.config.PropertiesConfig;
import com.courier.overc360.api.model.auth.AuthToken;
import com.courier.overc360.api.model.lastmile.*;
import com.courier.overc360.api.model.lastmile.maps.*;
import com.courier.overc360.api.model.transaction.*;
import lombok.Data;
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
import java.util.List;


@Service
@Slf4j
public class LastMileService {

    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    AuthTokenService authTokenService;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    private String getLastMileServiceUrl() {
        return propertiesConfig.getLastMileServiceUrl();
    }

    /*------------------------------------------------------LAST_MILE-----------------------------------------------------------------------------*/

    //===============================================Pickup===================================================
    // Get All Pickup Details
    public PickupEntity[] getAllPickup(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PickupEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PickupEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Pickup
    public PickupEntity[] createPickupConsignments(List<PickupEntity> pickupEntities, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/pickup")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(pickupEntities, headers);
            ResponseEntity<PickupEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PickupEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Pickup
    public PickupEntity[] updatePickup(List<PickupEntity> updatePickupList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updatePickupList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<PickupEntity[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, PickupEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Pickup
    public boolean deletePickup(List<PickupDeleteInput> deleteInputList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInputList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Pickup
    public PickupEntity getPickup(String languageId, String companyId, String partnerId, String houseAirwayBill,
                                  String pickupId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/get")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("partnerId", partnerId)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pickupId", pickupId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PickupEntity> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PickupEntity.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Pickup
    public PickupEntity[] findPickup(FindPickup findPickup, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/find");
            HttpEntity<?> entity = new HttpEntity<>(findPickup, headers);
            ResponseEntity<PickupEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PickupEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public MobileDashboardResponse getPickupDashboardCount(MobileDashboardCount mobileDashboardCount, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "ClassicWMS RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "reports/mobileDashboard/count");
            HttpEntity<?> entity = new HttpEntity<>(mobileDashboardCount, headers);
            ResponseEntity<MobileDashboardResponse> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, MobileDashboardResponse.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

//    public DeliveryCountResponse getDeliveryDashboardCount(DeliveryMobileApp deliveryMobileApp, String authToken) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//            headers.add("User-Agent", "RestTemplate");
//            headers.add("Authorization", "Bearer " + authToken);
//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "reports/deliveryDashboard/count");
//            HttpEntity<?> entity = new HttpEntity<>(deliveryMobileApp,headers);
//            ResponseEntity<DeliveryCountResponse> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, DeliveryCountResponse.class);
//            log.info("result : " + result.getStatusCode());
//            return result.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    public FindPickupAssigned[] findPickupAssignedList(PickupMobileAppReq pickupMobileAppReq,String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/find/pickupAssigned");
            HttpEntity<?> entity = new HttpEntity<>(pickupMobileAppReq,headers);
            ResponseEntity<FindPickupAssigned[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FindPickupAssigned[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FindPickupAssignedTask[] findPickupAcceptedList(PickupMobileAppReq pickupMobileAppReq,String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/find/pickupAssigned/taskMobileApp");
            HttpEntity<?> entity = new HttpEntity<>(pickupMobileAppReq,headers);
            ResponseEntity<FindPickupAssignedTask[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FindPickupAssignedTask[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public PickupEntity[] updatePickupStatus(List<UpdatePickupStatus> updatePickup, String loginUserID, String authToken) {
        try {
            log.info("PickupStatusMobileApp Input-------------> " + updatePickup);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updatePickup, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/update/pickupStatus/mobileApp")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<PickupEntity[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, PickupEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     *
     * @param authToken
     * @return
     */
    public PickerAssignment[] getPickerAssignment(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "ClassicWMS RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/getPicker");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PickerAssignment[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PickerAssignment[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Location[] getLocationTspWithoutEta(DistanceMatrixRequest distanceMatrixRequest, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "distanceMatrix/solve-tsp");
            HttpEntity<?> entity = new HttpEntity<>(distanceMatrixRequest, headers);
            ResponseEntity<Location[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Location[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public DistanceMatrix[] getLocationTspWithDuration(List<DistanceMatrixRequest> distanceMatrixRequest, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "distanceMatrix/solve-tsp-with-duration");
            HttpEntity<?> entity = new HttpEntity<>(distanceMatrixRequest, headers);
            ResponseEntity<DistanceMatrix[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, DistanceMatrix[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public DistanceMatrix[] getLocationTspWithDurationV2(List<DistanceMatrixRequest> distanceMatrixRequest, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "distanceMatrix/solve-tsp-with-duration-v2");
            HttpEntity<?> entity = new HttpEntity<>(distanceMatrixRequest, headers);
            ResponseEntity<DistanceMatrix[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, DistanceMatrix[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public GeoInfo getGeoInfo(GeoInfo geoInfo, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "geoinfo/get");
            HttpEntity<?> entity = new HttpEntity<>(geoInfo, headers);
            ResponseEntity<GeoInfo> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, GeoInfo.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Sequential Location
     *
     * @param authToken
     * @return
     */
    public TspRouteSolution[] getLocationTsp(DistanceMatrixRequest distanceMatrixRequest, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "distanceMatrix/solve-tsp-with-eta");
            HttpEntity<?> entity = new HttpEntity<>(distanceMatrixRequest, headers);
            ResponseEntity<TspRouteSolution[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, TspRouteSolution[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public PickupEntity[] updatePickupByPieceId(List<PickupUpdateByPiece> pickupUpdateByPieces, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(pickupUpdateByPieces, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/update/pickupByPiece")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<PickupEntity[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, PickupEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Npr[] createNprList(List<Npr> nprList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "npr/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(nprList, headers);
            ResponseEntity<Npr[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Npr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Npr[] updateNprList(List<Npr> updateNprList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateNprList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "npr/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Npr[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Npr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteNprList(List<Npr> nprList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(nprList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "npr/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Npr[] getAllNpr(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/npr/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Npr[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Npr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Npr getNpr(String languageId, String companyId, String pickupId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "npr/get")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("pickupId", pickupId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Npr> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Npr.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Npr[] findNpr(FindNpr findNpr, String authToken) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "npr/find");
            HttpEntity<?> entity = new HttpEntity<>(findNpr, headers);
            ResponseEntity<Npr[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Npr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public ReSchedulePickUp[] createReschedulePickup(List<ReSchedulePickUp> reSchedulePickUps, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "reschedulePickup/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(reSchedulePickUps, headers);
            ResponseEntity<ReSchedulePickUp[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ReSchedulePickUp[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ReSchedulePickUp[] updateRescheduleList(List<ReSchedulePickUp> updateRescheduleList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateRescheduleList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "reschedulePickup/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<ReSchedulePickUp[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, ReSchedulePickUp[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteReschedulePickupList(List<ReSchedulePickUp> deleteRescheduleList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteRescheduleList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "reschedulePickup/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ReSchedulePickUp[] getAllReschedulePickup(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "reschedulePickup/getAll");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ReSchedulePickUp[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ReSchedulePickUp[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public ReSchedulePickUp getReplicaReschedule(String languageId, String companyId, String pickupId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "reschedulePickup/get")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("pickupId", pickupId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ReSchedulePickUp> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, ReSchedulePickUp.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ReSchedulePickUp[] findReschedulePickup(FindReschedulePickup findReschedulePickup, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "reschedulePickup/find");
            HttpEntity<?> entity = new HttpEntity<>(findReschedulePickup, headers);
            ResponseEntity<ReSchedulePickUp[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ReSchedulePickUp[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public PickupEntity[] createConsolidationPickup(FindConsolidation findConsolidation, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/consolidation")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(findConsolidation, headers);
            ResponseEntity<PickupEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PickupEntity[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ConsignmentStatus[] addConsignmentStatus(List<FindConsignmentData> addConsignmentStatuses, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/assign/consignmentUpdate")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(addConsignmentStatuses, headers);
            ResponseEntity<ConsignmentStatus[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, ConsignmentStatus[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Inner class to represent the solution with ETA
    public static class TspRouteSolution {
        private Location fromLocation;
        private Location toLocation;
        private double eta;  // ETA in seconds or minutes (depends on API)

        // Default constructor (required for Jackson deserialization)
        public TspRouteSolution() {
        }

        // Constructor, getters, and setters
        public TspRouteSolution(Location fromLocation, Location toLocation, double eta) {
            this.fromLocation = fromLocation;
            this.toLocation = toLocation;
            this.eta = eta;
        }

        // Getters and setters
        public Location getFromLocation() {
            return fromLocation;
        }

        public Location getToLocation() {
            return toLocation;
        }

        public double getEta() {
            return eta;
        }
    }

    // Class representing the Google Distance Matrix API response
    @Data
    private static class DistanceMatrixApiResponse {
        private List<Row> rows;

        // Inner class to represent the rows of the response
        @Data
        public static class Row {
            private List<Element> elements;
        }

        @Data
        public static class Element {
            private Duration duration;

            @Data
            public static class Duration {
                private double value;  // Duration in seconds

                public double getValue() {
                    return value;
                }
            }
        }
    }

    public Delivery[] createDelivery(List<Delivery> deliveryList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(deliveryList, headers);
            ResponseEntity<Delivery[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Delivery[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Delivery[] updateDelivery(List<Delivery> updateDelivery, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateDelivery, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Delivery[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Delivery[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteDelivery(List<Delivery> deleteDelivery, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteDelivery,headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Delivery[] getAllDelivery(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Delivery[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Delivery[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Delivery[] findDeliveryData(FindDelivery findDelivery, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/find");
            HttpEntity<?> entity = new HttpEntity<>(findDelivery, headers);
            ResponseEntity<Delivery[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Delivery[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public UploadApiResponse[] postPickupUpload(List<PickupEntity> pickupOrders, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup")
                            .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(pickupOrders, headers);
            ResponseEntity<UploadApiResponse[]> result =
                    getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UploadApiResponse[].class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * MobileTracking
     * @return
     */
    public MobileTracking[] findMobileTracking(MobileTracking[] mobileTracking) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            AuthToken lastMileServiceAuthToken = authTokenService.getLastMileServiceAuthToken();
            String authToken = lastMileServiceAuthToken.getAccess_token();
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "reports/mobileTrack");
            HttpEntity<?> entity = new HttpEntity<>(mobileTracking, headers);
            ResponseEntity<MobileTracking[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, MobileTracking[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *
     * @param authToken
     * @return
     */
    public PickerAssignment[] getDeliveryAssignment(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "ClassicWMS RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/getDelivery");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<PickerAssignment[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, PickerAssignment[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FindDeliveryAssigned[] findDeliveryAssignedList(DeliveryMobileAppReq deliveryMobileAppReq, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/find/deliveryAssigned");
            HttpEntity<?> entity = new HttpEntity<>(deliveryMobileAppReq,headers);
            ResponseEntity<FindDeliveryAssigned[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FindDeliveryAssigned[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FindDeliveryAssignedTask[] findDeliveryAcceptedList(DeliveryMobileAppReq deliveryMobileAppReq, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/find/deliveryAssigned/taskMobileApp");
            HttpEntity<?> entity = new HttpEntity<>(deliveryMobileAppReq,headers);
            ResponseEntity<FindDeliveryAssignedTask[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FindDeliveryAssignedTask[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Delivery[] updateDeliveryStatus(List<UpdateDeliveryStatus> updateDeliveryStatuses, String loginUserID, String authToken) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateDeliveryStatuses, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/update/deliveryStatus/mobileApp")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Delivery[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Delivery[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    //===============================================Drs===================================================
    // Get All Drs Details
    public Drs[] getAllDrs(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/drs/getall");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Drs[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Drs[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Drs List
    public Drs[] createDrsList(List<Drs> drsList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/drs/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(drsList, headers);
            ResponseEntity<Drs[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Drs[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Drs
    public Drs createDrs(Drs drs, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/drs/create")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(drs, headers);
            ResponseEntity<Drs> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Drs.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Drs List
    public Drs[] updateDrsList(List<Drs> updateDrsList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateDrsList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "drs/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Drs[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Drs[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Drs
    public Drs updateDrs(Drs updateDrs, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateDrs, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "drs/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Drs> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Drs.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Drs List
    public boolean deleteDrsList(List<Drs> drsList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(drsList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "drs/delete/list")
                    .queryParam("drsList", drsList)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Drs
    public boolean deleteDrs(String languageId, String companyId, String customerId, String houseAirwayBill, String pieceId,String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "drs/delete")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("customerId", customerId)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get Drs
    public Drs getDrs(String languageId, String companyId, String customerId, String houseAirwayBill, String pieceId,
                      String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "drs/get")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("customerId", customerId)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("pieceId", pieceId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Drs> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Drs.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Drs
    public Drs[] findDrs(FindDrs findDrs, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "drs/find");
            HttpEntity<?> entity = new HttpEntity<>(findDrs, headers);
            ResponseEntity<Drs[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Drs[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===============================================InventoryTable===================================================
    // Get All InventoryTable Details
    public InventoryTable[] getAllInventoryTable(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/inventoryTable/getall");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<InventoryTable[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, InventoryTable[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create InventoryTable List
    public InventoryTable[] createInventoryTableList(List<InventoryTable> inventoryTableList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/inventoryTable/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(inventoryTableList, headers);
            ResponseEntity<InventoryTable[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, InventoryTable[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create InventoryTable
    public InventoryTable createInventoryTable(InventoryTable inventoryTable, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/inventoryTable/create")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(inventoryTable, headers);
            ResponseEntity<InventoryTable> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, InventoryTable.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update InventoryTable List
    public InventoryTable[] updateInventoryTableList(List<InventoryTable> updateInventoryTableList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateInventoryTableList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "inventoryTable/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<InventoryTable[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, InventoryTable[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update InventoryTable
    public InventoryTable updateInventoryTable(InventoryTable updateInventoryTable, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateInventoryTable, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "inventoryTable/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<InventoryTable> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, InventoryTable.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete InventoryTable List
    public boolean deleteInventoryTableList(List<InventoryTable> inventoryTableList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(inventoryTableList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "inventoryTable/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete InventoryTable
    public boolean deleteInventoryTable(String languageId, String companyId, String customerId, String houseAirwayBill ,String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "inventoryTable/delete")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("customerId", customerId)
                    .queryParam("houseAirwayBill", houseAirwayBill)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get InventoryTable
    public InventoryTable getInventoryTable(String languageId, String companyId, String customerId, String houseAirwayBill,
                                            String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "inventoryTable/get")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("customerId", customerId)
                    .queryParam("houseAirwayBill", houseAirwayBill);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<InventoryTable> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, InventoryTable.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find InventoryTable
    public InventoryTable[] findInventoryTable(FindInventoryTable findInventoryTable, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "inventoryTable/find");
            HttpEntity<?> entity = new HttpEntity<>(findInventoryTable, headers);
            ResponseEntity<InventoryTable[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, InventoryTable[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===============================================Debrief===================================================

    // Create Debrief
    public Debrief createDebrief(Debrief debrief, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/debrief/create")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(debrief, headers);
            ResponseEntity<Debrief> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Debrief.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Debrief
    public Debrief updateDebrief(Debrief updateDebrief, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateDebrief, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "debrief/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Debrief> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Debrief.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Debrief
    public boolean deleteDebrief(String languageId, String companyId, String courierId, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "debrief/delete")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("courierId", courierId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find Debrief
    public Debrief[] findDebrief(FindDebrief findDebrief, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "debrief/find");
            HttpEntity<?> entity = new HttpEntity<>(findDebrief, headers);
            ResponseEntity<Debrief[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Debrief[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param loginUserID
     * @param authToken
     * @return
     */
    public UploadApiResponse[] createDeliveryUpload(List<Delivery> deliveries, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/create/list")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(deliveries, headers);
        ResponseEntity<UploadApiResponse[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, UploadApiResponse[].class);
        return result.getBody();
    }


    //===============================================RescheduleDelivery===================================================

    // Get All RescheduleDelivery Details
    public RescheduleDelivery[] getAllRescheduleDelivery(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/rescheduledelivery/getall");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RescheduleDelivery[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RescheduleDelivery[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create RescheduleDelivery
    public RescheduleDelivery createRescheduleDelivery(RescheduleDelivery drs, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/rescheduledelivery/create")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(drs, headers);
            ResponseEntity<RescheduleDelivery> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RescheduleDelivery.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update RescheduleDelivery
    public RescheduleDelivery updateRescheduleDelivery(UpdateRescheduleDelivery updateRescheduleDelivery, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateRescheduleDelivery, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "rescheduledelivery/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<RescheduleDelivery> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, RescheduleDelivery.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete RescheduleDelivery
    public boolean deleteRescheduleDelivery(String languageId, String companyId, String deliveryId ,String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "rescheduledelivery/delete")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("deliveryId", deliveryId)
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Get RescheduleDelivery
    public RescheduleDelivery getRescheduleDelivery(String languageId, String companyId, String deliveryId,
                                                    String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "rescheduledelivery/get")
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("deliveryId", deliveryId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<RescheduleDelivery> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, RescheduleDelivery.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find RescheduleDelivery
    public RescheduleDelivery[] findRescheduleDelivery(FindRescheduleDelivery findRescheduleDelivery, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "rescheduledelivery/find");
            HttpEntity<?> entity = new HttpEntity<>(findRescheduleDelivery, headers);
            ResponseEntity<RescheduleDelivery[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, RescheduleDelivery[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //===============================================Ndr===================================================
    // Get All Ndr Details
    public Ndr[] getAllNdr(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "ndr");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Ndr[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Ndr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create Ndr List
    public Ndr[] createNdrList(List<Ndr> ndrList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/ndr/create/list")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(ndrList, headers);
            ResponseEntity<Ndr[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Ndr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update Ndr List
    public Ndr[] updateNdrList(List<Ndr> updateNdrList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateNdrList, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "ndr/update/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<Ndr[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Ndr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete Ndr List
    public boolean deleteNdrList(List<Ndr> ndrList, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(ndrList, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "ndr/delete/list")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    public Ndr getNdr(String languageId, String companyId, String deliveryId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "ndr/"+ deliveryId)
                    .queryParam("languageId", languageId)
                    .queryParam("companyId", companyId)
                    .queryParam("deliveryId", deliveryId);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Ndr> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Ndr.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Find InventoryTable
    public Ndr[] findNdr(FindNdr findNdr, String authToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "ndr/find");
            HttpEntity<?> entity = new HttpEntity<>(findNdr, headers);
            ResponseEntity<Ndr[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Ndr[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Pickup To Consignment Create
    public PickupEntity[] createPickupToConsignment(List<ConsignmentEntity> consignmentEntityList, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", " Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/consignment")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(consignmentEntityList, headers);
        ResponseEntity<PickupEntity[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PickupEntity[].class);
        return result.getBody();
    }

    // Delivery To Consignment Create
    public Delivery[] createDeliveryFromConsignment(List<ConsignmentEntity> consignmentEntityList, String loginUserID, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "RestTemplate");
        headers.add("Authorization", " Bearer " + authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/consignment")
                .queryParam("loginUserID", loginUserID);
        HttpEntity<?> entity = new HttpEntity<>(consignmentEntityList, headers);
        ResponseEntity<Delivery[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, Delivery[].class);
        return result.getBody();
    }

    public DeliveryManifestMobileAppRes[] findDeliveryManifestMobileApp(DeliveryManifestMobAppInput deliveryManifestMobAppInput, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/find/outscan/delivery/manifest/mobileApp");
            HttpEntity<?> entity = new HttpEntity<>(deliveryManifestMobAppInput,headers);
            ResponseEntity<DeliveryManifestMobileAppRes[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, DeliveryManifestMobileAppRes[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FindOutScanMobileApp[] findDeliveryHawbMobileApp(FindOutScanInput findOutScanInput, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/find/outscan/delivery/hawb/mobileApp");
            HttpEntity<?> entity = new HttpEntity<>(findOutScanInput,headers);
            ResponseEntity<FindOutScanMobileApp[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, FindOutScanMobileApp[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Create RescheduleDelivery
    public LMDInvoiceHeader[] createLmdInvoice(List<LMDInvoiceHeader> lmdInvoiceHeaders, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "/invoice/create")
                    .queryParam("loginUserID", loginUserID);
            HttpEntity<?> entity = new HttpEntity<>(lmdInvoiceHeaders, headers);
            ResponseEntity<LMDInvoiceHeader[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, LMDInvoiceHeader[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update RescheduleDelivery
    public LMDInvoiceHeader[] updateInvoiceHeader(LMDInvoiceHeader[] lmdInvoiceHeaders, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "Classic WMS's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(lmdInvoiceHeaders, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "invoice/update")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<LMDInvoiceHeader[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, LMDInvoiceHeader[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete RescheduleDelivery
    public boolean deleteInvoice(DeleteInvoice[] deleteInvoice, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(deleteInvoice, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "invoice/delete")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<String> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
            log.info("result : " + result.getStatusCode());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public LMDInvoiceHeader[] findInvoiceHeader(FindInvoiceHeader findInvoiceHeader, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "invoice/find");
            HttpEntity<?> entity = new HttpEntity<>(findInvoiceHeader,headers);
            ResponseEntity<LMDInvoiceHeader[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, LMDInvoiceHeader[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // Update Status Delivery, Consignment & ConsignmentStatus
    public FindConsignmentOutScanInput[] updateStatusDeliveryConsignment(List<FindConsignmentOutScanInput> updateDelivery, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(updateDelivery, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/update/Status/mobileApp")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<FindConsignmentOutScanInput[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, FindConsignmentOutScanInput[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Manual_LMDInvoice_Header
    public LMDInvoiceHeader manualCreateInvoice(LMDInvoice lmdInvoice, String loginUserID, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "MNRClara's RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            HttpEntity<?> entity = new HttpEntity<>(lmdInvoice, headers);
            HttpClient client = HttpClients.createDefault();
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "invoice/manual")
                    .queryParam("loginUserID", loginUserID);
            ResponseEntity<LMDInvoiceHeader> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, LMDInvoiceHeader.class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GetDelivery StatusCount
    public DeliveryStatusCount[] findDeliveryStatusCount(StatusCountInput input, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "delivery/count");
            HttpEntity<?> entity = new HttpEntity<>(input, headers);
            ResponseEntity<DeliveryStatusCount[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, DeliveryStatusCount[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GetDelivery StatusCount
    public PickupStatusCount[] findPickupStatusCount(StatusCountInput input, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/count");
            HttpEntity<?> entity = new HttpEntity<>(input, headers);
            ResponseEntity<PickupStatusCount[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, PickupStatusCount[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // GetDelivery StatusCount
    public CustomerPickupStatusCount[] findCustomerPickupStatusCount(StatusCountInput input, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "RestTemplate");
            headers.add("Authorization", "Bearer " + authToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getLastMileServiceUrl() + "pickup/customerPickup/count");
            HttpEntity<?> entity = new HttpEntity<>(input, headers);
            ResponseEntity<CustomerPickupStatusCount[]> result = getRestTemplate().exchange(builder.toUriString(), HttpMethod.POST, entity, CustomerPickupStatusCount[].class);
            log.info("result : " + result.getStatusCode());
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
