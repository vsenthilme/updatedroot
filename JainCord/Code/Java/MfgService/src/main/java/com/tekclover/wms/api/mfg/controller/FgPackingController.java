package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.fgpacking.FgPacking;
import com.tekclover.wms.api.mfg.model.fgpacking.SearchFgPacking;
import com.tekclover.wms.api.mfg.service.FgPackingService;
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
@Api(tags = {"FgPacking"}, value = "FgPacking  Operations related to FgPackingController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "FgPacking ", description = "Operations related to FgPacking")})
@RequestMapping("/fgPacking")
@RestController
public class FgPackingController {

    @Autowired
    FgPackingService fgPackingService;

    //-----------------------------------------FgReceiving Controller------------------------------------------------------

    //Get FgPacking
    @ApiOperation(response = FgPacking.class, value = "Get a FgPacking")
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getFgPacking(@PathVariable String productionOrderNo , @RequestParam String languageId,
                                            @RequestParam String companyCodeId, @RequestParam String plantId,
                                            @RequestParam String warehouseId, @RequestParam Long productionOrderLineNo,
                                            @RequestParam String receipeId, @RequestParam String operationNumber,
                                            @RequestParam String itemCode){
        FgPacking fgPacking = fgPackingService.getFgPacking(languageId, companyCodeId, plantId, warehouseId, productionOrderNo, productionOrderLineNo, receipeId, operationNumber, itemCode);
        return new ResponseEntity<>(fgPacking, HttpStatus.OK);
    }

    //Get BulkFgPacking
    @ApiOperation(response = FgPacking.class, value = "Get FgPacking")
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getBulkFgPacking(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                                @RequestParam String plantId, @RequestParam String languageId,
                                                @RequestParam String warehouseId){
        List<FgPacking> fgPacking = fgPackingService.getBulkFgPacking(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(fgPacking, HttpStatus.OK);
    }


    //Create FgPacking
    @ApiOperation(response = FgPacking.class, value = "Create FgPacking")
    @PostMapping("")
    public ResponseEntity<?> createFgPacking(@RequestBody List<FgPacking> addFgPacking, @RequestParam String loginUserID){
        List<FgPacking> fgPacking = fgPackingService.createFgPacking(addFgPacking, loginUserID);
        return new ResponseEntity<>(fgPacking, HttpStatus.OK);
    }

    //Update FgPacking
    @ApiOperation(response = FgPacking.class, value = "Patch FgPacking")
    @PatchMapping("{productionOrderNo}")
    public ResponseEntity<?> updateFgPacking(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                               @RequestParam String languageId, @RequestParam String plantId,
                                               @RequestParam String warehouseId, @RequestParam String loginUserID,
                                               @Valid @RequestBody List<FgPacking> modifyFgPacking ){
        List<FgPacking> updateFgPacking = fgPackingService.updateFgPacking(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, modifyFgPacking);
        return new ResponseEntity<>(updateFgPacking, HttpStatus.OK);
    }

    //Delete FgPacking
    @ApiOperation(response = FgPacking.class, value = "Delete FgPacking")
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteFgPacking(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                               @RequestParam String plantId, @RequestParam String languageId,
                                               @RequestParam String warehouseId, @RequestParam String loginUserID){
        fgPackingService.deleteFgPacking(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Find FgPacking
    @ApiOperation(response = FgPacking.class, value = "Find FgPacking")
    @PostMapping("/findFgPacking")
    public Stream<FgPacking> findFgPacking(@RequestBody SearchFgPacking searchFgPacking) throws Exception{
        return fgPackingService.findFgPacking(searchFgPacking);
    }
}
