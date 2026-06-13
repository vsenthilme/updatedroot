package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.reasonslistpickup.AddReasonsListPickup;
import com.courier.overc360.api.idmaster.primary.model.reasonslistpickup.ReasonsListPickup;
import com.courier.overc360.api.idmaster.primary.model.reasonslistpickup.UpdateReasonsListPickup;
import com.courier.overc360.api.idmaster.replica.model.reasonslistpickup.FindReasonsListPickup;
import com.courier.overc360.api.idmaster.replica.model.reasonslistpickup.ReplicaReasonsListPickup;
import com.courier.overc360.api.idmaster.service.ReasonsListPickupService;
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
@Api(tags = {"ReasonsListPickup"}, value = "ReasonsListPickup  Operations related to ReasonsListPickupController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ReasonsListPickup", description = "Operations related to ReasonsListPickup")})
@RequestMapping("/reasonsListPickup")
@RestController
public class ReasonsListPickupController {

    @Autowired
    ReasonsListPickupService reasonsListPickupService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/


    // Create ReasonsListPickup
    @ApiOperation(response = ReasonsListPickup.class, value = "Create ReasonsListPickup") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postReasonsListPickup(@Valid @RequestBody AddReasonsListPickup addReasonsListPickup, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ReasonsListPickup reasonsListPickup = reasonsListPickupService.createReasonsListPickup(addReasonsListPickup, loginUserID);
        return new ResponseEntity<>(reasonsListPickup, HttpStatus.OK);
    }

    // Update ReasonsListPickup
    @ApiOperation(response = ReasonsListPickup.class, value = "Update ReasonsListPickup") // label for swagger
    @PatchMapping("/{reasonsId}")
    public ResponseEntity<?> patchReasonsListPickup(@PathVariable String reasonsId, @RequestParam String companyId,
                                              @RequestParam String languageId,
                                              @RequestParam String loginUserID, @RequestBody UpdateReasonsListPickup updateReasonsListPickup)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ReasonsListPickup updatedReasonsListPickup =
                reasonsListPickupService.updateReasonsListPickup(companyId, languageId, reasonsId, loginUserID, updateReasonsListPickup);
        return new ResponseEntity<>(updatedReasonsListPickup, HttpStatus.OK);
    }

    // Delete ReasonsListPickup
    @ApiOperation(response = ReasonsListPickup.class, value = "Delete ReasonsListPickup") // label for swagger
    @DeleteMapping("/{reasonsId}")
    public ResponseEntity<?> deleteReasonsListPickup(@PathVariable String reasonsId, @RequestParam String languageId,
                                               @RequestParam String companyId,
                                               @RequestParam String loginUserID) {
        reasonsListPickupService.deleteReasonsListPickup(companyId, languageId, reasonsId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ReasonsListPickup Details
    @ApiOperation(response = ReplicaReasonsListPickup.class, value = "Get all ReplicaReasonsListPickup details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllReasonsListPickups() {
        List<ReplicaReasonsListPickup> reasonsListPickups = reasonsListPickupService.getAllReasonsListPickups();
        return new ResponseEntity<>(reasonsListPickups, HttpStatus.OK);
    }

    // Get ReasonsListPickup
    @ApiOperation(response = ReplicaReasonsListPickup.class, value = "Get a ReplicaReasonsListPickup") // label for swagger
    @GetMapping("/{reasonsId}")
    public ResponseEntity<?> getReasonsListPickup(@PathVariable String reasonsId, @RequestParam String languageId,
                                            @RequestParam String companyId) {
        ReplicaReasonsListPickup dbReasonsListPickup = reasonsListPickupService.getReplicaReasonsListPickup(companyId, languageId, reasonsId);
        return new ResponseEntity<>(dbReasonsListPickup, HttpStatus.OK);
    }

    // Find ReasonsListPickup
    @ApiOperation(response = ReplicaReasonsListPickup.class, value = "Find ReplicaReasonsListPickup") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findReasonsListPickups(@Valid @RequestBody FindReasonsListPickup findReasonsListPickup) throws Exception {
        List<ReplicaReasonsListPickup> reasonsListPickups = reasonsListPickupService.findReasonsListPickups(findReasonsListPickup);
        return new ResponseEntity<>(reasonsListPickups, HttpStatus.OK);
    }


}
