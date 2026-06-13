package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.fueltracking.AddFuelTracking;
import com.courier.overc360.api.midmile.primary.model.fueltracking.FuelTracking;
import com.courier.overc360.api.midmile.primary.model.fueltracking.UpdateFuelTracking;
import com.courier.overc360.api.midmile.replica.model.fueltracking.FindFuelTracking;
import com.courier.overc360.api.midmile.replica.model.fueltracking.ReplicaFuelTracking;
import com.courier.overc360.api.midmile.service.FuelTrackingService;
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
@Api(tags = {"FuelTracking"}, value = "FuelTracking Operations related to FuelTrackingController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "FuelTracking", description = "Operations related to FuelTracking")})
@RequestMapping("/fuelTracking")
@RestController
public class FuelTrackingController {

    @Autowired
    private FuelTrackingService fuelTrackingService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = FuelTracking.class, value = "Create New FuelTracking")
    @PostMapping("")
    public ResponseEntity<?> createFuelTracking(@Valid @RequestBody AddFuelTracking addFuelTracking, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        FuelTracking newFuelTracking = fuelTrackingService.createFuelTracking(addFuelTracking, loginUserID);
        return new ResponseEntity<>(newFuelTracking, HttpStatus.OK);
    }

    ///Update
    @ApiOperation(response = FuelTracking.class, value = "Update FuelTracking")
    @PatchMapping("/{vehicleRegNumber}")
    public ResponseEntity<?> patchFuelTracking(@PathVariable String vehicleRegNumber, @RequestParam String companyId, @RequestParam String languageId,
                                               @RequestParam String loginUserID, @RequestBody UpdateFuelTracking updateFuelTracking)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        FuelTracking updatedFuelTracking = fuelTrackingService.updateFuelTracking(companyId, languageId, vehicleRegNumber, updateFuelTracking, loginUserID);
        return new ResponseEntity<>(updatedFuelTracking, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = FuelTracking.class, value = "Delete FuelTracking")
    @DeleteMapping("/{vehicleRegNumber}")
    public ResponseEntity<?> deleteFuelTracking(@PathVariable String vehicleRegNumber, @RequestParam String companyId, @RequestParam String languageId,
                                                @RequestParam String loginUserID) {
        fuelTrackingService.deleteFuelTracking(companyId, languageId, vehicleRegNumber, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All FuelTracking Details
    @ApiOperation(response = ReplicaFuelTracking.class, value = "Get all FuelTracking Details")
    @GetMapping("")
    public ResponseEntity<?> getAllFuelTrackingDetails() {
        List<ReplicaFuelTracking> fuelTrackingList = fuelTrackingService.getAll();
        return new ResponseEntity<>(fuelTrackingList, HttpStatus.OK);
    }

    //Get FuelTracking
    @ApiOperation(response = ReplicaFuelTracking.class, value = "Get a FuelTracking")
    @GetMapping("/{vehicleRegNumber}")
    public ResponseEntity<?> getFuelTracking(@PathVariable String vehicleRegNumber, @RequestParam String companyId, @RequestParam String languageId) {
        ReplicaFuelTracking dbFuelTracking = fuelTrackingService.getReplicaFuelTracking(companyId, languageId, vehicleRegNumber);
        return new ResponseEntity<>(dbFuelTracking, HttpStatus.OK);
    }

    //Find FuelTracking
    @ApiOperation(response = ReplicaFuelTracking.class, value = "Find FuelTracking")
    @PostMapping("/find")
    public ResponseEntity<?> findFuelTracking(@Valid @RequestBody FindFuelTracking findFuelTracking) throws Exception {
        List<ReplicaFuelTracking> fuelTrackingList = fuelTrackingService.findFuelTracking(findFuelTracking);
        return new ResponseEntity<>(fuelTrackingList, HttpStatus.OK);
    }
}
