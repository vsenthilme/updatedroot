package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.fgDelivery.FgDelivery;
import com.tekclover.wms.api.mfg.model.fgDelivery.SearchFgDelivery;
import com.tekclover.wms.api.mfg.service.FgDeliveryService;
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
@Api(tags = {"FgDelivery"}, value = "FgDelivery Operations related to FgDeliveryController")
@SwaggerDefinition(tags = {@Tag(name = "FgDelivery", description = "Operations related to FgDelivery")})
@RequestMapping("/fgDelivery")
@RestController
public class FgDeliveryController {

    @Autowired
    FgDeliveryService fgDeliveryService;

    @ApiOperation(response = FgDelivery.class, value = "Get a FgDelivery")
    @GetMapping("")
    public ResponseEntity<?> getFgDelivery(@RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String plantId, @RequestParam String receipeId, @RequestParam String operationNumber, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode) {
        FgDelivery fgDelivery = fgDeliveryService.getFgDelivery(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
        return new ResponseEntity<>(fgDelivery, HttpStatus.OK);
    }

    @ApiOperation(response = FgDelivery.class, value = "Search FgDelivery")
    @PostMapping("/findFgDelivery")
    public Stream<FgDelivery> findFgDelivery(@RequestBody SearchFgDelivery searchFgDelivery) throws Exception {
        return fgDeliveryService.findFgDelivery(searchFgDelivery);
    }

    @ApiOperation(response = FgDelivery.class, value = "Create FgDelivery Batch")
    @PostMapping("/batch")
    public ResponseEntity<?> postFgDelivery(@Valid @RequestBody List<FgDelivery> newFgDelivery,@RequestParam String loginUserID) {
        List<FgDelivery> createdFgDelivery = fgDeliveryService.createFgDeliveryBatch(newFgDelivery, loginUserID);
        return new ResponseEntity<>(createdFgDelivery, HttpStatus.OK);
    }

    @ApiOperation(response = FgDelivery.class, value = "Patch FgDelivery Batch")
    @PatchMapping("/batch")
    public ResponseEntity<?> patchFgDelivery(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @Valid @RequestBody List<FgDelivery> modifyFgDelivery, @RequestParam String loginUserID) {
        List<FgDelivery> updatedFgDelivery = fgDeliveryService.updateFgDeliveryBatch(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID, modifyFgDelivery);
        return new ResponseEntity<>(updatedFgDelivery, HttpStatus.OK);
    }

    @ApiOperation(response = FgDelivery.class, value = "Delete FgDelivery")
    @DeleteMapping("")
    public ResponseEntity<?> deleteFgDelivery(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @RequestParam String loginUserID) {
        fgDeliveryService.deleteFgDelivery(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}