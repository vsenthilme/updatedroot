package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.consignmentstatus.ConsignmentStatus;
import com.courier.overc360.api.midmile.primary.model.pickup.*;
import com.courier.overc360.api.midmile.replica.model.delivery.DeliveryStatusCount;
import com.courier.overc360.api.midmile.replica.model.delivery.StatusCountInput;
import com.courier.overc360.api.midmile.replica.model.pickup.*;
import com.courier.overc360.api.midmile.service.PickupReportService;
import com.courier.overc360.api.midmile.service.PickupService;
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
@RestController
@Api(tags = {"Pickup"}, value = "Pickup Operations Related to Controller")
@SwaggerDefinition(tags = {@Tag(name = "Pickup", description = "Operations related to Pickup")})
@RequestMapping("/pickup")
public class PickupController {

    @Autowired
    PickupService pickupService;

    @Autowired
    PickupReportService pickupReportService;


    /*=================================================Primary=======================================================*/
    // Create Pickup
    @ApiOperation(response = PickupEntity.class, value = "Create Pickup")
    @PostMapping("")
    public ResponseEntity<?> postPickup(@Valid @RequestBody List<AddPickup> addPickups,
                                                  @RequestParam String loginUserID) {
        List<PickupEntity> pickup = pickupService.createPickupList(addPickups, loginUserID);
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    // Update Pickup
    @ApiOperation(response = PickupEntity.class, value = "Update Pickup") // label for Swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchPickup(@Valid @RequestBody List<AddPickup> updatePickupList,
                                                   @RequestParam String loginUserID)
            throws InvocationTargetException, IllegalAccessException {
        List<PickupEntity> pickup = pickupService.updatePickup(updatePickupList, loginUserID);
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    // Delete Pickup
    @ApiOperation(response = PickupEntity.class, value = "Delete Pickup") // label for Swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deletePickup(@Valid @RequestBody List<PickupDeleteInput> bondedManifestDeleteInputs,
                                                    @RequestParam String loginUserID) {
        pickupService.deletePickup(bondedManifestDeleteInputs, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update Pickup Status
    @ApiOperation(response = PickupEntity.class, value = "Update Pickup Status")
    @PatchMapping("/update/pickupStatus/mobileApp")
    public ResponseEntity<?> updatePickupStatus(@Valid @RequestBody List<UpdatePickupStatus> updatePickupStatus, @RequestParam String loginUserID){
        List<PickupEntity> updateStatus = pickupService.updatePickupStatus(updatePickupStatus,loginUserID);
        return new ResponseEntity<>(updateStatus,HttpStatus.OK);
    }

    // Pickup Update Mobile By Piece
    @ApiOperation(response = PickupEntity.class, value = "Pickup Update Mobile By Piece")
    @PatchMapping("/update/pickupByPiece")
    public ResponseEntity<?> updatePickupByPiece(@Valid @RequestBody List<PickupUpdateByPiece> pickupUpdateByPieces, @RequestParam String loginUserID){
        List<PickupEntity> updateStatus = pickupService.updatePickupByPieceId(pickupUpdateByPieces,loginUserID);
        return new ResponseEntity<>(updateStatus,HttpStatus.OK);
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/
    // Get All Pickup Details
    @ApiOperation(response = ReplicaPickupEntity.class, value = "Get all Unconsolidation Details")
    @GetMapping("")
    public ResponseEntity<?> getAllPickupDetails() {
        List<ReplicaPickupEntity> pickup = pickupService.getAllPickup();
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    // Get Pickup
    @ApiOperation(response = ReplicaPickupEntity.class, value = "Get Pickup")
    @GetMapping("/get")
    public ResponseEntity<?> getPickup(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String partnerId,
                                                @RequestParam String houseAirwayBill, @RequestParam String pickupId) {
        ReplicaPickupEntity pickup = pickupService.getPickupReplica(
                languageId, companyId, partnerId, houseAirwayBill, pickupId);
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    // Find Pickup
    @ApiOperation(response = ReplicaPickupEntity.class, value = "Find Pickup") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findPickup(@Valid @RequestBody FindPickup findPickup) throws Exception {
        List<ReplicaPickupEntity> pickupEntityList = pickupService.findPickup(findPickup);
        return new ResponseEntity<>(pickupEntityList, HttpStatus.OK);
    }

    // Find Pickup Assigned List
    @ApiOperation(response = FindPickupAssigned.class, value = "Find Pickup Assigned List")
    @PostMapping("/find/pickupAssigned")
    public ResponseEntity<?> findPickupAssigned(@Valid @RequestBody PickupMobileAppReq pickupMobileAppReq){
        List<FindPickupAssigned> pickupAssignedList = pickupService.findPickupAssignedList(pickupMobileAppReq);
        return new ResponseEntity<>(pickupAssignedList,HttpStatus.OK);
    }

    // Find Pickup Accepted Task List
    @ApiOperation(response = FindPickupAssignedTask.class, value = "Find Pickup Assigned Task List")
    @PostMapping("/find/pickupAssigned/taskMobileApp")
    public ResponseEntity<?> findPickupAcceptedTask(@Valid @RequestBody PickupMobileAppReq pickupMobileAppReq){
        List<FindPickupAssignedTask> pickupAcceptedList = pickupService.findPickupAcceptedList(pickupMobileAppReq);
        return new ResponseEntity<>(pickupAcceptedList,HttpStatus.OK);
    }

    // Find PickerAssignment
    @ApiOperation(response = PickerAssignment.class, value = "Get PickerAssignment")
    @GetMapping("/getPicker")
    public ResponseEntity<?> getPickerAssignment() {
        List<PickerAssignment> pickerAssignments = pickupReportService.findPickerAssignment();
        return new ResponseEntity<>(pickerAssignments, HttpStatus.OK);
    }

    // Consignment to Pickup Create
    @ApiOperation(response = ConsignmentEntity.class, value = "Create PickUp From Consignment")
    @PostMapping("/consignment")
    public ResponseEntity<?> createPickUpFromConsignment(@Valid @RequestBody List<ConsignmentEntity> consignmentEntities,
                                                                @RequestParam String loginUserID) throws IOException,
            InvocationTargetException, IllegalAccessException, CsvException, ExecutionException, InterruptedException {
        List<PickupEntity> dbPickup = pickupService.createPickupConsignment(consignmentEntities, loginUserID);
        return new ResponseEntity<>(dbPickup, HttpStatus.OK);
    }

    // Consolidation Create Pickup
    @ApiOperation(response = PickupEntity.class, value = "Consolidation Create Pickup")
    @PostMapping("/consolidation")
    public ResponseEntity<?> createConsolidationApi(@Valid @RequestBody FindConsolidation findConsolidation,
                                                         @RequestParam String loginUserID) throws IOException,
            InvocationTargetException, IllegalAccessException, CsvException, ExecutionException, InterruptedException {
        List<PickupEntity> dbPickup = pickupService.createConsolidationPickup(findConsolidation, loginUserID);
        return new ResponseEntity<>(dbPickup, HttpStatus.OK);
    }

    //Pickup & Assign consignment update
    @ApiOperation(response = ConsignmentStatus.class, value = "Pickup & Assign consignment update")
    @PostMapping("/assign/consignmentUpdate")
    public ResponseEntity<?> consignmentUpdate(@Valid @RequestBody List<AddConsignmentStatus> addConsignmentStatuses,@RequestParam String loginUserID) throws Exception {
        List<ConsignmentStatus> consignmentStatusList = pickupService.addConsignmentStatus(addConsignmentStatuses,loginUserID);
        return new ResponseEntity<>(consignmentStatusList, HttpStatus.OK);
    }

    // Get Delivery Status Count
    @ApiOperation(response = DeliveryStatusCount.class, value = "Delivery Status Count")
    @PostMapping("/count")
    public ResponseEntity<?>getStatusCount(@Valid @RequestBody StatusCountInput input) throws ParseException {
        List<PickupStatusCount> response = pickupService.statusPickupCount(input);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get Customer pickup Status Count
    @ApiOperation(response = CustomerPickupStatusCount.class, value = "Customer Pickup Status Count")
    @PostMapping("/customerPickup/count")
    public ResponseEntity<?>getStatusCustomerStatusCount(@Valid @RequestBody StatusCountInput input) throws ParseException {
        List<CustomerPickupStatusCount> response = pickupService.statusCustomerPickupCount(input);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
