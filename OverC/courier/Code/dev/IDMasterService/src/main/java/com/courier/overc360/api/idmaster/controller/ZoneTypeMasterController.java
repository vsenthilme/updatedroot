package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.zonetypemaster.AddZoneTypeMaster;
import com.courier.overc360.api.idmaster.primary.model.zonetypemaster.ZoneTypeMaster;
import com.courier.overc360.api.idmaster.primary.model.zonetypemaster.UpdateZoneTypeMaster;
import com.courier.overc360.api.idmaster.replica.model.zonetypemaster.FindZoneTypeMaster;
import com.courier.overc360.api.idmaster.replica.model.zonetypemaster.ReplicaZoneTypeMaster;
import com.courier.overc360.api.idmaster.service.ZoneTypeMasterService;
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
@Api(tags = {"ZoneTypeMaster"}, value = "ZoneTypeMaster Operations related to ZoneTypeMasterController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ZoneTypeMaster", description = "Operations related to ZoneTypeMaster")})
@RequestMapping("/zoneTypeMaster")
@RestController
public class ZoneTypeMasterController {

    @Autowired
    private ZoneTypeMasterService zoneTypeMasterService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = ZoneTypeMaster.class, value = "Create New ZoneTypeMaster")
    @PostMapping("")
    public ResponseEntity<?> createZoneTypeMaster(@Valid @RequestBody AddZoneTypeMaster addZoneTypeMaster, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ZoneTypeMaster newZoneTypeMaster = zoneTypeMasterService.createZoneTypeMaster(addZoneTypeMaster, loginUserID);
        return new ResponseEntity<>(newZoneTypeMaster, HttpStatus.OK);
    }

    ///Update
    @ApiOperation(response = ZoneTypeMaster.class, value = "Update ZoneTypeMaster")
    @PatchMapping("/{zoneTypeId}")
    public ResponseEntity<?> patchZoneTypeMaster(@PathVariable String zoneTypeId, @RequestParam String companyId, @RequestParam String languageId,
                                                     @RequestParam String loginUserID, @RequestBody UpdateZoneTypeMaster updateZoneTypeMaster)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ZoneTypeMaster updatedZoneTypeMaster = zoneTypeMasterService.updateZoneTypeMaster(companyId, languageId, zoneTypeId, updateZoneTypeMaster, loginUserID);
        return new ResponseEntity<>(updatedZoneTypeMaster, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = ZoneTypeMaster.class, value = "Delete ZoneTypeMaster")
    @DeleteMapping("/{zoneTypeId}")
    public ResponseEntity<?> deleteZoneTypeMaster(@PathVariable String zoneTypeId, @RequestParam String companyId, @RequestParam String languageId,
                                                     @RequestParam String loginUserID) {
        zoneTypeMasterService.deleteZoneTypeMaster(companyId, languageId, zoneTypeId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ZoneTypeMaster Details
    @ApiOperation(response = ReplicaZoneTypeMaster.class, value = "Get all ZoneTypeMaster Details")
    @GetMapping("")
    public ResponseEntity<?> getAllZoneTypeMasterDetails() {
        List<ReplicaZoneTypeMaster> zoneTypeMasterList = zoneTypeMasterService.getAll();
        return new ResponseEntity<>(zoneTypeMasterList, HttpStatus.OK);
    }

    //Get ZoneTypeMaster
    @ApiOperation(response = ReplicaZoneTypeMaster.class, value = "Get a ZoneTypeMaster")
    @GetMapping("/{zoneTypeId}")
    public ResponseEntity<?> getZoneTypeMaster(@PathVariable String zoneTypeId, @RequestParam String companyId, @RequestParam String languageId) {
        ReplicaZoneTypeMaster dbZoneTypeMaster = zoneTypeMasterService.getReplicaZoneTypeMaster(companyId, languageId, zoneTypeId);
        return new ResponseEntity<>(dbZoneTypeMaster, HttpStatus.OK);
    }

    //Find ZoneTypeMaster
    @ApiOperation(response = ReplicaZoneTypeMaster.class, value = "Find ZoneTypeMaster")
    @PostMapping("/find")
    public ResponseEntity<?> findZoneTypeMaster(@Valid @RequestBody FindZoneTypeMaster findZoneTypeMaster) throws Exception {
        List<ReplicaZoneTypeMaster> zoneTypeMasterList = zoneTypeMasterService.findZoneTypeMaster(findZoneTypeMaster);
        return new ResponseEntity<>(zoneTypeMasterList, HttpStatus.OK);
    }

}
