package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.packingqc.PackingQc;
import com.tekclover.wms.api.mfg.model.packingqc.SearchPackingQc;
import com.tekclover.wms.api.mfg.service.PackingQcService;
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
@Api(tags = {"PackingQc"}, value = "PackingQc  Operations related to PackingQcController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "PackingQc", description = "Operations related to PackingQc")})
@RequestMapping("/packingQc")
@RestController
public class PackingQcController {

    @Autowired
    PackingQcService packingQcService;

    @ApiOperation(response = PackingQc.class, value = "Get a PackingQc")
    @GetMapping("")
    public ResponseEntity<?> getPackingQc(@RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String plantId, @RequestParam String receipeId, @RequestParam String operationNumber, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode) {
        PackingQc packingQc = packingQcService.getPackingQc(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
        return new ResponseEntity<>(packingQc, HttpStatus.OK);
    }

    @ApiOperation(response = PackingQc.class, value = "Search PackingQc")
    @PostMapping("/findPackingQc")
    public Stream<PackingQc> findPacking(@RequestBody SearchPackingQc searchPackingQc) throws Exception {
        return packingQcService.findPackingQc(searchPackingQc);
    }

    @ApiOperation(response = PackingQc.class, value = "Create PackingQc Batch")
    @PostMapping("/batch")
    public ResponseEntity<?> postPackingQc(@Valid @RequestBody List<PackingQc> newPackingQc, @RequestParam String loginUserID) {
        List<PackingQc> createdPackingQc = packingQcService.createPackingQcBatch(newPackingQc, loginUserID);
        return new ResponseEntity<>(createdPackingQc, HttpStatus.OK);
    }

    @ApiOperation(response = PackingQc.class, value = "Patch PackingQc Batch")
    @PatchMapping("/batch")
    public ResponseEntity<?> patchPackingQc(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @Valid @RequestBody List<PackingQc> modifyPackingQc, @RequestParam String loginUserID) {
        List<PackingQc> updatedPackingQc = packingQcService.updatePackingQcBatch(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID, modifyPackingQc);
        return new ResponseEntity<>(updatedPackingQc, HttpStatus.OK);
    }

    @ApiOperation(response = PackingQc.class, value = "Delete PackingQc")
    @DeleteMapping("")
    public ResponseEntity<?> deletePackingQc(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @RequestParam String loginUserID) {
        packingQcService.deletePackingQc(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}