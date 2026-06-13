package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.masterreceipe.MasterReceipe;
import com.tekclover.wms.api.mfg.model.masterreceipe.SearchMasterReceipe;
import com.tekclover.wms.api.mfg.service.MasterReceipeService;
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
@Api(tags = {"MasterReceipe"}, value = "MasterReceipe  Operations related to MasterReceipeController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "MasterReceipe ", description = "Operations related to MasterReceipe")})
@RequestMapping("/masterreceipe")
@RestController
public class MasterReceipeController {

    @Autowired
    MasterReceipeService masterReceipeService;

    @ApiOperation(response = MasterReceipe.class, value = "Get a MasterReceipe") // label for swagger 
    @GetMapping("/{receipeId}")
    public ResponseEntity<?> getMasterReceipe(@PathVariable String receipeId, @RequestParam String companyCodeId, @RequestParam String plantId,
                                              @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber,
                                              @RequestParam String itemCode, @RequestParam String bomNumber, @RequestParam String childItemCode, @RequestParam String phaseNumber) {
        MasterReceipe masterreceipe = masterReceipeService.getMasterReceipe(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId,
                itemCode, bomNumber, phaseNumber, childItemCode);
        return new ResponseEntity<>(masterreceipe, HttpStatus.OK);
    }

    @ApiOperation(response = MasterReceipe.class, value = "Get a MasterReceipe") // label for swagger
    @GetMapping("/v2/{receipeId}")
    public ResponseEntity<?> getMasterReceipe(@PathVariable String receipeId, @RequestParam String companyCodeId, @RequestParam String plantId,
                                              @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber) {
        List<MasterReceipe> masterreceipe = masterReceipeService.getMasterReceipe(companyCodeId, plantId, languageId, warehouseId, receipeId, operationNumber);
        return new ResponseEntity<>(masterreceipe, HttpStatus.OK);
    }

    @ApiOperation(response = MasterReceipe.class, value = "Search MasterReceipe") // label for swagger
    @PostMapping("/findMasterReceipe")
    public Stream<MasterReceipe> findMasterReceipe(@RequestBody SearchMasterReceipe searchMasterReceipe)
            throws Exception {
        return masterReceipeService.findMasterReceipe(searchMasterReceipe);
    }

    @ApiOperation(response = MasterReceipe.class, value = "Create MasterReceipe") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postMasterReceipe(@Valid @RequestBody MasterReceipe newMasterReceipe, @RequestParam String loginUserID) {
        MasterReceipe createdMasterReceipe = masterReceipeService.createMasterReceipe(newMasterReceipe, loginUserID);
        return new ResponseEntity<>(createdMasterReceipe, HttpStatus.OK);
    }

    @ApiOperation(response = MasterReceipe.class, value = "Create MasterReceipe Batch") // label for swagger
    @PostMapping("/batch")
    public ResponseEntity<?> postMasterReceipeBatch(@Valid @RequestBody List<MasterReceipe> newMasterReceipe, @RequestParam String loginUserID) {
        List<MasterReceipe> createdMasterReceipe = masterReceipeService.createMasterReceipeBatch(newMasterReceipe, loginUserID);
        return new ResponseEntity<>(createdMasterReceipe, HttpStatus.OK);
    }

    @ApiOperation(response = MasterReceipe.class, value = "Patch MasterReceipe Batch") // label for swagger
    @PatchMapping("/batch")
    public ResponseEntity<?> patchMasterReceipeBatch(@RequestParam String companyCodeId, @RequestParam String plantId,
                                                     @RequestParam String languageId, @RequestParam String warehouseId,
                                                     @Valid @RequestBody List<MasterReceipe> modifyMasterReceipe, @RequestParam String loginUserID) {
        List<MasterReceipe> createdMasterReceipe = masterReceipeService.updateMasterReceipeBatch(companyCodeId, plantId, languageId, warehouseId, loginUserID, modifyMasterReceipe);
        return new ResponseEntity<>(createdMasterReceipe, HttpStatus.OK);
    }

    @ApiOperation(response = MasterReceipe.class, value = "Delete MasterReceipe") // label for swagger
    @DeleteMapping("/{receipeId}")
    public ResponseEntity<?> deleteMasterReceipe(@PathVariable String receipeId, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                 @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber,
                                                 @RequestParam String loginUserID) {
        masterReceipeService.deleteMasterReceipe(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}