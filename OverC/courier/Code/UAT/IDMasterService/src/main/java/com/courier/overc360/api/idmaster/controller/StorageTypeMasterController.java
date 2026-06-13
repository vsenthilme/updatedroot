package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.storagetypemaster.AddStorageTypeMaster;
import com.courier.overc360.api.idmaster.primary.model.storagetypemaster.StorageTypeMaster;
import com.courier.overc360.api.idmaster.primary.model.storagetypemaster.UpdateStorageTypeMaster;
import com.courier.overc360.api.idmaster.replica.model.storagetypemaster.FindStorageTypeMaster;
import com.courier.overc360.api.idmaster.replica.model.storagetypemaster.ReplicaStorageTypeMaster;
import com.courier.overc360.api.idmaster.service.StorageTypeMasterService;
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
@Api(tags = {"StorageTypeMaster"}, value = "StorageTypeMaster Operations related to StorageTypeMasterController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "StorageTypeMaster", description = "Operations related to StorageTypeMaster")})
@RequestMapping("/storageTypeMaster")
@RestController

public class StorageTypeMasterController {

    @Autowired
    private StorageTypeMasterService storageTypeMasterService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = StorageTypeMaster.class, value = "Create New StorageTypeMaster")
    @PostMapping("")
    public ResponseEntity<?> createStorageTypeMaster(@Valid @RequestBody AddStorageTypeMaster addStorageTypeMaster, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        StorageTypeMaster newStorageTypeMaster = storageTypeMasterService.createStorageTypeMaster(addStorageTypeMaster,loginUserID);
        return new ResponseEntity<>(newStorageTypeMaster, HttpStatus.OK);
    }

    ///Update
    @ApiOperation(response = StorageTypeMaster.class, value = "Update StorageTypeMaster")
    @PatchMapping("/{storageTypeId}")
    public ResponseEntity<?> patchStorageTypeMaster(@PathVariable String storageTypeId, @RequestParam String companyId, @RequestParam String languageId,
                                                  @RequestParam String loginUserID, @RequestBody UpdateStorageTypeMaster updateStorageTypeMaster)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        StorageTypeMaster updatedStorageTypeMaster = storageTypeMasterService.updateStorageTypeMaster(companyId, languageId, storageTypeId,updateStorageTypeMaster, loginUserID);
        return new ResponseEntity<>(updatedStorageTypeMaster, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = StorageTypeMaster.class, value = "Delete StorageTypeMaster")
    @DeleteMapping("/{storageTypeId}")
    public ResponseEntity<?> deleteStorageTypeMaster(@PathVariable String storageTypeId, @RequestParam String companyId, @RequestParam String languageId,
                                                   @RequestParam String loginUserID) {
        storageTypeMasterService.deleteStorageTypeMaster( companyId,languageId, storageTypeId,loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All StorageTypeMaster Details
    @ApiOperation(response = ReplicaStorageTypeMaster.class, value = "Get all StorageTypeMaster Details")
    @GetMapping("")
    public ResponseEntity<?> getAllStorageTypeMasterDetails() {
        List<ReplicaStorageTypeMaster> storageTypeMasterList = storageTypeMasterService.getAll();
        return new ResponseEntity<>(storageTypeMasterList, HttpStatus.OK);
    }
    //Get StorageTypeMaster
    @ApiOperation(response = ReplicaStorageTypeMaster.class, value = "Get a StorageTypeMaster")
    @GetMapping("/{storageTypeId}")
    public ResponseEntity<?> getStorageTypeMaster(@PathVariable String storageTypeId, @RequestParam String companyId, @RequestParam String languageId) {
        ReplicaStorageTypeMaster dbStorageTypeMaster = storageTypeMasterService.getReplicaStorageTypeMaster(companyId,languageId,storageTypeId);
        return new ResponseEntity<>(dbStorageTypeMaster, HttpStatus.OK);
    }
    //Find StorageTypeMaster
    @ApiOperation(response = ReplicaStorageTypeMaster.class, value = "Find StorageTypeMaster")
    @PostMapping("/find")
    public ResponseEntity<?> findStorageTypeMaster(@Valid @RequestBody FindStorageTypeMaster findStorageTypeMaster) throws Exception {
        List<ReplicaStorageTypeMaster> storageTypeMasterList = storageTypeMasterService.findStorageTypeMaster(findStorageTypeMaster);
        return new ResponseEntity<>(storageTypeMasterList, HttpStatus.OK);
    }
}
