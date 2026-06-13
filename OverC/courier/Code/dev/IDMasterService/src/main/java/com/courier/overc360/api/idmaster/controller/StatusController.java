package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.status.AddStatus;
import com.courier.overc360.api.idmaster.primary.model.status.Status;
import com.courier.overc360.api.idmaster.primary.model.status.UpdateStatus;
import com.courier.overc360.api.idmaster.replica.model.status.FindReplicaStatus;
import com.courier.overc360.api.idmaster.replica.model.status.ReplicaStatus;
import com.courier.overc360.api.idmaster.service.StatusService;
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
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"Status"}, value = "Status Operations related to StatusController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Status", description = "Operations related to Status")})
@RequestMapping("/status")
@RestController
public class StatusController {

    @Autowired
    StatusService statusService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create Status
    @ApiOperation(response = Status.class, value = "Create new Status") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postStatus(@Valid @RequestBody AddStatus addStatus, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        Status newStatus = statusService.createStatus(addStatus, loginUserID);
        return new ResponseEntity<>(newStatus, HttpStatus.OK);
    }

    // Update Status
    @ApiOperation(response = Status.class, value = "Update Status") // label for swagger
    @PatchMapping("/{statusId}")
    public ResponseEntity<?> patchStatus(@PathVariable String statusId, @RequestParam String languageId,
                                         @RequestParam String loginUserID, @RequestBody UpdateStatus updateStatus)
            throws IllegalAccessException, InvocationTargetException, ParseException {
        Status updatedStatus = statusService.updateStatus(languageId, statusId, loginUserID, updateStatus);
        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }

    // Delete Status
    @ApiOperation(response = Status.class, value = "Delete Status") // label for swagger
    @DeleteMapping("/{statusId}")
    public ResponseEntity<?> deleteStatus(@PathVariable String statusId, @RequestParam String languageId,
                                          @RequestParam String loginUserID) {
        statusService.deleteStatus(languageId, statusId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Status Details
    @ApiOperation(response = ReplicaStatus.class, value = "Get all Status Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllStatusDetails() {
        List<ReplicaStatus> statusList = statusService.getAllStatuses();
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }

    // Get Status
    @ApiOperation(response = ReplicaStatus.class, value = "Get a Status") // label for swagger
    @GetMapping("/{statusId}")
    public ResponseEntity<?> getStatus(@PathVariable String statusId, @RequestParam String languageId) {
        ReplicaStatus dbStatus = statusService.getReplicaStatus(languageId, statusId);
        return new ResponseEntity<>(dbStatus, HttpStatus.OK);
    }

    // Find Status
    @ApiOperation(response = ReplicaStatus.class, value = "Find Status") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findStatus(@Valid @RequestBody FindReplicaStatus findReplicaStatus) throws Exception {
        List<ReplicaStatus> statusList = statusService.findReplicaStatus(findReplicaStatus);
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }
}
