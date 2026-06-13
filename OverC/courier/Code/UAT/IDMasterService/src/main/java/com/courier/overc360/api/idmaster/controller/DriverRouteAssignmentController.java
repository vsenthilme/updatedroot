package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.driverRouteAssignment.AddDriverRouteAssignment;
import com.courier.overc360.api.idmaster.primary.model.driverRouteAssignment.DriverRouteAssignment;
import com.courier.overc360.api.idmaster.primary.model.driverRouteAssignment.UpdateDriverRouteAssignment;
import com.courier.overc360.api.idmaster.replica.model.driverrouteassignment.FindDriverRouteAssignment;
import com.courier.overc360.api.idmaster.replica.model.driverrouteassignment.ReplicaDriverRouteAssignment;
import com.courier.overc360.api.idmaster.service.DriverRouteAssignmentService;
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
@Api(tags = {"DriverRouteAssignment"}, value = "DriverRouteAssignment  Operations related to DriverRouteAssignmentController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "DriverRouteAssignment", description = "Operations related to DriverRouteAssignment")})
@RequestMapping("/driverRouteAssignment")
@RestController
public class DriverRouteAssignmentController {

    @Autowired
    DriverRouteAssignmentService driverRouteAssignmentService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/
    // Create DriverRouteAssignment
    @ApiOperation(response = DriverRouteAssignment.class, value = "Create DriverRouteAssignment") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postDriverRouteAssignment(@Valid @RequestBody AddDriverRouteAssignment addDriverRouteAssignment, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        DriverRouteAssignment driverRouteAssignment = driverRouteAssignmentService.createDriverRouteAssignment(addDriverRouteAssignment, loginUserID);
        return new ResponseEntity<>(driverRouteAssignment, HttpStatus.OK);
    }

    // Update DriverRouteAssignment
    @ApiOperation(response = DriverRouteAssignment.class, value = "Update DriverRouteAssignment") // label for swagger
    @PatchMapping("/{courierId}")
    public ResponseEntity<?> patchDriverRouteAssignment(@PathVariable String courierId, @RequestParam String companyId, @RequestParam String languageId,
                                                        @RequestParam String routeId, @RequestParam String vehicleRegNumber, @RequestParam String assignedHubCode,
                                                        @RequestParam String loginUserID, @RequestBody UpdateDriverRouteAssignment updateDriverRouteAssignment)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        DriverRouteAssignment updatedDriverRouteAssignment =
                driverRouteAssignmentService.updateDriverRouteAssignment(companyId, languageId, courierId, routeId, vehicleRegNumber, assignedHubCode, loginUserID, updateDriverRouteAssignment);
        return new ResponseEntity<>(updatedDriverRouteAssignment, HttpStatus.OK);
    }

    // Delete DriverRouteAssignment
    @ApiOperation(response = DriverRouteAssignment.class, value = "Delete DriverRouteAssignment") // label for swagger
    @DeleteMapping("/{courierId}")
    public ResponseEntity<?> deleteDriverRouteAssignment(@PathVariable String courierId, @RequestParam String companyId, @RequestParam String languageId,
                                                         @RequestParam String routeId, @RequestParam String vehicleRegNumber, @RequestParam String assignedHubCode,
                                                         @RequestParam String loginUserID) {
        driverRouteAssignmentService.deleteDriverRouteAssignment(companyId, languageId, courierId, routeId, vehicleRegNumber, assignedHubCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/
    // Get All DriverRouteAssignment Details
    @ApiOperation(response = ReplicaDriverRouteAssignment.class, value = "Get all ReplicaDriverRouteAssignment details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllDriverRouteAssignments() {
        List<ReplicaDriverRouteAssignment> driverRouteAssignmentList = driverRouteAssignmentService.getAllDriverRouteAssignments();
        return new ResponseEntity<>(driverRouteAssignmentList, HttpStatus.OK);
    }

    // Get DriverRouteAssignment
    @ApiOperation(response = ReplicaDriverRouteAssignment.class, value = "Get a ReplicaDriverRouteAssignment")
    // label for swagger
    @GetMapping("/{courierId}")
    public ResponseEntity<?> getDriverRouteAssignment(@PathVariable String courierId, @RequestParam String companyId, @RequestParam String languageId,
                                                      @RequestParam String routeId, @RequestParam String vehicleRegNumber, @RequestParam String assignedHubCode) {
        ReplicaDriverRouteAssignment dbDriverRouteAssignment = driverRouteAssignmentService.getReplicaDriverRouteAssignment(companyId, languageId, courierId, routeId, vehicleRegNumber, assignedHubCode);
        return new ResponseEntity<>(dbDriverRouteAssignment, HttpStatus.OK);
    }

    // Find DriverRouteAssignments
    @ApiOperation(response = ReplicaDriverRouteAssignment.class, value = "Find ReplicaDriverRouteAssignment")
    // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findDriverRouteAssignments(@Valid @RequestBody FindDriverRouteAssignment findDriverRouteAssignment) throws Exception {
        List<ReplicaDriverRouteAssignment> driverRouteAssignmentList = driverRouteAssignmentService.findDriverRouteAssignments(findDriverRouteAssignment);
        return new ResponseEntity<>(driverRouteAssignmentList, HttpStatus.OK);
    }

}
