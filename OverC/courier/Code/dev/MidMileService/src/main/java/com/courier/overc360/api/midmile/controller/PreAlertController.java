package com.courier.overc360.api.midmile.controller;


import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlertDeleteInput;
import com.courier.overc360.api.midmile.primary.model.prealert.UpdatePreAlert;
import com.courier.overc360.api.midmile.replica.model.prealert.*;
import com.courier.overc360.api.midmile.service.PreAlertService;
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
import java.util.concurrent.ExecutionException;

@Slf4j
@Validated
@Api(tags = {"PreAlert"}, value = "PreAlert Operations related to PreAlertController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "PreAlert", description = "Operations related to PreAlert")})
@RequestMapping("/prealert")
@RestController
public class PreAlertController {

    @Autowired
    PreAlertService preAlertService;

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    // PreAlert Create
    @ApiOperation(response = PreAlert.class, value = "PreAlert Create")
    @PostMapping("/post/list")
    public ResponseEntity<?> createPreAlert(@Valid @RequestBody List<PreAlert> preAlert, @RequestParam String loginUserID) {
        List<PreAlert> dbPreAlert = preAlertService.createPreAlertService(preAlert, loginUserID);
        return new ResponseEntity<>(dbPreAlert, HttpStatus.OK);
    }

    // Update PreAlert
    @ApiOperation(response = PreAlert.class, value = "Update PreAlert") // label for Swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchBondedManifest(@Valid @RequestBody List<UpdatePreAlert> updatePreAlertList,
                                                 @RequestParam String loginUserID)
            throws InvocationTargetException, IllegalAccessException, IOException, CsvException {
        List<PreAlert> preAlert = preAlertService.updatePreAlert(updatePreAlertList, loginUserID);
        return new ResponseEntity<>(preAlert, HttpStatus.OK);
    }

    // Delete PreAlert
    @ApiOperation(response = PreAlert.class, value = "Delete PreAlert") // label for Swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deletePreAlert(@Valid @RequestBody List<PreAlertDeleteInput> preAlertDeleteInputs,
                                            @RequestParam String loginUserID) throws IOException, CsvException {
        preAlertService.deletePreAlert(preAlertDeleteInputs, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    // Get All PreAlert Details
    @ApiOperation(response = ReplicaPreAlert.class, value = "Get all PreAlert Details")
    // label for swagger
    @GetMapping(" ")
    public ResponseEntity<?> getAllReplicaPreAlert() {
        List<ReplicaPreAlert> preAlert = preAlertService.getAllPreAlert();
        return new ResponseEntity<>(preAlert, HttpStatus.OK);
    }

    // Get PreAlert
    @ApiOperation(response = ReplicaPreAlert.class, value = "Get a PreAlert")
    @GetMapping("/{partnerId}")
    public ResponseEntity<?> getPreAlert(@RequestParam String companyId, @RequestParam String languageId, @PathVariable String partnerId,
                                         @RequestParam String partnerMasterAirwayBill, @RequestParam String partnerHouseAirwayBill) {
        ReplicaPreAlert preAlert = preAlertService.getPreAlertReplica(
                languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill);
        return new ResponseEntity<>(preAlert, HttpStatus.OK);
    }

    // Find PreAlert
    @ApiOperation(response = ReplicaPreAlertProjection.class, value = "Find PreAlert")
    @PostMapping("/findPrealert")
    public ResponseEntity<?> postPreAlert(@Valid @RequestBody FindPreAlert findPreAlert) throws ExecutionException, InterruptedException {
        List<ReplicaPreAlertProjection> dbPreAlert = preAlertService.findPreAlert(findPreAlert);
        return new ResponseEntity<>(dbPreAlert, HttpStatus.OK);
    }

    // Find PreAlert New
    @ApiOperation(response = FindPreAlertRes.class, value = "Find New PreAlert")
    @PostMapping("/findPrealert/new")
    public ResponseEntity<?> findPreAlert(@Valid @RequestBody FindPreAlert findPreAlert) throws ExecutionException, InterruptedException {
        List<PreAlertResponse> dbPreAlert = preAlertService.findPreAlertNew(findPreAlert);
        return new ResponseEntity<>(dbPreAlert, HttpStatus.OK);
    }



}
