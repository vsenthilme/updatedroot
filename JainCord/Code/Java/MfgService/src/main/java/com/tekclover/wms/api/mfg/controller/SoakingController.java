package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.soaking.SearchSoaking;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.service.SoakingService;
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
@Api(tags = {"Soaking"}, value = "Soaking  Operations related to SoakingController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Soaking ", description = "Operations related to Soaking")})
@RequestMapping("/soaking")
@RestController
public class SoakingController {

    @Autowired
    SoakingService soakingService;

    //-----------------------------------------Soaking Controller------------------------------------------------------

    //Get Soaking
    @ApiOperation(response = Soaking.class, value = "Get a Soaking")
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getSoaking(@PathVariable String productionOrderNo , @RequestParam String languageId,
                                        @RequestParam String companyCodeId, @RequestParam String plantId,
                                        @RequestParam String warehouseId, @RequestParam Long productionOrderLineNo,
                                        @RequestParam String recipeId, @RequestParam String operationNumber,
                                        @RequestParam String itemCode){
        Soaking soaking = soakingService.getSoaking(languageId, companyCodeId, plantId, warehouseId, productionOrderNo, productionOrderLineNo, recipeId, operationNumber, itemCode);
        return new ResponseEntity<>(soaking, HttpStatus.OK);
    }

    //Get BulkSoaking
    @ApiOperation(response = Soaking.class, value = "Get BulkSoaking")
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getBulkSoaking(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                            @RequestParam String plantId, @RequestParam String languageId,
                                            @RequestParam String warehouseId){
        List<Soaking> soaking = soakingService.getBulkSoaking(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(soaking, HttpStatus.OK);
    }

    //Create Soaking
    @ApiOperation(response = Soaking.class, value = "Create Soaking")
    @PostMapping("")
    public ResponseEntity<?> createSoaking(@RequestBody List<Soaking> addSoaking, @RequestParam String loginUserID){
        List<Soaking> soaking = soakingService.createSoaking(addSoaking, loginUserID);
        return new ResponseEntity<>(soaking, HttpStatus.OK);
    }

    //Update Soaking
    @ApiOperation(response = Soaking.class, value = "Patch Soaking")
    @PatchMapping("{productionOrderNo}")
    public ResponseEntity<?> updateSoaking(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                           @RequestParam String languageId, @RequestParam String plantId,
                                           @RequestParam String warehouseId, @RequestParam String loginUserID,
                                           @Valid @RequestBody List<Soaking> modifySoaking ){
        List<Soaking> updateSoaking = soakingService.updateSoaking(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, modifySoaking);
        return new ResponseEntity<>(updateSoaking, HttpStatus.OK);
    }

    //Delete Soaking
    @ApiOperation(response = Soaking.class, value = "Delete Soaking")
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteSoaking(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                           @RequestParam String plantId, @RequestParam String languageId,
                                           @RequestParam String warehouseId, @RequestParam String loginUserID){
        soakingService.deleteSoaking(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Find Soaking
    @ApiOperation(response = Soaking.class, value = "Find Soaking")
    @PostMapping("/findSoaking")
    public Stream<Soaking> findSoaking(@RequestBody SearchSoaking searchSoaking) throws Exception{
        return soakingService.findSoaking(searchSoaking);
    }
}
