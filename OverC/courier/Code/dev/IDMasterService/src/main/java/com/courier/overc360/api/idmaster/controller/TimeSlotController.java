package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.event.AddEvent;
import com.courier.overc360.api.idmaster.primary.model.event.Event;
import com.courier.overc360.api.idmaster.primary.model.timeslot.AddTimeSlot;
import com.courier.overc360.api.idmaster.primary.model.timeslot.TimeSlot;
import com.courier.overc360.api.idmaster.replica.model.paymenttype.FindPaymentType;
import com.courier.overc360.api.idmaster.replica.model.paymenttype.ReplicaPaymentType;
import com.courier.overc360.api.idmaster.replica.model.timeslot.FindTimeSlot;
import com.courier.overc360.api.idmaster.replica.model.timeslot.ReplicaTimeSlot;
import com.courier.overc360.api.idmaster.service.EventService;
import com.courier.overc360.api.idmaster.service.TimeSlotService;
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
@Api(tags = {"TimeSlot"}, value = "TimeSlot operations related to TimeSlotController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "TimeSlot", description = "Operations related to TimeSlot")})
@RequestMapping("/timeSlot")
@RestController
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    // Create TimeSlot
    @ApiOperation(response = TimeSlot.class, value = "Create TimeSlot") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postTimeSlot(@Valid @RequestBody List<AddTimeSlot> addTimeSlot, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<TimeSlot> createTimeSlot = timeSlotService.createTimeslot(addTimeSlot, loginUserID);
        return new ResponseEntity<>(createTimeSlot, HttpStatus.OK);
    }

    // Update Timeslot List
    @ApiOperation(response = TimeSlot.class, value = "Update Timeslot List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchTimeslotList(@RequestParam String loginUserID, @RequestBody List<AddTimeSlot> updateTimeslot)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<TimeSlot> updatedTimeslot = timeSlotService.updateTimeslot(updateTimeslot, loginUserID);
        return new ResponseEntity<>(updatedTimeslot, HttpStatus.OK);
    }

    // Delete Timeslot List
    @ApiOperation(response = TimeSlot.class, value = "Delete Timeslot") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteNprList(@RequestBody List<TimeSlot> deleteTimeslotList, @RequestParam String loginUserID) {
        timeSlotService.deleteTimeslotList(deleteTimeslotList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Timeslot Details
    @ApiOperation(response = ReplicaTimeSlot.class, value = "Get all Timeslot details") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTimeslot() {
        List<ReplicaTimeSlot> replicaTimeslot = timeSlotService.getAllTimeslot();
        return new ResponseEntity<>(replicaTimeslot, HttpStatus.OK);
    }

    // Get Timeslot
    @ApiOperation(response = ReplicaTimeSlot.class, value = "Get a Timeslot") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getTimeslot(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String timeslotId) {

        ReplicaTimeSlot replicaTimeSlot = timeSlotService.getReplicaTimeslot(languageId, companyId, timeslotId);
        return new ResponseEntity<>(replicaTimeSlot, HttpStatus.OK);
    }


    // Find Timeslot
    @ApiOperation(response = ReplicaTimeSlot.class, value = "Find Timeslot") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findTimeslot(@RequestBody FindTimeSlot findTimeSlot) throws Exception {
        List<ReplicaTimeSlot> findTimeslots = timeSlotService.findTimeSlot(findTimeSlot);
        return new ResponseEntity<>(findTimeslots, HttpStatus.OK);
    }











}
