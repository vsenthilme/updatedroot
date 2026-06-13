//package com.iweb2b.core.client;
//
//import com.iweb2b.core.model.integration.ConsignmentTracking;
//import com.iweb2b.core.model.integration.asyad.Consignment;
//import com.iweb2b.core.model.integration.asyad.ConsignmentResponse;
//import com.iweb2b.core.model.integration.asyad.ConsignmentWebhook;
//import com.iweb2b.core.model.integration.asyad.JNTResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "b2b-integration-service", url = "${api.b2bintegration.service.url}")
//public interface IntegrationClient {
//
//    @GetMapping("/consignment/{referenceNumber}/shipment")
//    ConsignmentTracking getConsignmentTrackingByRefNumber(@RequestHeader("Authorization") String authorization,
//            @PathVariable("referenceNumber") String referenceNumber);
//
//    @PostMapping("/consignment/webhook")
//    JNTResponse sendConsignmentWebhook(@RequestHeader("Authorization") String authorization,
//            @RequestBody ConsignmentWebhook consignmentTracking);
//
//    @PostMapping("/softDataUpload/order")
//    ConsignmentResponse createConsignmentWebhook(@RequestHeader("Authorization") String authorization,
//            @RequestParam("loginUserID") String loginUserID, @RequestBody Consignment consignment);
//}