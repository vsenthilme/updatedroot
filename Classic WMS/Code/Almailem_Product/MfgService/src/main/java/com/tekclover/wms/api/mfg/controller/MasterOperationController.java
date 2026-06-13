package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.masteroperation.MasterOperation;
import com.tekclover.wms.api.mfg.model.masteroperation.SearchMasterOperation;
import com.tekclover.wms.api.mfg.service.MasterOperationService;
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
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Validated
@Api(tags = {"MasterOperation"}, value = "MasterOperation  Operations related to MasterOperationController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "MasterOperation ", description = "Operations related to MasterOperation")})
@RequestMapping("/masteroperation")
@RestController
public class MasterOperationController {

    @Autowired
    MasterOperationService masterOperationService;

    @ApiOperation(response = MasterOperation.class, value = "Get a MasterOperation") // label for swagger 
    @GetMapping("/{operationNumber}")
    public ResponseEntity<?> getMasterOperation(@PathVariable String operationNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String phaseNumber) {
        MasterOperation masteroperation = masterOperationService.getMasterOperation(companyCodeId, plantId, languageId, warehouseId, operationNumber, phaseNumber);
        return new ResponseEntity<>(masteroperation, HttpStatus.OK);
    }

    @ApiOperation(response = MasterOperation.class, value = "Get MasterOperations") // label for swagger
    @GetMapping("/v2/{operationNumber}")
    public ResponseEntity<?> getMasterOperations(@PathVariable String operationNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                 @RequestParam String languageId, @RequestParam String warehouseId) {
        List<MasterOperation> masteroperation = masterOperationService.getMasterOperations(companyCodeId, plantId, languageId, warehouseId, operationNumber);
        return new ResponseEntity<>(masteroperation, HttpStatus.OK);
    }

    @ApiOperation(response = MasterOperation.class, value = "Search MasterOperation") // label for swagger
    @PostMapping("/findMasterOperation")
    public Stream<MasterOperation> findMasterOperation(@RequestBody SearchMasterOperation searchMasterOperation)
            throws Exception {
        return masterOperationService.findMasterOperation(searchMasterOperation);
    }

    @ApiOperation(response = MasterOperation.class, value = "Create MasterOperation") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postMasterOperation(@Valid @RequestBody MasterOperation newMasterOperation, @RequestParam String loginUserID) {
        MasterOperation createdMasterOperation = masterOperationService.createMasterOperation(newMasterOperation, loginUserID);
        return new ResponseEntity<>(createdMasterOperation, HttpStatus.OK);
    }

    @ApiOperation(response = MasterOperation.class, value = "Patch MasterOperation") // label for swagger
    @PatchMapping("/{operationNumber}")
    public ResponseEntity<?> patchMasterOperation(@PathVariable String operationNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                  @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String phaseNumber,
                                                  @Valid @RequestBody MasterOperation modifyMasterOperation, @RequestParam String loginUserID) {
        MasterOperation createdMasterOperation = masterOperationService.updateMasterOperation(companyCodeId, plantId, languageId, warehouseId, operationNumber, phaseNumber, loginUserID, modifyMasterOperation);
        return new ResponseEntity<>(createdMasterOperation, HttpStatus.OK);
    }

    @ApiOperation(response = MasterOperation.class, value = "Create MasterOperation Batch") // label for swagger
    @PostMapping("/batch")
    public ResponseEntity<?> postMasterOperationBatch(@Valid @RequestBody List<MasterOperation> newMasterOperation, @RequestParam String loginUserID) {
        List<MasterOperation> createdMasterOperation = masterOperationService.createMasterOperationBatch(newMasterOperation, loginUserID);
        return new ResponseEntity<>(createdMasterOperation, HttpStatus.OK);
    }

    @ApiOperation(response = MasterOperation.class, value = "Patch MasterOperation") // label for swagger
    @PatchMapping("/batch")
    public ResponseEntity<?> patchMasterOperationBatch(@RequestParam String companyCodeId, @RequestParam String plantId,
                                                       @RequestParam String languageId, @RequestParam String warehouseId,
                                                       @Valid @RequestBody List<MasterOperation> modifyMasterOperation, @RequestParam String loginUserID) {
        List<MasterOperation> createdMasterOperation = masterOperationService.updateMasterOperationBatch(companyCodeId, plantId, languageId, warehouseId, loginUserID, modifyMasterOperation);
        return new ResponseEntity<>(createdMasterOperation, HttpStatus.OK);
    }

    @ApiOperation(response = MasterOperation.class, value = "Delete MasterOperation") // label for swagger
    @DeleteMapping("/{operationNumber}")
    public ResponseEntity<?> deleteMasterOperation(@PathVariable String operationNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                   @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam(required = false) String phaseNumber,
                                                   @RequestParam String loginUserID) {
        masterOperationService.deleteMasterOperation(companyCodeId, plantId, languageId, warehouseId, operationNumber, phaseNumber, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}