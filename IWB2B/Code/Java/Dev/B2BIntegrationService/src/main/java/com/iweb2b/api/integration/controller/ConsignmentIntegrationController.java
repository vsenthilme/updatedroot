package com.iweb2b.api.integration.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iweb2b.api.integration.model.consignment.dto.CancelResponse;
import com.iweb2b.api.integration.model.consignment.dto.CancelShipmentRequest;
import com.iweb2b.api.integration.model.consignment.dto.Consignment;
import com.iweb2b.api.integration.model.consignment.dto.ConsignmentImpl;
import com.iweb2b.api.integration.model.consignment.dto.ConsignmentWebhook;
import com.iweb2b.api.integration.model.consignment.dto.FindConsignment;
import com.iweb2b.api.integration.model.consignment.dto.InventoryScanRequest;
import com.iweb2b.api.integration.model.consignment.dto.JNTResponse;
import com.iweb2b.api.integration.model.consignment.dto.OrderStatusUpdate;
import com.iweb2b.api.integration.model.consignment.dto.OrderStatusUpdateResponse;
import com.iweb2b.api.integration.model.consignment.dto.ajex.AddressUpdateRequest;
import com.iweb2b.api.integration.model.consignment.dto.ajex.AddressUpdateResponse;
import com.iweb2b.api.integration.model.consignment.dto.ajex.RescheduledDatesRequest;
import com.iweb2b.api.integration.model.consignment.dto.ajex.RescheduledDatesResponse;
import com.iweb2b.api.integration.model.consignment.dto.ajex.Willingness;
import com.iweb2b.api.integration.model.consignment.dto.ajex.WillingnessResponse;
import com.iweb2b.api.integration.model.consignment.dto.jnt.JNTOrderCreateRequest;
import com.iweb2b.api.integration.model.consignment.dto.jnt.JNTPrintLabelResponse;
import com.iweb2b.api.integration.model.consignment.dto.jnt.JNTWebhookRequest;
import com.iweb2b.api.integration.model.consignment.dto.qp.QPOrderCreateResponse;
import com.iweb2b.api.integration.model.consignment.dto.shopini.FindShopini;
import com.iweb2b.api.integration.model.consignment.dto.shopini.IShopiniWebhook;
import com.iweb2b.api.integration.model.consignment.dto.shopini.ShopiniWebhook;
import com.iweb2b.api.integration.model.consignment.entity.ConsignmentWebhookEntity;
import com.iweb2b.api.integration.model.consignment.entity.JNTWebhookEntity;
import com.iweb2b.api.integration.model.tracking.ConsignmentTracking;
import com.iweb2b.api.integration.service.ConsignmentTrackingService;
import com.iweb2b.api.integration.service.IntegrationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@CrossOrigin(origins = "*")
@Api(tags = {"Consignment"}, value = "Consignment Operations related to ConsignmentController")
@SwaggerDefinition(tags = {@Tag(name = "ConsignmentTracking", description = "Operations related to Consignment")})
@RequestMapping("/consignment")
@RestController
public class ConsignmentIntegrationController {

    @Autowired
    IntegrationService integrationService;

    @Autowired
    ConsignmentTrackingService consignmentTrackingService;

    //------------------- To fetch shipment tracking for client-----------------------------------------------------------------------
    @ApiOperation(response = ConsignmentTracking.class, value = "Get a ConsignmentTracking") // label for swagger
    @GetMapping("/{referenceNumber}/shipment")
    public ResponseEntity<?> getConsignmentTracking(@PathVariable String referenceNumber) {
        ConsignmentTracking dbConsignmentTracking = consignmentTrackingService.getConsignmentTracking(referenceNumber);
        return new ResponseEntity<>(dbConsignmentTracking, HttpStatus.OK);
    }

    //--------------------------------------Shipping (AWB) label-----------------------------------------------------------------------
    @ApiOperation(response = Optional.class, value = "Get a ClientLevel") // label for swagger
    @GetMapping("/{referenceNumber}/shippingLabel")
    public ResponseEntity<?> getShippingLabel(@PathVariable String referenceNumber) {
        byte[] shippingLabelArr = integrationService.getShippingLabel(referenceNumber);
        return new ResponseEntity<>(shippingLabelArr, HttpStatus.OK);
    }
    
    //--------------------------------------Shipping (AWB) label-----------------------------------------------------------------------
    @ApiOperation(response = Optional.class, value = "Get a ClientLevel") // label for swagger
    @GetMapping("/{wayBillNumber}/shippingLabel/v2")
    public ResponseEntity<?> getShippingLabelV2(@PathVariable String wayBillNumber) {
    	String referenceNumber = integrationService.getConsigmentByWayBillNumber(wayBillNumber);
        byte[] shippingLabelArr = integrationService.getShippingLabel(referenceNumber);
        return new ResponseEntity<>(shippingLabelArr, HttpStatus.OK);
    }
    
    //--------------------------------------Shipping (AWB) label-----------------------------------------------------------------------
    @ApiOperation(response = Optional.class, value = "Get a ClientLevel") // label for swagger
    @GetMapping("/{customerReferenceNumber}/shippingLabel/v3")
    public ResponseEntity<?> getShippingLabelV3(@PathVariable String customerReferenceNumber) {
    	String referenceNumber = integrationService.getConsigmentByWayBillNumber(customerReferenceNumber);
        byte[] shippingLabelArr = integrationService.getShippingLabel(referenceNumber);
        return new ResponseEntity<>(shippingLabelArr, HttpStatus.OK);
    }
    
    @ApiOperation(response = Optional.class, value = "Get a CustomerLevel") // label for swagger
    @GetMapping("/{referenceNumber}/flow/shippingLabel")
    public ResponseEntity<?> getFlowShippingLabel(@PathVariable String referenceNumber) {
        byte[] shippingLabelArr = integrationService.getFlowShippingLabel(referenceNumber);
        return new ResponseEntity<>(shippingLabelArr, HttpStatus.OK);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //-------------------------------------Cancel-API------------------------------------------------------------------------------
    // POST /api/client/integration/consignment/cancellation
    @ApiOperation(response = ConsignmentTracking.class, value = "CancelShipment") // label for swagger
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelShipment(@RequestBody CancelShipmentRequest cancelShipmentRequest) {
        CancelResponse cancelShipmentResponse = consignmentTrackingService.cancelShipment(cancelShipmentRequest);
        return new ResponseEntity<>(cancelShipmentResponse, HttpStatus.OK);
    }
    
    //-------------------------------------InventoryScan-API-----------------------------------------------------------------------
    @ApiOperation(response = ConsignmentTracking.class, value = "Get a InventoryScan API") // label for swagger
    @PostMapping("/inventoryScan")
    public ResponseEntity<?> inventoryScan (@RequestBody InventoryScanRequest inventoryScanRequest) {
        List<ConsignmentWebhookEntity> webhooks = integrationService.scanInventory(inventoryScanRequest);
        return new ResponseEntity<>(webhooks, HttpStatus.OK);
    }
    
    //-------------------------------------Rescheduled-Dates-API-----------------------------------------------------------------------
    @ApiOperation(response = Optional.class, value = "Get a Rescheduled-Dates API") // label for swagger
    @PostMapping("/rescheduled-dates")
    public ResponseEntity<?> rescheduledDates (@RequestBody RescheduledDatesRequest rescheduledDatesRequest) throws Exception {
        RescheduledDatesResponse dates = integrationService.getRescheduledDates(rescheduledDatesRequest);
        return new ResponseEntity<>(dates, HttpStatus.OK);
    }
    
    //-------------------------------------Address-Update-API-----------------------------------------------------------------------
    @ApiOperation(response = Optional.class, value = "Address Update API") // label for swagger
    @PostMapping("/address")
    public ResponseEntity<?> updateAddress (@RequestBody AddressUpdateRequest addressUpdateRequest) throws ParseException {
        AddressUpdateResponse response = integrationService.addressUpdate(addressUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    //-------------------------------------Willingness-API-----------------------------------------------------------------------
    @ApiOperation(response = Optional.class, value = "Willingness API") // label for swagger
    @PostMapping("/willingness")
    public ResponseEntity<?> willingness (@RequestBody Willingness willingness) throws ParseException {
        WillingnessResponse response = integrationService.willingnessUpdate(willingness);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------------Webhook Endpoint-----------------------------------------------------------------------
    @ApiOperation(response = ConsignmentWebhook.class, value = "Post Consignment Webhook") // label for swagger
    @PostMapping("/webhook")
    public ResponseEntity<?> listenWebhook(@RequestBody ConsignmentWebhook consignmentWebhook) throws Exception {
        JNTResponse webhookResponse = integrationService.createConsignmentWebhook(consignmentWebhook);
        return new ResponseEntity<>(webhookResponse, HttpStatus.OK);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //------------------------------------- Flow Logistics-----------------------------------------------------------------------------
    @ApiOperation(response = ConsignmentWebhook.class, value = "Post Consignment Webhook") // label for swagger
    @PostMapping("/flow/webhook")
    public ResponseEntity<?> listenFlowWebhook(@RequestBody ConsignmentWebhook consignmentWebhook) throws Exception {
        integrationService.createConsignmentWebhookForFlowLog(consignmentWebhook);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //------------------------------------- Emirates Post-----------------------------------------------------------------------------
    @ApiOperation(response = ConsignmentWebhook.class, value = "Post Consignment Webhook") // label for swagger
    @PostMapping("/ep/webhook")
    public ResponseEntity<?> listenEPWebhook(@RequestBody ConsignmentWebhook consignmentWebhook) throws Exception {
        integrationService.createEmiratesPostConsignmentWebhook(consignmentWebhook);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
//    //-------------------------------------Cancel-API------------------------------------------------------------------------------
//    // POST /api/client/integration/consignment/cancel
//    @ApiOperation(response = ConsignmentTracking.class, value = "CancelShipment") // label for swagger
//    @PostMapping("/flow/cancel")
//    public ResponseEntity<?> flowCancelShipment(@RequestBody FlowCancelShipmentRequest flowCancelShipmentRequest) {
//        CancelResponse cancelShipmentResponse = consignmentTrackingService.flowCancelShipment(flowCancelShipmentRequest);
//        return new ResponseEntity<>(cancelShipmentResponse, HttpStatus.OK);
//    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //--------------------------------------JNT Request Endpoint-----------------------------------------------------------------------
    @ApiOperation(response = JNTOrderCreateRequest.class, value = "Get JNT Request") // label for swagger
    @GetMapping("/jnt")
    public ResponseEntity<?> postJNTRequest(@RequestParam String referenceNumber) throws Exception {
        JNTResponse response = integrationService.postJNTRequest(referenceNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = JNTWebhookRequest.class, value = "Listen JNTWebhook") // label for swagger
    @PostMapping("/jnt/webhook")
    public ResponseEntity<?> listenJNTWebhook(@RequestBody JNTWebhookRequest jntWebhookRequest) throws Exception {
        JNTWebhookEntity webhookResponse = integrationService.createJNTWebhook(jntWebhookRequest);
        return new ResponseEntity<>(webhookResponse, HttpStatus.OK);
    }

    @ApiOperation(response = JNTWebhookRequest.class, value = "Print Label") // label for swagger
    @GetMapping("/jnt/{billCode}/printLabel")
    public ResponseEntity<?> printLabel(@PathVariable String billCode) throws Exception {
        JNTPrintLabelResponse printLabelResponse = integrationService.printLabel(billCode);
        return new ResponseEntity<>(printLabelResponse, HttpStatus.OK);
    }

    @ApiOperation(response = JNTWebhookRequest.class, value = "Print Label") // label for swagger
    @GetMapping("/jnt/{billCode}/pdf/printLabel")
    public ResponseEntity<?> pdfPrintLabel(@PathVariable String billCode) throws Exception {
        byte[] printLabelResponse = integrationService.pdfPrintLabel(billCode);
        return new ResponseEntity<>(printLabelResponse, HttpStatus.OK);
    }

    @ApiOperation(response = JNTWebhookRequest.class, value = "Update Shipsy Order Event") // label for swagger
    @PostMapping("/jnt/{referenceNumber}/eventUpdate")
    public ResponseEntity<?> listenJNTWebhook(@RequestBody OrderStatusUpdate orderStatusUpdate, @RequestParam String event) throws Exception {
        OrderStatusUpdateResponse response = integrationService.updateOrderInShipsy(orderStatusUpdate, event);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------
    //---------AJX-Webhook-------------------------------------------------------------------------------------------------------------------
    
    @ApiOperation(response = JNTWebhookRequest.class, value = "Listen AJEX Webhook") // label for swagger
    @PostMapping("/ajx/webhook")
    public ResponseEntity<?> listenAJXWebhook(@RequestBody JNTWebhookRequest jntWebhookRequest) throws Exception {
        JNTWebhookEntity webhookResponse = integrationService.createJNTWebhook(jntWebhookRequest);
        return new ResponseEntity<>(webhookResponse, HttpStatus.OK);
    }

    //--------------------------------------QatarPost-Request-Endpoint-----------------------------------------------------------------------
    @ApiOperation(response = QPOrderCreateResponse.class, value = "Post QPOrderCreateRequest") // label for swagger
    @GetMapping("/qp")
    public ResponseEntity<?> postQPOrderRequest(@RequestParam String referenceNumber) throws Exception {
        List<QPOrderCreateResponse> response = integrationService.postQPRequest(referenceNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ApiOperation(response = JNTWebhookRequest.class, value = "Post QPWebhook")                // label for swagger
//    @PostMapping("/qp/webhook")
//    public ResponseEntity<?> listenQPWebhook(@RequestBody QPTrackingRequest qpTrackingRequest) throws Exception {
//        QPTrackingResponse webhookResponse = integrationService.listenQPWebhook(qpTrackingRequest);
//        return new ResponseEntity<>(webhookResponse, HttpStatus.OK);
//    }

    //--------------------------------------DashBoard Count-----------------------------------------------------------------------

//	@ApiOperation(response = Optional.class, value = "Get JNT Dashboard Count") // label for swagger
//	@PostMapping("/dashboard/getJNTcount")
//	public ResponseEntity<?> getJNTDashboardCount(@RequestBody CountInput countInput) throws Exception {
//		Long dashboard = integrationService.getJNTCount(countInput);
//		return new ResponseEntity<>(dashboard, HttpStatus.OK);
//	}
//
//	@ApiOperation(response = Optional.class, value = "Get Boutiqaat Dashboard Count") // label for swagger
//	@PostMapping("/dashboard/getBoutiqaatCount")
//	public ResponseEntity<?> getBoutiqaatDashboardCount(@RequestBody CountInput countInput) throws Exception {
//		Long dashboard = integrationService.getBoutiqaatCount(countInput);
//		return new ResponseEntity<>(dashboard, HttpStatus.OK);
//	}

    //------------------------------------OLD CODE---------------------------------------------------------------
    // Commenting out - START
//    @ApiOperation(response = DashboardCountOutput.class, value = "Get Dashboard Count") // label for swagger
//    @PostMapping("/dashboard/getDashboardCount")
//    public ResponseEntity<?> getBoutiqaatDashboardCount(@RequestBody CountInput countInput) throws Exception {
//        DashboardCountOutput dashboard = integrationService.getDashboardCount(countInput);
//        return new ResponseEntity<>(dashboard, HttpStatus.OK);
//    }
    // END

    @ApiOperation(response = Consignment.class, value = "Search Consignment") // label for swagger
    @PostMapping("/findConsignment")
    public List<ConsignmentImpl> findConsignment(@RequestBody FindConsignment findConsignment)
            throws Exception {
        return integrationService.findConsignment(findConsignment);
    }

    @ApiOperation(response = ShopiniWebhook.class, value = "Search Shopini webhook") // label for swagger
    @PostMapping("/findShopiniWebhook")
    public List<IShopiniWebhook> findShopiniWebhook(@RequestBody FindShopini findShopini)
            throws Exception {
        return integrationService.findShopiniWebhook(findShopini);
    }

//    @ApiOperation(response = String.class, value = "Get JNT Failure Response Code") // label for swagger
//    @PostMapping("/jntFailureResponseCode")
//    public ResponseEntity<?> getJntFailureResponseCode(@RequestBody JNTFailureResponseInput jntFailureResponseDesc) throws Exception {
//        String jNTFailureResponseCode = integrationService.getJNTFailureResponseCode(jntFailureResponseDesc);
//        return new ResponseEntity<>(jNTFailureResponseCode, HttpStatus.OK);
//    }
}