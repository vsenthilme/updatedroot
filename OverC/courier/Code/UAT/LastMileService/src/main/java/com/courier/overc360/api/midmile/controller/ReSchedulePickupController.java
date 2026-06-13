package com.courier.overc360.api.midmile.controller;


import com.courier.overc360.api.midmile.primary.model.npr.Npr;
import com.courier.overc360.api.midmile.primary.model.reschedulepickup.ReSchedulePickUp;
import com.courier.overc360.api.midmile.replica.model.npr.FindNpr;
import com.courier.overc360.api.midmile.replica.model.npr.ReplicaNpr;
import com.courier.overc360.api.midmile.replica.model.reschedulepickup.FindReschedulePickup;
import com.courier.overc360.api.midmile.replica.model.reschedulepickup.ReplicaReSchedulePickUp;
import com.courier.overc360.api.midmile.service.ReschedulePickupService;
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
import java.util.List;

@Slf4j
@Validated
@RestController
@Api(tags = {"Reschedule Pickup"}, value = "Reschedule Pickup Operations Related to ReSchedulePickupController")
@SwaggerDefinition(tags = {@Tag(name = "Reschedule Pickup", description = "Operations related to Reschedule Pickup")})
@RequestMapping("/reschedulePickup")
public class ReSchedulePickupController {

    @Autowired
    private ReschedulePickupService reschedulePickupService;


    // Create Reschedule Pickup List
    @ApiOperation(response = ReSchedulePickUp.class, value = "Create Reschedule Pickup List") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postReschedulePickupList(@Valid @RequestBody List<ReSchedulePickUp> reSchedulePickUps, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<ReSchedulePickUp> createdReschedulePickup = reschedulePickupService.createReschedulePickup(reSchedulePickUps, loginUserID);
        return new ResponseEntity<>(createdReschedulePickup, HttpStatus.OK);
    }

    // Update Reschedule List
    @ApiOperation(response = ReSchedulePickUp.class, value = "Update Reschedule List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchRescheduleList(@RequestParam String loginUserID, @RequestBody List<ReSchedulePickUp> updateRescheduleList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<ReSchedulePickUp> updatedReschedulePickup = reschedulePickupService.updateRescheduleList(updateRescheduleList, loginUserID);
        return new ResponseEntity<>(updatedReschedulePickup, HttpStatus.OK);
    }

    // Delete Reschedule List
    @ApiOperation(response = ReSchedulePickUp.class, value = "Delete Reschedule Pickup") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteRescheduleList(@RequestBody List<ReSchedulePickUp> deleteRescheduleList, @RequestParam String loginUserID) {
        reschedulePickupService.deleteReschedulePickupList(deleteRescheduleList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Reschedule Pickup Details
    @ApiOperation(response = ReplicaReSchedulePickUp.class, value = "Get all Reschedule Pickup details") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllReschedulePickup() {
        List<ReplicaReSchedulePickUp> reSchedulePickUps = reschedulePickupService.getAllReschedulePickup();
        return new ResponseEntity<>(reSchedulePickUps, HttpStatus.OK);
    }

    // Get Reschedule Pickup
    @ApiOperation(response = ReplicaReSchedulePickUp.class, value = "Get a Reschedule Pickup") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getReschedulePickup(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String pickupId) {

        ReplicaReSchedulePickUp replicaReSchedulePickUp = reschedulePickupService.getReplicaReschedule(languageId, companyId, pickupId);
        return new ResponseEntity<>(replicaReSchedulePickUp, HttpStatus.OK);
    }

    // Find Reschedule Pickup
    @ApiOperation(response = ReplicaReSchedulePickUp.class, value = "Find Reschedule Pickup") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findReschedulePickup(@RequestBody FindReschedulePickup findReschedulePickup) throws Exception {
        List<ReplicaReSchedulePickUp> findReschedule = reschedulePickupService.findReschedulePickup(findReschedulePickup);
        return new ResponseEntity<>(findReschedule, HttpStatus.OK);
    }








}
