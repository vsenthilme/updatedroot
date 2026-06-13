package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.loadtype.AddLoadType;
import com.courier.overc360.api.idmaster.primary.model.loadtype.LoadType;
import com.courier.overc360.api.idmaster.primary.model.loadtype.UpdateLoadType;
import com.courier.overc360.api.idmaster.replica.model.loadtype.FindLoadType;
import com.courier.overc360.api.idmaster.replica.model.loadtype.ReplicaLoadType;
import com.courier.overc360.api.idmaster.service.LoadTypeService;
import com.opencsv.exceptions.CsvException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Api(tags = {"LoadType"}, value = "LoadTyoe Operations related to LoadTypeController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "LoadType", description = "Operations related to LoadType")})
@RequestMapping("/loadType")
@RestController
public class LoadTypeController {

    @Autowired
    LoadTypeService loadTypeService;

    /*========================================PRIMARY==================================================================*/

    //Create loadtype
    @ApiOperation(response = LoadType.class, value = "Create new LoadType") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postLoadType(@Valid @RequestBody AddLoadType addLoadType, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        LoadType createdLoadType = loadTypeService.createLoadType(addLoadType, loginUserID);
        return new ResponseEntity<>(createdLoadType, HttpStatus.OK);
    }

    // Update LoadType
    @ApiOperation(response = LoadType.class, value = "Update LoadType") // label for swagger
    @PatchMapping("/{loadTypeId}")
    public ResponseEntity<?> patchLoadType(@PathVariable String loadTypeId, @RequestParam String languageId,
                                           @RequestParam String loginUserID, @RequestParam String companyId,
                                           @RequestBody UpdateLoadType updateLoadType)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        LoadType updatedLoadType = loadTypeService.updateLoadType(loadTypeId, languageId, companyId, updateLoadType, loginUserID);
        return new ResponseEntity<>(updatedLoadType, HttpStatus.OK);
    }

    // Delete LoadType
    @ApiOperation(response = LoadType.class, value = "Delete LoadType") // label for swagger
    @DeleteMapping("/{loadTypeId}")
    public ResponseEntity<?> deleteLoadType(@PathVariable String loadTypeId, @RequestParam String languageId,
                                            @RequestParam String companyId, @RequestParam String loginUserID) {
        loadTypeService.deleteLoadType(loadTypeId, languageId, companyId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*========================================REPLICA==================================================================*/

    // Get All LoadType Details
    @ApiOperation(response = ReplicaLoadType.class, value = "Get all LoadType Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllLoadType() {
        List<ReplicaLoadType> loadTypeList = loadTypeService.getAllLoadType();
        return new ResponseEntity<>(loadTypeList, HttpStatus.OK);
    }

    // Get LoadType
    @ApiOperation(response = ReplicaLoadType.class, value = "Get a LoadType") // label for swagger
    @GetMapping("/{loadTypeId}")
    public ResponseEntity<?> getLoadType(@PathVariable String loadTypeId, @RequestParam String languageId,
                                         @RequestParam String companyId) {
        ReplicaLoadType dbLoadType = loadTypeService.replicaGetLoadType(loadTypeId, languageId, companyId);
        return new ResponseEntity<>(dbLoadType, HttpStatus.OK);
    }

    // Find LoadType
    @ApiOperation(response = ReplicaLoadType.class, value = "Find LoadType") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findLoadTypes(@Valid @RequestBody FindLoadType findLoadType) throws Exception {
        List<ReplicaLoadType> loadTypeList = loadTypeService.findLoadType(findLoadType);
        return new ResponseEntity<>(loadTypeList, HttpStatus.OK);
    }

}
