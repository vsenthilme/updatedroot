package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.vehicle.AddVehicle;
import com.courier.overc360.api.idmaster.primary.model.vehicle.UpdateVehicle;
import com.courier.overc360.api.idmaster.primary.model.vehicle.Vehicle;
import com.courier.overc360.api.idmaster.replica.model.vehicle.FindVehicle;
import com.courier.overc360.api.idmaster.replica.model.vehicle.ReplicaVehicle;
import com.courier.overc360.api.idmaster.service.VehicleService;
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
@Api(tags = {"Vehicle"}, value = "Vehicle  Operations related to VehicleController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Vehicle", description = "Operations related to Vehicle")})
@RequestMapping("/vehicle")
@RestController
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/
    // Create Vehicle
    @ApiOperation(response = Vehicle.class, value = "Create Vehicle") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postVehicle(@Valid @RequestBody AddVehicle addVehicle, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Vehicle vehicle = vehicleService.createVehicle(addVehicle, loginUserID);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    // Update Vehicle
    @ApiOperation(response = Vehicle.class, value = "Update Vehicle") // label for swagger
    @PatchMapping("/{vehicleRegNumber}")
    public ResponseEntity<?> patchVehicle(@PathVariable String vehicleRegNumber, @RequestParam String companyId, @RequestParam String languageId,
                                          @RequestParam String loginUserID, @RequestBody UpdateVehicle updateVehicle)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Vehicle updatedVehicle =
                vehicleService.updateVehicle(companyId, languageId, vehicleRegNumber, loginUserID, updateVehicle);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
    }

    // Delete Vehicle
    @ApiOperation(response = Vehicle.class, value = "Delete Vehicle") // label for swagger
    @DeleteMapping("/{vehicleRegNumber}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String vehicleRegNumber, @RequestParam String languageId, @RequestParam String companyId,
                                           @RequestParam String loginUserID) {
        vehicleService.deleteVehicle(companyId, languageId, vehicleRegNumber, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/
    // Get All Vehicle Details
    @ApiOperation(response = ReplicaVehicle.class, value = "Get all ReplicaVehicle details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllVehicles() {
        List<ReplicaVehicle> vehicleList = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
    }

    // Get Vehicle
    @ApiOperation(response = ReplicaVehicle.class, value = "Get a ReplicaVehicle") // label for swagger
    @GetMapping("/{vehicleRegNumber}")
    public ResponseEntity<?> getVehicle(@PathVariable String vehicleRegNumber, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaVehicle dbVehicle = vehicleService.getReplicaVehicle(companyId, languageId, vehicleRegNumber);
        return new ResponseEntity<>(dbVehicle, HttpStatus.OK);
    }

    // Find Vehicles
    @ApiOperation(response = ReplicaVehicle.class, value = "Find ReplicaVehicle") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findVehicles(@Valid @RequestBody FindVehicle findVehicle) throws Exception {
        List<ReplicaVehicle> vehicleList = vehicleService.findVehicles(findVehicle);
        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
    }

}
