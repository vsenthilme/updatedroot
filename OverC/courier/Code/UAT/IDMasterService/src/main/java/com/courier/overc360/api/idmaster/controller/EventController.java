package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.event.AddEvent;
import com.courier.overc360.api.idmaster.primary.model.event.Event;
import com.courier.overc360.api.idmaster.primary.model.event.UpdateEvent;
import com.courier.overc360.api.idmaster.replica.model.event.FindEvent;
import com.courier.overc360.api.idmaster.replica.model.event.ReplicaEvent;
import com.courier.overc360.api.idmaster.service.EventService;
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
@Api(tags = {"Event"}, value = "Event operations related to EventController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Event", description = "Operations related to Event")})
@RequestMapping("/event")
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create Event
    @ApiOperation(response = Event.class, value = "Create Event") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postEvent(@Valid @RequestBody AddEvent newEvent, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Event createdEvent = eventService.createEvent(newEvent, loginUserID);
        return new ResponseEntity<>(createdEvent, HttpStatus.OK);
    }

    // Update Event
    @ApiOperation(response = Event.class, value = "Update Event") // label for swagger
    @PatchMapping("/{eventCode}")
    public ResponseEntity<?> patchEvent(@PathVariable String eventCode, @RequestParam String languageId, @RequestParam String companyId,
                                        @RequestParam String loginUserID, @RequestBody UpdateEvent updateEvent)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Event updatedEvent = eventService.updateEvent(languageId, companyId, eventCode, loginUserID, updateEvent);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    // Delete Event
    @ApiOperation(response = Event.class, value = "Delete Event") // label for swagger
    @DeleteMapping("/{eventCode}")
    public ResponseEntity<?> deleteEvent(@PathVariable String eventCode, @RequestParam String languageId,
                                         @RequestParam String companyId, @RequestParam String loginUserID) {
        eventService.deleteEvent(languageId, companyId, eventCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Event Details
    @ApiOperation(response = ReplicaEvent.class, value = "Get all Event details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllEvents() {
        List<ReplicaEvent> eventList = eventService.getAllEvents();
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    // Get Event
    @ApiOperation(response = ReplicaEvent.class, value = "Get Event") // label for swagger
    @GetMapping("/{eventCode}")
    public ResponseEntity<?> getEvent(@PathVariable String eventCode, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaEvent event = eventService.replicaGetEvent(languageId, companyId, eventCode);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    // Find Event
    @ApiOperation(response = ReplicaEvent.class, value = "Find Event") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findEvent(@RequestBody FindEvent findEvent) throws Exception {
        List<ReplicaEvent> createdEvent = eventService.findEvents(findEvent);
        return new ResponseEntity<>(createdEvent, HttpStatus.OK);
    }
}
