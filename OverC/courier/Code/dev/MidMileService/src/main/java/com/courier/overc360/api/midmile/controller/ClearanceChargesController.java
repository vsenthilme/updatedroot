package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.clearancecharges.AddClearanceCharges;
import com.courier.overc360.api.midmile.primary.model.clearancecharges.ClearanceCharges;
import com.courier.overc360.api.midmile.replica.model.clearancecharges.FindClearanceCharges;
import com.courier.overc360.api.midmile.replica.model.clearancecharges.ReplicaClearanceCharges;
import com.courier.overc360.api.midmile.service.ClearanceChargesService;
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
@Api(tags = {"ClearanceCharges"}, value = "ClearanceCharges Operations related to ClearanceChargesController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ClearanceCharges", description = "Operations related to ClearanceCharges")})
@RequestMapping("/clearanceCharges")
@RestController
public class ClearanceChargesController {

    @Autowired
    private ClearanceChargesService clearanceChargesService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = ClearanceCharges.class, value = "Create New ClearanceCharges")
    @PostMapping("")
    public ResponseEntity<?> createClearanceCharges(@Valid @RequestBody List<AddClearanceCharges> addClearanceCharges, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<ClearanceCharges> newClearanceCharges = clearanceChargesService.createClearanceCharges(addClearanceCharges,loginUserID);
        return new ResponseEntity<>(newClearanceCharges, HttpStatus.OK);
    }

    ///Update
    @ApiOperation(response = ClearanceCharges.class, value = "Update ClearanceCharges")
    @PatchMapping("/update/list")
    public ResponseEntity<?> updateClearanceCharge(@RequestParam String loginUserID, @Valid @RequestBody List<ClearanceCharges> updateClearanceCharges)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<ClearanceCharges> updatedClearanceCharges = clearanceChargesService.updateClearanceCharges(updateClearanceCharges, loginUserID);
        return new ResponseEntity<>(updatedClearanceCharges, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = ClearanceCharges.class, value = "Delete ClearanceCharges")
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteClearanceCharges(@Valid @RequestBody List<ClearanceCharges> clearanceChargesDelete, @RequestParam String loginUserID) {
        clearanceChargesService.deleteClearanceCharges(clearanceChargesDelete,loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ClearanceCharges Details
    @ApiOperation(response = ReplicaClearanceCharges.class, value = "Get all ClearanceCharges Details")
    @GetMapping("")
    public ResponseEntity<?> getAllClearanceChargesDetails() {
        List<ReplicaClearanceCharges> clearanceChargesList = clearanceChargesService.getAll();
        return new ResponseEntity<>(clearanceChargesList, HttpStatus.OK);
    }
    //Get ClearanceCharges
    @ApiOperation(response = ReplicaClearanceCharges.class, value = "Get a ClearanceCharges")
    @GetMapping("/{clearanceChargesId}")
    public ResponseEntity<?> getClearanceCharges(@PathVariable Long clearanceChargesId) {
        ReplicaClearanceCharges dbClearanceCharges = clearanceChargesService.getReplicaClearanceCharges(clearanceChargesId);
        return new ResponseEntity<>(dbClearanceCharges, HttpStatus.OK);
    }

    //Find ClearanceCharges
    @ApiOperation(response = ReplicaClearanceCharges.class, value = "Find ClearanceCharges")
    @PostMapping("/find")
    public ResponseEntity<?> findClearanceCharges(@Valid @RequestBody FindClearanceCharges findClearanceCharges) throws Exception {
        List<ReplicaClearanceCharges> clearanceChargesList = clearanceChargesService.findClearanceCharges(findClearanceCharges);
        return new ResponseEntity<>(clearanceChargesList, HttpStatus.OK);
    }
}
