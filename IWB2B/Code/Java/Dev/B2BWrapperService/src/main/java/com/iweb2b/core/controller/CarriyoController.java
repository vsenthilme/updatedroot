//package com.iweb2b.core.controller;
//
//import com.iweb2b.core.model.carriyo.CarriyoShipmentRequest;
//import com.iweb2b.core.model.carriyo.CarriyoShipmentTrackingRequest;
//import com.iweb2b.core.model.carriyo.CarriyoShipmentsTrackingSearchResponse;
//import com.iweb2b.core.service.AuthService;
//import com.iweb2b.core.service.CarriyoService;
//import com.iweb2b.core.service.IntegrationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class CarriyoController {
//
//    @Autowired
//    private IntegrationService integrationService;
//
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private CarriyoService carriyoService;
//
//    @PostMapping("/shipments")
//    public void createShipment(@RequestHeader(value = "x-api-key") String authorization,
//            @RequestBody CarriyoShipmentRequest request) {
//
//        authService.validateAuthorization(authorization);
//
//        carriyoService.createConsignment("NEW_ORDER", request);
//    }
//
//    @GetMapping("/shipments/{tracking-number}/label")
//    public ResponseEntity<byte[]> getLabel(@PathVariable("tracking-number") String trackingNumber) {
//        return integrationService.getShippingLabelResponse(trackingNumber);
//    }
//
//    @PostMapping("/shipments/history")
//    public CarriyoShipmentsTrackingSearchResponse getConsignmentHistory(
//            @RequestHeader(value = "x-api-key") String authorization,
//            @RequestBody CarriyoShipmentTrackingRequest request) {
//
//        authService.validateAuthorization(authorization);
//
//        return carriyoService.searchShipmentsTrackingHistory(request);
//    }
//
//}