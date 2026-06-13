package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.billingfrequency.AddBillingFrequency;
import com.courier.overc360.api.idmaster.primary.model.billingfrequency.BillingFrequency;
import com.courier.overc360.api.idmaster.replica.model.billingfrequency.FindBillingFrequency;
import com.courier.overc360.api.idmaster.replica.model.billingfrequency.ReplicaBillingFrequency;
import com.courier.overc360.api.idmaster.service.BillingFrequencyService;
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
@Api(tags = {"BillingFrequency"}, value = "BillingFrequency operations related to BillingFrequencyController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "BillingFrequency", description = "Operations related to BillingFrequency")})
@RequestMapping("/billingFrequency")
@RestController
public class BillingFrequencyController {


    @Autowired
    private BillingFrequencyService billingFrequencyService;


    // Create BillingFrequency
    @ApiOperation(response = BillingFrequency.class, value = "Create BillingFrequency") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postBillingFrequency(@Valid @RequestBody List<AddBillingFrequency> addBillingFrequencys, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<BillingFrequency> createBillingFrequency = billingFrequencyService.createBillingFrequency(addBillingFrequencys, loginUserID);
        return new ResponseEntity<>(createBillingFrequency, HttpStatus.OK);
    }

    // Update BillingFrequency
    @ApiOperation(response = BillingFrequency.class, value = "Update BillingFrequency") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchBillingFrequency(@RequestParam String loginUserID, @RequestBody List<AddBillingFrequency> updateBillingFrequency)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<BillingFrequency> updatedBillingFrequency = billingFrequencyService.updateBillingFrequency(updateBillingFrequency, loginUserID);
        return new ResponseEntity<>(updatedBillingFrequency, HttpStatus.OK);
    }

    // Delete BillingFrequency
    @ApiOperation(response = BillingFrequency.class, value = "Delete BillingFrequency") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteBillingFrequency(@RequestBody List<BillingFrequency> deleteBillingFrequency, @RequestParam String loginUserID) {
        billingFrequencyService.deleteBillingFrequency(deleteBillingFrequency, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All BillingFrequency Details
    @ApiOperation(response = ReplicaBillingFrequency.class, value = "Get all BillingFrequency details")
    // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBillingFrequency() {
        List<ReplicaBillingFrequency> replicaBillingFrequency = billingFrequencyService.getAllBillingFrequency();
        return new ResponseEntity<>(replicaBillingFrequency, HttpStatus.OK);
    }


    // Get BillingFrequency
    @ApiOperation(response = ReplicaBillingFrequency.class, value = "Get a BillingFrequency") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getBillingFrequency(@RequestParam String languageId, @RequestParam String companyId,
                                                 @RequestParam String billingFrequencyId) {

        ReplicaBillingFrequency replicaBillingFrequency = billingFrequencyService.getReplicaBillingFrequency(languageId, companyId, billingFrequencyId);
        return new ResponseEntity<>(replicaBillingFrequency, HttpStatus.OK);
    }

    // Find BillingFrequency
    @ApiOperation(response = ReplicaBillingFrequency.class, value = "Find BillingFrequency") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findBillingFrequency(@RequestBody FindBillingFrequency findBillingFrequency) throws Exception {
        List<ReplicaBillingFrequency> findBilling = billingFrequencyService.findBillingFrequency(findBillingFrequency);
        return new ResponseEntity<>(findBilling, HttpStatus.OK);
    }


}
