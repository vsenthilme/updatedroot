package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.cooking.Cooking;
import com.tekclover.wms.api.mfg.model.cooking.SearchCooking;
import com.tekclover.wms.api.mfg.service.CookingService;
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
@Api(tags = {"Cooking"}, value = "Cooking  Operations related to CookingController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Cooking", description = "Operations related to Cooking")})
@RequestMapping("/cooking")
@RestController
public class CookingController {

    @Autowired
    CookingService cookingService;

    @ApiOperation(response = Cooking.class, value = "Get a Cooking")
    @GetMapping("")
    public ResponseEntity<?> geCooking(@RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String plantId, @RequestParam String receipeId, @RequestParam String operationNumber, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode) {
        Cooking cooking = cookingService.getCooking(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
        return new ResponseEntity<>(cooking, HttpStatus.OK);
    }

    @ApiOperation(response = Cooking.class, value = "Search Cooking")
    @PostMapping("/findCooking")
    public Stream<Cooking> findCooking(@RequestBody SearchCooking searchCooking) throws Exception {
        return cookingService.findCooking(searchCooking);
    }

    @ApiOperation(response = Cooking.class, value = "Create Cooking Batch")
    @PostMapping("/batch")
    public ResponseEntity<?> postCooking(@Valid @RequestBody List<Cooking> newCooking, @RequestParam String loginUserID) {
        List<Cooking> createdCooking = cookingService.createCookingBatch(newCooking, loginUserID);
        return new ResponseEntity<>(createdCooking, HttpStatus.OK);
    }

    @ApiOperation(response = Cooking.class, value = "Patch Cooking Batch")
    @PatchMapping("/batch")
    public ResponseEntity<?> patchCooking(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @Valid @RequestBody List<Cooking> modifyCooking, @RequestParam String loginUserID) {
        List<Cooking> updatedCooking = cookingService.updateCookingBatch(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID, modifyCooking);
        return new ResponseEntity<>(updatedCooking, HttpStatus.OK);
    }

    @ApiOperation(response = Cooking.class, value = "Delete Cooking")
    @DeleteMapping("")
    public ResponseEntity<?> deleteCooking(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @RequestParam String loginUserID) {
        cookingService.deleteCooking(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}