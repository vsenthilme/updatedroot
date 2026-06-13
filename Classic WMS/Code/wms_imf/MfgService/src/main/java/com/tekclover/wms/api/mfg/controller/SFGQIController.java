package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.sfgqi.SFGQI;
import com.tekclover.wms.api.mfg.model.sfgqi.SearchSFGQI;
import com.tekclover.wms.api.mfg.service.SFGQIService;
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
@Api(tags = {"SFGQI"}, value = "SFGQI  Operations related to SFGQIController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "SFGQI", description = "Operations related to SFGQI")})
@RequestMapping("/sfgqi")
@RestController
public class SFGQIController {

    @Autowired
    SFGQIService sfgqiService;

    @ApiOperation(response = SFGQI.class, value = "Get a SFGQI")
    @GetMapping("")
    public ResponseEntity<?> geSFGQI(@RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String plantId, @RequestParam String receipeId, @RequestParam String operationNumber, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode) {
        SFGQI sfgqi = sfgqiService.getSFGQI(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
        return new ResponseEntity<>(sfgqi, HttpStatus.OK);
    }

    @ApiOperation(response = SFGQI.class, value = "Search SFGQI")
    @PostMapping("/findSFGQI")
    public Stream<SFGQI> findSFGQI(@RequestBody SearchSFGQI searchSFGQI) throws Exception {
        return sfgqiService.findSFGQI(searchSFGQI);
    }

    @ApiOperation(response = SFGQI.class, value = "Create SFGQI Batch")
    @PostMapping("/batch")
    public ResponseEntity<?> postSFGQI(@Valid @RequestBody List<SFGQI> newSFGQI, @RequestParam String loginUserID) {
        List<SFGQI> createdSFGQI = sfgqiService.createSFGQIBatch(newSFGQI, loginUserID);
        return new ResponseEntity<>(createdSFGQI, HttpStatus.OK);
    }

    @ApiOperation(response = SFGQI.class, value = "Patch SFGQI Batch")
    @PatchMapping("/batch")
    public ResponseEntity<?> patchSFGQI(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @Valid @RequestBody List<SFGQI> modifySFGQI, @RequestParam String loginUserID) {
        List<SFGQI> updatedSFGQI = sfgqiService.updateSFGQIBatch(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID, modifySFGQI);
        return new ResponseEntity<>(updatedSFGQI, HttpStatus.OK);
    }

    @ApiOperation(response = SFGQI.class, value = "Delete SFGQI")
    @DeleteMapping("")
    public ResponseEntity<?> deleteSFGQI(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @RequestParam String loginUserID) {
        sfgqiService.deleteSFGQI(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}