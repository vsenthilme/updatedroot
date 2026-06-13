package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.reasonslistdelivery.AddReasonsListDelivery;
import com.courier.overc360.api.idmaster.primary.model.reasonslistdelivery.ReasonsListDelivery;
import com.courier.overc360.api.idmaster.primary.model.reasonslistdelivery.UpdateReasonsListDelivery;
import com.courier.overc360.api.idmaster.replica.model.reasonslistdelivery.FindReasonsListDelivery;
import com.courier.overc360.api.idmaster.replica.model.reasonslistdelivery.ReplicaReasonsListDelivery;
import com.courier.overc360.api.idmaster.service.ReasonsListDeliveryService;
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
@Api(tags = {"ReasonsListDelivery"}, value = "ReasonsListDelivery  Operations related to ReasonsListDeliveryController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ReasonsListDelivery", description = "Operations related to ReasonsListDelivery")})
@RequestMapping("/reasonsListDelivery")
@RestController
public class ReasonsListDeliveryController {
    @Autowired
    ReasonsListDeliveryService reasonsListDeliveryService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/


    // Create ReasonsListDelivery
    @ApiOperation(response = ReasonsListDelivery.class, value = "Create ReasonsListDelivery") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postReasonsListDelivery(@Valid @RequestBody AddReasonsListDelivery addReasonsListDelivery, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ReasonsListDelivery reasonsListDelivery = reasonsListDeliveryService.createReasonsListDelivery(addReasonsListDelivery, loginUserID);
        return new ResponseEntity<>(reasonsListDelivery, HttpStatus.OK);
    }

    // Update ReasonsListDelivery
    @ApiOperation(response = ReasonsListDelivery.class, value = "Update ReasonsListDelivery") // label for swagger
    @PatchMapping("/{reasonsId}")
    public ResponseEntity<?> patchReasonsListDelivery(@PathVariable String reasonsId, @RequestParam String companyId,
                                                      @RequestParam String languageId,
                                                      @RequestParam String loginUserID, @RequestBody UpdateReasonsListDelivery updateReasonsListDelivery)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ReasonsListDelivery updatedReasonsListDelivery =
                reasonsListDeliveryService.updateReasonsListDelivery(companyId, languageId, reasonsId, loginUserID, updateReasonsListDelivery);
        return new ResponseEntity<>(updatedReasonsListDelivery, HttpStatus.OK);
    }

    // Delete ReasonsListDelivery
    @ApiOperation(response = ReasonsListDelivery.class, value = "Delete ReasonsListDelivery") // label for swagger
    @DeleteMapping("/{reasonsId}")
    public ResponseEntity<?> deleteReasonsListDelivery(@PathVariable String reasonsId, @RequestParam String languageId,
                                                       @RequestParam String companyId,
                                                       @RequestParam String loginUserID) {
        reasonsListDeliveryService.deleteReasonsListDelivery(companyId, languageId, reasonsId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ReasonsListDelivery Details
    @ApiOperation(response = ReplicaReasonsListDelivery.class, value = "Get all ReplicaReasonsListDelivery details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllReasonsListDeliveries() {
        List<ReplicaReasonsListDelivery> reasonsListDeliveries = reasonsListDeliveryService.getAllReasonsListDeliveries();
        return new ResponseEntity<>(reasonsListDeliveries, HttpStatus.OK);
    }

    // Get ReasonsListDelivery
    @ApiOperation(response = ReplicaReasonsListDelivery.class, value = "Get a ReplicaReasonsListDelivery")
    // label for swagger
    @GetMapping("/{reasonsId}")
    public ResponseEntity<?> getReasonsListDelivery(@PathVariable String reasonsId, @RequestParam String languageId,
                                                    @RequestParam String companyId) {
        ReplicaReasonsListDelivery dbReasonsListDelivery = reasonsListDeliveryService.getReplicaReasonsListDelivery(companyId, languageId, reasonsId);
        return new ResponseEntity<>(dbReasonsListDelivery, HttpStatus.OK);
    }

    // Find ReasonsListDelivery
    @ApiOperation(response = ReplicaReasonsListDelivery.class, value = "Find ReplicaReasonsListDelivery")
    // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findReasonsListDeliveries(@Valid @RequestBody FindReasonsListDelivery findReasonsListDelivery) throws Exception {
        List<ReplicaReasonsListDelivery> reasonsListDeliveries = reasonsListDeliveryService.findReasonsListDeliveries(findReasonsListDelivery);
        return new ResponseEntity<>(reasonsListDeliveries, HttpStatus.OK);
    }


}
