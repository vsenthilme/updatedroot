package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.consignmentType.AddConsignmentType;
import com.courier.overc360.api.idmaster.primary.model.consignmentType.ConsignmentType;
import com.courier.overc360.api.idmaster.primary.model.consignmentType.UpdateConsignmentType;
import com.courier.overc360.api.idmaster.replica.model.consignmentType.FindConsignmentType;
import com.courier.overc360.api.idmaster.replica.model.consignmentType.ReplicaConsignmentType;
import com.courier.overc360.api.idmaster.service.ConsignmentTypeService;
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
@Api(tags = {"ConsignmentType"}, value = "ConsignmentType  Operations related to ConsignmentTypeController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ConsignmentType", description = "Operations related to ConsignmentType")})
@RequestMapping("/consignmentType")
@RestController
public class ConsignmentTypeController {

    @Autowired
    ConsignmentTypeService consignmentTypeService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/


    // Create ConsignmentType
    @ApiOperation(response = ConsignmentType.class, value = "Create ConsignmentType") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postConsignmentType(@Valid @RequestBody AddConsignmentType addConsignmentType, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ConsignmentType consignmentType = consignmentTypeService.createConsignmentType(addConsignmentType, loginUserID);
        return new ResponseEntity<>(consignmentType, HttpStatus.OK);
    }

    // Update ConsignmentType
    @ApiOperation(response = ConsignmentType.class, value = "Update ConsignmentType") // label for swagger
    @PatchMapping("/{consignmentTypeId}")
    public ResponseEntity<?> patchConsignmentType(@PathVariable String consignmentTypeId, @RequestParam String companyId, @RequestParam String languageId,
                                                  @RequestParam String loginUserID, @RequestBody UpdateConsignmentType updateConsignmentType)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ConsignmentType updatedConsignmentType =
                consignmentTypeService.updateConsignmentType(companyId, languageId, consignmentTypeId, loginUserID, updateConsignmentType);
        return new ResponseEntity<>(updatedConsignmentType, HttpStatus.OK);
    }

    // Delete ConsignmentType
    @ApiOperation(response = ConsignmentType.class, value = "Delete ConsignmentType") // label for swagger
    @DeleteMapping("/{consignmentTypeId}")
    public ResponseEntity<?> deleteConsignmentType(@PathVariable String consignmentTypeId, @RequestParam String languageId, @RequestParam String companyId,
                                                   @RequestParam String loginUserID) {
        consignmentTypeService.deleteConsignmentType(companyId, languageId, consignmentTypeId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ConsignmentType Details
    @ApiOperation(response = ReplicaConsignmentType.class, value = "Get all ReplicaConsignmentType details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllConsignmentTypes() {
        List<ReplicaConsignmentType> consignmentTypeList = consignmentTypeService.getAllConsignmentTypes();
        return new ResponseEntity<>(consignmentTypeList, HttpStatus.OK);
    }

    // Get ConsignmentType
    @ApiOperation(response = ReplicaConsignmentType.class, value = "Get a ReplicaConsignmentType") // label for swagger
    @GetMapping("/{consignmentTypeId}")
    public ResponseEntity<?> getConsignmentType(@PathVariable String consignmentTypeId, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaConsignmentType dbConsignmentType = consignmentTypeService.getReplicaConsignmentType(companyId, languageId, consignmentTypeId);
        return new ResponseEntity<>(dbConsignmentType, HttpStatus.OK);
    }

    // Find ConsignmentType
    @ApiOperation(response = ReplicaConsignmentType.class, value = "Find ReplicaConsignmentType") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findConsignmentTypes(@Valid @RequestBody FindConsignmentType findConsignmentType) throws Exception {
        List<ReplicaConsignmentType> consignmentTypeList = consignmentTypeService.findConsignmentTypes(findConsignmentType);
        return new ResponseEntity<>(consignmentTypeList, HttpStatus.OK);
    }

}
