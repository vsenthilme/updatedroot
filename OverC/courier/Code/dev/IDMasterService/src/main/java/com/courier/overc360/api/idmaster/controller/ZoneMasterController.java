package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.zonemaster.AddZoneMaster;
import com.courier.overc360.api.idmaster.primary.model.zonemaster.UpdateZoneMaster;
import com.courier.overc360.api.idmaster.primary.model.zonemaster.ZoneMaster;
import com.courier.overc360.api.idmaster.replica.model.zonemaster.FindZoneMaster;
import com.courier.overc360.api.idmaster.replica.model.zonemaster.ReplicaZoneMaster;
import com.courier.overc360.api.idmaster.service.ZoneMasterService;
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
@Api(tags = {"ZoneMaster"}, value = "ZoneMaster Operations related to ZoneMasterController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ZoneMaster", description = "Operations related to ZoneMaster")})
@RequestMapping("/zonemaster")
@RestController
public class ZoneMasterController {

    @Autowired
    private ZoneMasterService zoneMasterService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = ZoneMaster.class, value = "Create New ZoneMaster")
    @PostMapping("")
    public ResponseEntity<?> createZoneMaster(@Valid @RequestBody AddZoneMaster addZoneMaster, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ZoneMaster newZoneMaster = zoneMasterService.createZoneMaster(addZoneMaster, loginUserID);
        return new ResponseEntity<>(newZoneMaster, HttpStatus.OK);
    }

    ///Update
    @ApiOperation(response = ZoneMaster.class, value = "Update ZoneMaster")
    @PatchMapping("/{zoneId}")
    public ResponseEntity<?> patchZoneMaster(@PathVariable String zoneId, @RequestParam String companyId, @RequestParam String languageId,
                                             @RequestParam String zoneType, @RequestParam String hubCode, @RequestParam String loginUserID, @RequestBody UpdateZoneMaster updateZoneMaster)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ZoneMaster updatedZoneMaster = zoneMasterService.updateZoneMaster(companyId, languageId, zoneId, zoneType, hubCode, updateZoneMaster, loginUserID);
        return new ResponseEntity<>(updatedZoneMaster, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = ZoneMaster.class, value = "Delete ZoneMaster")
    @DeleteMapping("/{zoneId}")
    public ResponseEntity<?> deleteZoneMaster(@PathVariable String zoneId, @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String zoneType, @RequestParam String hubCode, @RequestParam String loginUserID) {
        zoneMasterService.deleteZoneMaster(companyId, languageId, zoneId, zoneType, hubCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ZoneMaster Details
    @ApiOperation(response = ReplicaZoneMaster.class, value = "Get all ZoneMaster Details")
    @GetMapping("")
    public ResponseEntity<?> getAllZoneMasterDetails() {
        List<ReplicaZoneMaster> zoneMasterList = zoneMasterService.getAll();
        return new ResponseEntity<>(zoneMasterList, HttpStatus.OK);
    }

    //Get ZoneMaster
    @ApiOperation(response = ReplicaZoneMaster.class, value = "Get a ZoneMaster")
    @GetMapping("/{zoneId}")
    public ResponseEntity<?> getZoneMaster(@PathVariable String zoneId, @RequestParam String companyId,
                                           @RequestParam String languageId, @RequestParam String zoneType,
                                           @RequestParam String hubCode) {
        ReplicaZoneMaster dbZoneMaster = zoneMasterService.getReplicaZoneMaster(companyId, languageId, zoneId, zoneType, hubCode);
        return new ResponseEntity<>(dbZoneMaster, HttpStatus.OK);
    }

    //Find ZoneMaster
    @ApiOperation(response = ReplicaZoneMaster.class, value = "Find ZoneMaster")
    @PostMapping("/find")
    public ResponseEntity<?> findZoneMaster(@Valid @RequestBody FindZoneMaster findZoneMaster) throws Exception {
        List<ReplicaZoneMaster> zoneMasterList = zoneMasterService.findZoneMaster(findZoneMaster);
        return new ResponseEntity<>(zoneMasterList, HttpStatus.OK);
    }

}
