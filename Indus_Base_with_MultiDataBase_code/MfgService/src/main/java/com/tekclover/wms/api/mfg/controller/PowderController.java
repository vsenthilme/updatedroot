package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.powder.Powder;
import com.tekclover.wms.api.mfg.model.powder.SearchPowder;
import com.tekclover.wms.api.mfg.service.PowderService;
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
@Api(tags = {"Powder"}, value = "Powder  Operations related to PowderController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Powder ", description = "Operations related to Powder")})
@RequestMapping("/powder")
@RestController
public class PowderController {

    @Autowired
    PowderService powderService;

    @ApiOperation(response = Powder.class, value = "Get a Powder")
    @GetMapping("")
    public ResponseEntity<?> getPowder(@RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String plantId, @RequestParam String receipeId, @RequestParam String operationNumber, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode) {
        Powder powder = powderService.getPowder(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
        return new ResponseEntity<>(powder, HttpStatus.OK);
    }

    @ApiOperation(response = Powder.class, value = "Search Powder")
    @PostMapping("/findPowder")
    public Stream<Powder> findPowder(@RequestBody SearchPowder searchPowder) throws Exception {
        return powderService.findPowder(searchPowder);
    }

    @ApiOperation(response = Powder.class, value = "Create Powder Batch")
    @PostMapping("/batch")
    public ResponseEntity<?> postPowderBatch(@Valid @RequestBody List<Powder> newPowder, @RequestParam String loginUserID) {
        List<Powder> createdPowder = powderService.createPowderBatch(newPowder, loginUserID);
        return new ResponseEntity<>(createdPowder, HttpStatus.OK);
    }

    @ApiOperation(response = Powder.class, value = "Patch Powder Batch")
    @PatchMapping("/batch")
    public ResponseEntity<?> patchPowderBatch(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @Valid @RequestBody List<Powder> modifyPowder, @RequestParam String loginUserID) {
        List<Powder> updatedPowder = powderService.updatePowderBatch(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID, modifyPowder);
        return new ResponseEntity<>(updatedPowder, HttpStatus.OK);
    }

    @ApiOperation(response = Powder.class, value = "Delete Powder")
    @DeleteMapping("")
    public ResponseEntity<?> deletePowder(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @RequestParam String loginUserID) {
        powderService.deletePowder(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
