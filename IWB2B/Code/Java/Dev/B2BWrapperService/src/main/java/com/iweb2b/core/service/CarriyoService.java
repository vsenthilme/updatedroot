//package com.iweb2b.core.service;
//
//import com.iweb2b.core.client.IntegrationClient;
//import com.iweb2b.core.mapper.CarriyoMapper;
//import com.iweb2b.core.mapper.ConsignmentAdaptor;
//import com.iweb2b.core.model.auth.AuthToken;
//import com.iweb2b.core.model.carriyo.CarriyoShipmentRequest;
//import com.iweb2b.core.model.carriyo.CarriyoShipmentTrackingRequest;
//import com.iweb2b.core.model.carriyo.CarriyoShipmentsTrackingSearchResponse;
//import com.iweb2b.core.model.integration.ConsignmentTracking;
//import com.iweb2b.core.model.integration.asyad.Consignment;
//import com.iweb2b.core.util.CollectionUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class CarriyoService {
//
//    @Autowired
//    private AuthTokenService authTokenService;
//
//    @Autowired
//    private IntegrationClient integrationClient;
//
//    @Autowired
//    private CarriyoMapper carriyoMapper;
//
//    @Autowired
//    private ConsignmentAdaptor consignmentAdaptor;
//
//    @Autowired
//    private IntegrationService integrationService;
//
//    public CarriyoShipmentsTrackingSearchResponse searchShipmentsTrackingHistory(
//            CarriyoShipmentTrackingRequest request) {
//
//        AuthToken integAuthToken = authTokenService.getIntegrationServiceAuthToken();
//        String authToken = integAuthToken.getAccess_token();
//        String authorization = "Bearer " + authToken;
//
//        List<ConsignmentTracking> trackingList = CollectionUtils.stream(request.getTrackingNumbers())
//                .map(trackingNumber -> integrationClient.getConsignmentTrackingByRefNumber(authorization,
//                        trackingNumber)).collect(Collectors.toList());
//
//        return carriyoMapper.toResponse(trackingList);
//
//    }
//
//    public void createConsignment(String loginIdUser, CarriyoShipmentRequest request) {
//        Consignment consignment = consignmentAdaptor.adapt(request);
//        integrationService.createConsignment(consignment, loginIdUser);
//    }
//}