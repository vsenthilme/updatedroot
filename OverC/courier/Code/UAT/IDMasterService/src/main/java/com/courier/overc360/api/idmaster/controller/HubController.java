package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.hub.AddHub;
import com.courier.overc360.api.idmaster.primary.model.hub.Hub;
import com.courier.overc360.api.idmaster.primary.model.hub.UpdateHub;
import com.courier.overc360.api.idmaster.replica.model.hub.FindHub;
import com.courier.overc360.api.idmaster.replica.model.hub.ReplicaHub;
import com.courier.overc360.api.idmaster.service.HubService;
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
@Api(tags = {"Hub"}, value = "Hub Operations related to HubController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Hub", description = "Operations related to Hub")})
@RequestMapping("/hub")
@RestController
public class HubController {

    @Autowired
    HubService hubService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create Hub
    @ApiOperation(response = Hub.class, value = "Create new Hub") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postHub(@Valid @RequestBody AddHub addHub, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Hub newHub = hubService.createHub(addHub, loginUserID);
        return new ResponseEntity<>(newHub, HttpStatus.OK);
    }

    // Update Hub
    @ApiOperation(response = Hub.class, value = "Update Hub") // label for swagger
    @PatchMapping("/{hubCode}")
    public ResponseEntity<?> patchHub(@PathVariable String hubCode, @RequestParam String languageId, @RequestParam String loginUserID,
                                      @RequestParam String companyId, @RequestBody UpdateHub updateHub)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Hub dbHub = hubService.updateHub(languageId, companyId, hubCode, updateHub, loginUserID);
        return new ResponseEntity<>(dbHub, HttpStatus.OK);
    }

    // Delete Hub
    @ApiOperation(response = Hub.class, value = "Delete Hub") // label for swagger
    @DeleteMapping("/{hubCode}")
    public ResponseEntity<?> deleteHub(@PathVariable String hubCode, @RequestParam String languageId,
                                       @RequestParam String companyId, @RequestParam String loginUserID) {
        hubService.deleteHub(languageId, companyId, hubCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Hub Details
    @ApiOperation(response = ReplicaHub.class, value = "Get all Hub Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllHubDetails() {
        List<ReplicaHub> hubList = hubService.getAllHubs();
        return new ResponseEntity<>(hubList, HttpStatus.OK);
    }

    // Get Hub
    @ApiOperation(response = ReplicaHub.class, value = "Get a Hub") // label for swagger
    @GetMapping("/{hubCode}")
    public ResponseEntity<?> getHub(@PathVariable String hubCode, @RequestParam String languageId,
                                    @RequestParam String companyId) {
        ReplicaHub dbHub = hubService.replicaGetHub(languageId, companyId, hubCode);
        return new ResponseEntity<>(dbHub, HttpStatus.OK);
    }

    // Find Hub
    @ApiOperation(response = ReplicaHub.class, value = "Find Hub") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findHubs(@Valid @RequestBody FindHub findHub) throws Exception {
        List<ReplicaHub> hubList = hubService.findHubs(findHub);
        return new ResponseEntity<>(hubList, HttpStatus.OK);
    }

}
