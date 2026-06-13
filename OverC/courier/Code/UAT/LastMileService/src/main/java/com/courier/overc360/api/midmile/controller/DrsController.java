package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.drs.AddDrs;
import com.courier.overc360.api.midmile.primary.model.drs.Drs;
import com.courier.overc360.api.midmile.primary.model.drs.UpdateDrs;
import com.courier.overc360.api.midmile.replica.model.drs.FindDrs;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import com.courier.overc360.api.midmile.service.DrsService;
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
@Api(tags = {"Drs"}, value = "Drs Operations related to Drs Controller") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Drs", description = "Operations related to Drs")})
@RequestMapping("/drs")
@RestController
public class DrsController {

    @Autowired
    DrsService drsService;

    /*--------------------------------------PRIMARY----------------------------------------*/

    // Create Drs
    @ApiOperation(response = Drs.class, value = "Create Drs") // label for swagger
    @PostMapping("/create")
    public ResponseEntity<?> postDrs(@Valid @RequestBody AddDrs newDrs, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Drs createdDrs = drsService.createDrs(newDrs, loginUserID);
        return new ResponseEntity<>(createdDrs, HttpStatus.OK);
    }

    // Create Drs List
    @ApiOperation(response = Drs.class, value = "Create Drs List") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postDrsList(@Valid @RequestBody List<AddDrs> drsList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Drs> createdDrs = drsService.createDrsList(drsList, loginUserID);
        return new ResponseEntity<>(createdDrs, HttpStatus.OK);
    }

    // Update Drs
    @ApiOperation(response = Drs.class, value = "Update Drs") // label for swagger
    @PatchMapping("/update")
    public ResponseEntity<?> patchDrs(@RequestParam String loginUserID, @RequestBody UpdateDrs updateDrs)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Drs updatedDrs = drsService.updateDrs(updateDrs, loginUserID);
        return new ResponseEntity<>(updatedDrs, HttpStatus.OK);
    }

    // Update Drs List
    @ApiOperation(response = Drs.class, value = "Update Drs List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchDrsList(@RequestParam String loginUserID, @RequestBody List<Drs> updateDrsList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Drs> updatedDrs = drsService.updateDrsList(updateDrsList, loginUserID);
        return new ResponseEntity<>(updatedDrs, HttpStatus.OK);
    }

    // Delete Drs
    @ApiOperation(response = Drs.class, value = "Delete Drs") // label for swagger
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDrs(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String customerId
            , @RequestParam String houseAirwayBill, @RequestParam String pieceId, @RequestParam String loginUserID) {
        drsService.deleteDrs(languageId, companyId, customerId, houseAirwayBill, pieceId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete Drs List
    @ApiOperation(response = Drs.class, value = "Delete Drs") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteDrsList(@RequestBody List<Drs> deleteDrsList, @RequestParam String loginUserID) {
        drsService.deleteDrsList(deleteDrsList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Drs Details
    @ApiOperation(response = ReplicaDrs.class, value = "Get all ReplicaDrs details") // label for swagger
    @GetMapping("/getall")
    public ResponseEntity<?> getAllDrs() {
        List<ReplicaDrs> replicaDrsList = drsService.getAllDrs();
        return new ResponseEntity<>(replicaDrsList, HttpStatus.OK);
    }

    // Get Drs
    @ApiOperation(response = ReplicaDrs.class, value = "Get a ReplicaDrs") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getDrs(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String customerId, @RequestParam String houseAirwayBill,
                                    @RequestParam String pieceId) {

        ReplicaDrs replicaDrs = drsService.getReplicaDrs(languageId, companyId, customerId, houseAirwayBill, pieceId);
        return new ResponseEntity<>(replicaDrs, HttpStatus.OK);
    }

    // Find Drs
    @ApiOperation(response = ReplicaDrs.class, value = "Find ReplicaDrs") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findDrs(@RequestBody FindDrs findDrs) throws Exception {
        List<ReplicaDrs> createdDrs = drsService.findDrs(findDrs);
        return new ResponseEntity<>(createdDrs, HttpStatus.OK);
    }
}
