package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.consignment.AddConsignment;
import com.courier.overc360.api.midmile.primary.model.riderassignment.RiderAssignmentDeleteInput;
import com.courier.overc360.api.midmile.primary.model.riderassignment.RiderAssignmentEntity;
import com.courier.overc360.api.midmile.replica.model.riderassignment.FindRiderAssignment;
import com.courier.overc360.api.midmile.replica.model.riderassignment.ReplicaRiderAssignmentEntity;
import com.courier.overc360.api.midmile.service.RiderAssignmentService;
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
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Validated
@RestController
@Api(tags = {"RiderAssignment"}, value = "RiderAssignment Operations Related to Controller")
@SwaggerDefinition(tags = {@Tag(name = "RiderAssignment", description = "Operations related to RiderAssignment")})
@RequestMapping("/riderAssignment")
public class RiderAssignmentController {

    @Autowired
    RiderAssignmentService riderAssignmentService;


    /*=================================================Primary=======================================================*/
    // Create RiderAssignments
    @ApiOperation(response = RiderAssignmentEntity.class, value = "Create RiderAssignments")
    @PostMapping("")
    public ResponseEntity<?> postRiderAssignments(@Valid @RequestBody List<AddConsignment> consignmentList,
                                                  @RequestParam String loginUserID) {
        List<RiderAssignmentEntity> riderAssignments = riderAssignmentService.createRiderConsignmentList(consignmentList, loginUserID);
        return new ResponseEntity<>(riderAssignments, HttpStatus.OK);
    }

    // Update RiderAssignments
    @ApiOperation(response = RiderAssignmentEntity.class, value = "Update RiderAssignments") // label for Swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchRiderAssignments(@Valid @RequestBody List<RiderAssignmentEntity> updateRiderAssignmentList,
                                                   @RequestParam String loginUserID)
            throws InvocationTargetException, IllegalAccessException {
        List<RiderAssignmentEntity> riderAssignments = riderAssignmentService.updateRiderAssignment(updateRiderAssignmentList, loginUserID);
        return new ResponseEntity<>(riderAssignments, HttpStatus.OK);
    }

    // Delete RiderAssignments
    @ApiOperation(response = RiderAssignmentEntity.class, value = "Delete RiderAssignments") // label for Swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteRiderAssignments(@Valid @RequestBody List<RiderAssignmentDeleteInput> bondedManifestDeleteInputs,
                                                    @RequestParam String loginUserID) {
        riderAssignmentService.deleteRiderAssignments(bondedManifestDeleteInputs, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/
    // Get All RiderAssignment Details
    @ApiOperation(response = ReplicaRiderAssignmentEntity.class, value = "Get all Unconsolidation Details")
    @GetMapping("")
    public ResponseEntity<?> getAllRiderAssignmentDetails() {
        List<ReplicaRiderAssignmentEntity> riderAssignments = riderAssignmentService.getAllRiderAssignments();
        return new ResponseEntity<>(riderAssignments, HttpStatus.OK);
    }

    // Get RiderAssignment
    @ApiOperation(response = ReplicaRiderAssignmentEntity.class, value = "Get RiderAssignment")
    @GetMapping("/get")
    public ResponseEntity<?> getRiderAssignment(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String partnerId,
                                                @RequestParam String houseAirwayBill, @RequestParam String pickupId) {
        ReplicaRiderAssignmentEntity riderAssignment = riderAssignmentService.getRiderAssignmentReplica(
                languageId, companyId, partnerId, houseAirwayBill, pickupId);
        return new ResponseEntity<>(riderAssignment, HttpStatus.OK);
    }

    // Find RiderAssignments
    @ApiOperation(response = ReplicaRiderAssignmentEntity.class, value = "Find RiderAssignments") // label for swagger
    @PostMapping("/find")
    public List<ReplicaRiderAssignmentEntity> findRiderAssignments(@Valid @RequestBody FindRiderAssignment findRiderAssignment) throws Exception {
        return riderAssignmentService.findRiderAssignments(findRiderAssignment);
    }
//    @ApiOperation(response = ReplicaRiderAssignmentEntity.class, value = "Find RiderAssignments") // label for swagger
//    @PostMapping("/find")
//    public ResponseEntity<?> findRiderAssignments(@Valid @RequestBody FindRiderAssignment findRiderAssignment) throws Exception {
//        List<ReplicaRiderAssignmentEntity> riderAssignmentList = riderAssignmentService.findRiderAssignments(findRiderAssignment);
//        return new ResponseEntity<>(riderAssignmentList, HttpStatus.OK);
//    }


}
