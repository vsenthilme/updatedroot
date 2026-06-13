package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.statusevent.AddStatusEvent;
import com.courier.overc360.api.idmaster.primary.model.statusevent.StatusEvent;
import com.courier.overc360.api.idmaster.primary.model.statusevent.UpdateStatusEvent;
import com.courier.overc360.api.idmaster.replica.model.statusevent.FindStatusEvent;
import com.courier.overc360.api.idmaster.replica.model.statusevent.ReplicaStatusEvent;
import com.courier.overc360.api.idmaster.service.StatusEventService;
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
@Api(tags = {"StatusEvent"}, value = "StatusEvent related to StatusEventController")
@SwaggerDefinition(tags = {@Tag(name = "StatusEvent", description = "Operations related to StatusEvent")})
@RequestMapping("/statusevent")
@RestController

public class StatusEventController {

    @Autowired
    private StatusEventService statusEventService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create StatusEvent
    @ApiOperation(response = StatusEvent.class, value = "Create New StatusEvent")
    @PostMapping("")
    public ResponseEntity<?> createStatusEvent(@Valid @RequestBody AddStatusEvent addStatusEvent, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        StatusEvent newStatusEvent = statusEventService.createStatusEvent(addStatusEvent, loginUserID);
        return new ResponseEntity<>(newStatusEvent, HttpStatus.OK);
    }

    //Update StatusEvent
    @ApiOperation(response = StatusEvent.class, value = "Update StatusEvent")
    @PatchMapping("/{typeId}")
    public ResponseEntity<?> patchStatusEvent(@PathVariable String typeId, @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String loginUserID, @RequestBody UpdateStatusEvent updateStatusEvent)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        StatusEvent updatedStatusEvent = statusEventService.updateStatusEvent(companyId, languageId, typeId,updateStatusEvent, loginUserID);
        return new ResponseEntity<>(updatedStatusEvent, HttpStatus.OK);
    }

    //Delete StatusEvent
    @ApiOperation(response = StatusEvent.class, value = "Delete StatusEvent")
    @DeleteMapping("/{typeId}")
    public ResponseEntity<?> deleteStatusEvent(@PathVariable String typeId,@RequestParam String companyId, @RequestParam String languageId,
                                               @RequestParam String loginUserID) {
        statusEventService.deleteStatusEvent( companyId,languageId, typeId,loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All StatusEvent Details
    @ApiOperation(response = ReplicaStatusEvent.class, value = "Get all StatusEvent Details")
    @GetMapping("")
    public ResponseEntity<?> getAllStatusEventDetails() {
        List<ReplicaStatusEvent> statusEventList = statusEventService.getAllStatusEvent();
        return new ResponseEntity<>(statusEventList, HttpStatus.OK);
    }

    //Get StatusEvent
    @ApiOperation(response = ReplicaStatusEvent.class, value = "Get a StatusEvent")
    @GetMapping("/{typeId}")
    public ResponseEntity<?> getStatusEvent(@PathVariable String typeId,@RequestParam String companyId, @RequestParam String languageId) {
        ReplicaStatusEvent dbStatusEvent = statusEventService.getReplicaStatusEvent(companyId,languageId,typeId);
        return new ResponseEntity<>(dbStatusEvent, HttpStatus.OK);
    }

    //Find StatusEvent
    @ApiOperation(response = ReplicaStatusEvent.class, value = "Find StatusEvent")
    @PostMapping("/find")
    public ResponseEntity<?> findStatusEvent(@Valid @RequestBody FindStatusEvent findStatusEvent) throws Exception {
        List<ReplicaStatusEvent> statusEventList = statusEventService.findStatusEvent(findStatusEvent);
        return new ResponseEntity<>(statusEventList, HttpStatus.OK);
    }
}
