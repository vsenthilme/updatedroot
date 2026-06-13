package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.peeling.Peeling;
import com.tekclover.wms.api.mfg.model.peeling.SearchPeeling;
import com.tekclover.wms.api.mfg.service.PeelingService;
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
@Api(tags = {"Peeling"}, value = "Peeling  Operations related to PeelingController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Peeling ", description = "Operations related to Peeling")})
@RequestMapping("/peeling")
@RestController
public class PeelingController {

    @Autowired
    PeelingService peelingService;

    //-------------------------------------------------Peeling Controller-------------------------------------------

    //Get Peeling
    @ApiOperation(response = Peeling.class, value = "Get a Peeling") // label for swagger
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getPeeling(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                        @RequestParam String languageId, @RequestParam String warehouseId,
                                        @RequestParam Long productionOrderLineNo, @RequestParam String recipeId,
                                        @RequestParam String operationNumber, @RequestParam String itemCode) {
        Peeling peeling = peelingService.getPeeling(languageId, companyCodeId, plantId, warehouseId, productionOrderNo, productionOrderLineNo, recipeId, operationNumber, itemCode);
        return new ResponseEntity<>(peeling, HttpStatus.OK);
    }

    //Get BulkPeeling record
    @ApiOperation(response = Peeling.class, value = "Get BulkPeeling") // label for swagger
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getBulkPeeling(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                            @RequestParam String plantId, @RequestParam String languageId,
                                            @RequestParam String warehouseId) {
        List<Peeling> peeling = peelingService.getBulkPeeling(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(peeling, HttpStatus.OK);
    }

    //create Peeling
    @ApiOperation(response = Peeling.class, value = "Create Peeling")
    @PostMapping("")
    public ResponseEntity<?> createSorting(@Valid @RequestBody List<Peeling> addPeeling , @RequestParam String loginUserID){
        List<Peeling> createPeeling = peelingService.createPeeling(addPeeling, loginUserID);
        return new ResponseEntity<>(createPeeling, HttpStatus.OK);
    }

    //Update Peeling
    @ApiOperation(response = Peeling.class, value = "Patch Peeling")
    @PatchMapping("/{productionOrderNo}")
    public ResponseEntity<?> updatePeeling(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                           @RequestParam String languageId, @RequestParam String plantId,
                                           @RequestParam String warehouseId, @RequestParam String loginUserID,
                                           @Valid @RequestBody List<Peeling> modifyPeeling){
        List<Peeling> updatePeeling = peelingService.updatePeeling(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, modifyPeeling);
        return new ResponseEntity<>(updatePeeling, HttpStatus.OK);
    }

    //Delete Peeling
    @ApiOperation(response = Peeling.class, value = "Delete Peeling")
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deletePeeling(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                           @RequestParam String plantId, @RequestParam String languageId,
                                           @RequestParam String warehouseId, @RequestParam String loginUserID){
        peelingService.deletePeeling(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Find Peeling
    @ApiOperation(response = Peeling.class, value = "Find Peeling")
    @PostMapping("/findPeeling")
    public Stream<Peeling> findPeeling(@RequestBody SearchPeeling searchPeeling) throws Exception{
        return peelingService.findPeeling(searchPeeling);
    }
}
