package com.courier.overc360.api.midmile.controller;


import com.courier.overc360.api.midmile.primary.model.delivery.Delivery;
import com.courier.overc360.api.midmile.primary.model.delivery.UpdateDeliveryStatus;
import com.courier.overc360.api.midmile.primary.model.pickup.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.pickup.PickerAssignment;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupEntity;
import com.courier.overc360.api.midmile.primary.model.pickup.UpdatePickupStatus;
import com.courier.overc360.api.midmile.replica.model.delivery.*;
import com.courier.overc360.api.midmile.replica.model.pickup.PickupMobileAppReq;
import com.courier.overc360.api.midmile.service.DeliveryService;
import com.courier.overc360.api.midmile.service.PickupReportService;
import com.opencsv.exceptions.CsvException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Slf4j
@Validated
@Api(tags = {"Delivery"}, value = "Delivery Operations related to DeliveryController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Delivery", description = "Operations related to Delivery")})
@RequestMapping("/delivery")
@RestController
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private PickupReportService pickupReportService;

    //=======================================================================================================================================

    //Create Delivery
    @ApiOperation(response = Delivery.class, value = "Create New Delivery")
    @PostMapping("/create/list")
    public ResponseEntity<?> createDelivery(@Valid @RequestBody List<Delivery> deliveryList, @RequestParam String  loginUserID){
        List<Delivery> newDelivery = deliveryService.createDelivery(deliveryList,loginUserID);
        return new ResponseEntity<>(newDelivery, HttpStatus.OK);
    }

    //Update Delivery
    @ApiOperation(response = Delivery.class, value = "Update Delivery")
    @PatchMapping("/update/list")
    public ResponseEntity<?> updateDelivery(@Valid @RequestBody List<Delivery> updateDelivery,@RequestParam String loginUserID){
     List<Delivery> updatedDelivery = deliveryService.updateDelivery(updateDelivery,loginUserID);
     return new ResponseEntity<>(updatedDelivery,HttpStatus.OK);
    }

    //Delete Delivery
    @ApiOperation(response = Delivery.class, value = "Delete Delivery")
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteDelivery(@Valid @RequestBody List<Delivery> deleteDelivery,@RequestParam String loginUserID){
        deliveryService.deleteDelivery(deleteDelivery,loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

      /* -------------------------Replica--------------------*/

    //Get All Delivery List
    @ApiOperation(response = ReplicaDelivery.class, value = "Get All Delivery List")
    @GetMapping("")
    public ResponseEntity<?> getAllDelivery(){
        List<ReplicaDelivery> deliveryList = deliveryService.getAllDelivery();
        return new ResponseEntity<>(deliveryList,HttpStatus.OK);
    }

    @ApiOperation(response = ReplicaDelivery.class, value = "Find Delivery")
    @PostMapping("/find")
    public ResponseEntity<?> findDelivery(@Valid @RequestBody FindDelivery findDelivery) throws ParseException {
        List<ReplicaDelivery> deliveryList = deliveryService.findDeliveryList(findDelivery);
        return new ResponseEntity<>(deliveryList,HttpStatus.OK);
    }

    // Find Delivery Assigned List
    @ApiOperation(response = FindDeliveryAssigned.class, value = "Find Delivery Assigned List")
    @PostMapping("/find/deliveryAssigned")
    public ResponseEntity<?> findDeliveryAssigned(@Valid @RequestBody DeliveryMobileAppReq deliveryMobileAppReq){
        List<FindDeliveryAssigned> deliveryAssigneds = deliveryService.findDeliveryAssignedList(deliveryMobileAppReq);
        return new ResponseEntity<>(deliveryAssigneds,HttpStatus.OK);
    }

    // Find Delivery Accepted Task List
    @ApiOperation(response = FindDeliveryAssignedTask.class, value = "Find Delivery Assigned Task List")
    @PostMapping("/find/deliveryAssigned/taskMobileApp")
    public ResponseEntity<?> findPickupAcceptedTask(@Valid @RequestBody DeliveryMobileAppReq deliveryMobileAppReq){
        List<FindDeliveryAssignedTask> deliveryAssignedTasks = deliveryService.findDeliveryAcceptedList(deliveryMobileAppReq);
        return new ResponseEntity<>(deliveryAssignedTasks,HttpStatus.OK);
    }

    // Find DeliveryAssignment
    @ApiOperation(response = PickerAssignment.class, value = "Get DeliveryAssignment")
    @GetMapping("/getDelivery")
    public ResponseEntity<?> getDeliveryAssignment() {
        List<PickerAssignment> deliveryAssignment = pickupReportService.findDeliveryAssignmentCount();
        return new ResponseEntity<>(deliveryAssignment, HttpStatus.OK);
    }

    // Update Delivery Status
    @ApiOperation(response = Delivery.class, value = "Update Delivery Status")
    @PatchMapping("/update/deliveryStatus/mobileApp")
    public ResponseEntity<?> updateDeliveryStatus(@Valid @RequestBody List<UpdateDeliveryStatus> updateDeliveryStatuses, @RequestParam String loginUserID){
        List<Delivery> updateStatus = deliveryService.updateDeliveryStatus(updateDeliveryStatuses,loginUserID);
        return new ResponseEntity<>(updateStatus,HttpStatus.OK);
    }

    // Consignment to Delivery Create
    @ApiOperation(response = ConsignmentEntity.class, value = "Create Delivery From Consignment")
    @PostMapping("/consignment")
    public ResponseEntity<?> createPickUpFromConsignment(@Valid @RequestBody List<ConsignmentEntity> consignmentEntities,
                                                         @RequestParam String loginUserID) throws IOException,
            InvocationTargetException, IllegalAccessException, CsvException, ExecutionException, InterruptedException {
        List<Delivery> dbDelivery = deliveryService.createConsignmentToDelivery(consignmentEntities, loginUserID);
        return new ResponseEntity<>(dbDelivery, HttpStatus.OK);
    }

    // DeliveryManifest MobileApp Find
    @ApiOperation(response = DeliveryManifestMobileAppRes.class, value = "Find OutScan Delivery Manifest Mobile App")
    @PostMapping("/find/outscan/delivery/manifest/mobileApp")
    public ResponseEntity<?> findDeliveryManifestMobApp(@Valid @RequestBody DeliveryManifestMobAppInput deliveryManifestMobAppInput) {
        List<DeliveryManifestMobileAppRes> deliveryManifestMobileAppRes = deliveryService.findDeliveryManifestMobApp(deliveryManifestMobAppInput);
        return new ResponseEntity<>(deliveryManifestMobileAppRes, HttpStatus.OK);
    }

    // Delivery OutScan HAWB Find
    @ApiOperation(response = FindOutScanMobileApp.class, value = "Find OutScan Delivery HAWB Mobile App")
    @PostMapping("find/outscan/delivery/hawb/mobileApp")
    public ResponseEntity<?> findDeliveryHawbMobApp(@Valid @RequestBody FindOutScanInput findOutScanInput) {
        List<FindOutScanMobileApp> deliveryHawbMobileAppRes = deliveryService.findDeliveryHawbMobApp(findOutScanInput);
        return new ResponseEntity<>(deliveryHawbMobileAppRes, HttpStatus.OK);
    }

    // Update Status Delivery, Consignment & ConsignmentStatus table
    @ApiOperation(response = FindConsignmentOutScanInput[].class, value = "Update Status Delivery, Consignment & ConsignmentStatus")
    @PatchMapping("/update/Status/mobileApp")
    public ResponseEntity<?> updateStatus(@Valid @RequestBody List<FindConsignmentOutScanInput> updateDeliveryStatuses, @RequestParam String loginUserID){
        List<FindConsignmentOutScanInput> findConsignmentOutScanInputList = deliveryService.updateDeliveryConsignmentStatus(updateDeliveryStatuses,loginUserID);
        return new ResponseEntity<>(findConsignmentOutScanInputList, HttpStatus.OK);
    }

    // Get Delivery Status Count
    @ApiOperation(response = DeliveryStatusCount.class, value = "Delivery Status Count")
    @PostMapping("/count")
    public ResponseEntity<?>getStatusCount(@Valid @RequestBody StatusCountInput input) throws ParseException {
        List<DeliveryStatusCount> response = deliveryService.statusDeliveryCount(input);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
