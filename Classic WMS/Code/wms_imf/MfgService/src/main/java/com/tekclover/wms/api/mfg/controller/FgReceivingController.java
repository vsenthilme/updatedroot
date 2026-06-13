package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.fgreceiving.FgReceiving;
import com.tekclover.wms.api.mfg.model.fgreceiving.SearchFgReceiving;
import com.tekclover.wms.api.mfg.service.FgReceivingService;
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
@Api(tags = {"FgReceiving"}, value = "FgReceiving  Operations related to FgReceivingController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "FgReceiving ", description = "Operations related to FgReceiving")})
@RequestMapping("/fgReceiving")
@RestController
public class FgReceivingController {

    @Autowired
    FgReceivingService fgReceivingService;

    //-----------------------------------------FgReceiving Controller------------------------------------------------------

    //Get FgReceiving
    @ApiOperation(response = FgReceiving.class, value = "Get a FgReceiving")
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getFgReceiving(@PathVariable String productionOrderNo , @RequestParam String languageId,
                                            @RequestParam String companyCodeId, @RequestParam String plantId,
                                            @RequestParam String warehouseId, @RequestParam Long productionOrderLineNo,
                                            @RequestParam String receipeId, @RequestParam String operationNumber,
                                            @RequestParam String itemCode){
        FgReceiving fgReceiving = fgReceivingService.getFgReceiving(languageId, companyCodeId, plantId, warehouseId, productionOrderNo, productionOrderLineNo, receipeId, operationNumber, itemCode);
        return new ResponseEntity<>(fgReceiving, HttpStatus.OK);
    }

    //Get BulkFgReceiving
    @ApiOperation(response = FgReceiving.class, value = "Get FgReceiving")
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getBulkFgReceiving(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                                @RequestParam String plantId, @RequestParam String languageId,
                                                @RequestParam String warehouseId){
        List<FgReceiving> fgReceiving = fgReceivingService.getBulkFgReceiving(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(fgReceiving, HttpStatus.OK);
    }


    //Create FgReceiving
    @ApiOperation(response = FgReceiving.class, value = "Create FgReceiving")
    @PostMapping("")
    public ResponseEntity<?> createFgReceiving(@RequestBody List<FgReceiving> addFgReceiving, @RequestParam String loginUserID){
        List<FgReceiving> fgReceiving = fgReceivingService.createFgReceiving(addFgReceiving, loginUserID);
        return new ResponseEntity<>(fgReceiving, HttpStatus.OK);
    }

    //Update FgReceiving
    @ApiOperation(response = FgReceiving.class, value = "Patch FgReceiving")
    @PatchMapping("{productionOrderNo}")
    public ResponseEntity<?> updateFgReceiving(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                               @RequestParam String languageId, @RequestParam String plantId,
                                               @RequestParam String warehouseId, @RequestParam String loginUserID,
                                               @Valid @RequestBody List<FgReceiving> modifyFgReceiving ){
        List<FgReceiving> updateFgReceiving = fgReceivingService.updateFgReceiving(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, modifyFgReceiving);
        return new ResponseEntity<>(updateFgReceiving, HttpStatus.OK);
    }

    //Delete FgReceiving
    @ApiOperation(response = FgReceiving.class, value = "Delete FgReceiving")
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteFgReceiving(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                               @RequestParam String plantId, @RequestParam String languageId,
                                               @RequestParam String warehouseId, @RequestParam String loginUserID){
        fgReceivingService.deleteFgReceiving(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Find FgReceiving
    @ApiOperation(response = FgReceiving.class, value = "Find FgReceiving")
    @PostMapping("/findFgReceiving")
    public Stream<FgReceiving> findFgReceiving(@RequestBody SearchFgReceiving searchFgReceiving) throws Exception{
        return fgReceivingService.findFgReceiving(searchFgReceiving);
    }

}
