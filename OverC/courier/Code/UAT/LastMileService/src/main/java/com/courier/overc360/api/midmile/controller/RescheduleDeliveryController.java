package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.drs.AddDrs;
import com.courier.overc360.api.midmile.primary.model.drs.Drs;
import com.courier.overc360.api.midmile.primary.model.drs.UpdateDrs;
import com.courier.overc360.api.midmile.primary.model.rescheduledelivery.AddRescheduleDelivery;
import com.courier.overc360.api.midmile.primary.model.rescheduledelivery.RescheduleDelivery;
import com.courier.overc360.api.midmile.primary.model.rescheduledelivery.UpdateRescheduleDelivery;
import com.courier.overc360.api.midmile.replica.model.drs.FindDrs;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import com.courier.overc360.api.midmile.replica.model.rescheduledelivery.FindRescheduleDelivery;
import com.courier.overc360.api.midmile.replica.model.rescheduledelivery.ReplicaRescheduleDelivery;
import com.courier.overc360.api.midmile.service.RescheduleDeliveryService;
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
@Api(tags = {"RescheduleDelivery"}, value = "RescheduleDelivery Operations related to RescheduleDelivery Controller") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "RescheduleDelivery", description = "Operations related to RescheduleDelivery")})
@RequestMapping("/rescheduledelivery")
@RestController
public class RescheduleDeliveryController {

    @Autowired
    RescheduleDeliveryService rescheduleDeliveryService;

    /*--------------------------------------PRIMARY----------------------------------------*/

    // Create RescheduleDelivery
    @ApiOperation(response = RescheduleDelivery.class, value = "Create RescheduleDelivery") // label for swagger
    @PostMapping("/create")
    public ResponseEntity<?> postRescheduleDelivery(@Valid @RequestBody AddRescheduleDelivery newRescheduleDelivery, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        RescheduleDelivery createdRescheduleDelivery = rescheduleDeliveryService.createRescheduleDelivery(newRescheduleDelivery, loginUserID);
        return new ResponseEntity<>(createdRescheduleDelivery, HttpStatus.OK);
    }

    // Update RescheduleDelivery
    @ApiOperation(response = RescheduleDelivery.class, value = "Update RescheduleDelivery") // label for swagger
    @PatchMapping("/update")
    public ResponseEntity<?> patchRescheduleDelivery(@RequestParam String loginUserID, @RequestBody UpdateRescheduleDelivery updateRescheduleDelivery)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        RescheduleDelivery updatedRescheduleDelivery = rescheduleDeliveryService.updateRescheduleDelivery(updateRescheduleDelivery, loginUserID);
        return new ResponseEntity<>(updatedRescheduleDelivery, HttpStatus.OK);
    }

    // Delete RescheduleDelivery
    @ApiOperation(response = RescheduleDelivery.class, value = "Delete RescheduleDelivery") // label for swagger
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRescheduleDelivery(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String deliveryId, @RequestParam String loginUserID) {
        rescheduleDeliveryService.deleteRescheduleDelivery(languageId, companyId, deliveryId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All RescheduleDelivery Details
    @ApiOperation(response = ReplicaRescheduleDelivery.class, value = "Get all ReplicaRescheduleDelivery details") // label for swagger
    @GetMapping("/getall")
    public ResponseEntity<?> getAllRescheduleDelivery() {
        List<ReplicaRescheduleDelivery> replicaRescheduleDeliveryList = rescheduleDeliveryService.getAllRescheduleDelivery();
        return new ResponseEntity<>(replicaRescheduleDeliveryList, HttpStatus.OK);
    }

    // Get RescheduleDelivery
    @ApiOperation(response = ReplicaRescheduleDelivery.class, value = "Get a ReplicaRescheduleDelivery") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getRescheduleDelivery(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String deliveryId) {

        ReplicaRescheduleDelivery replicaRescheduleDelivery = rescheduleDeliveryService.getReplicaRescheduleDelivery(languageId, companyId, deliveryId);
        return new ResponseEntity<>(replicaRescheduleDelivery, HttpStatus.OK);
    }

    // Find RescheduleDelivery
    @ApiOperation(response = ReplicaRescheduleDelivery.class, value = "Find ReplicaRescheduleDelivery") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findRescheduleDelivery(@RequestBody FindRescheduleDelivery findRescheduleDelivery) throws Exception {
        List<ReplicaRescheduleDelivery> createdRescheduleDelivery = rescheduleDeliveryService.findRescheduleDelivery(findRescheduleDelivery);
        return new ResponseEntity<>(createdRescheduleDelivery, HttpStatus.OK);
    }
}
