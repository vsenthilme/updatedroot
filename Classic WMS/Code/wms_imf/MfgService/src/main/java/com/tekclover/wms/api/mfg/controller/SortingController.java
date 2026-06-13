package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.sorting.SearchSorting;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import com.tekclover.wms.api.mfg.service.SortingService;
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
@Api(tags = {"Sorting"}, value = "Sorting  Operations related to SortingController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Sorting ", description = "Operations related to Sorting")})
@RequestMapping("/sorting")
@RestController
public class SortingController {

    @Autowired
    SortingService sortingService;

    //-------------------------------------------------Sorting Controller-------------------------------------------

    //Get Sorting
    @ApiOperation(response = Sorting.class, value = "Get a Sorting") // label for swagger
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getSorting(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                     @RequestParam String languageId, @RequestParam String warehouseId,
                                                     @RequestParam Long productionOrderLineNo, @RequestParam String recipeId,
                                                     @RequestParam String operationNumber, @RequestParam String itemCode) {
        Sorting sorting = sortingService.getSorting(languageId, companyCodeId, plantId, warehouseId, productionOrderNo, productionOrderLineNo, recipeId, operationNumber, itemCode);
        return new ResponseEntity<>(sorting, HttpStatus.OK);
    }

    //Get BulkSorting record
    @ApiOperation(response = Sorting.class, value = "Get BulkSorting") // label for swagger
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getBulkSorting(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                            @RequestParam String plantId, @RequestParam String languageId,
                                            @RequestParam String warehouseId) {
        List<Sorting> sorting = sortingService.getBulkSorting(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(sorting, HttpStatus.OK);
    }

    //create Sorting
    @ApiOperation(response = Sorting.class, value = "Create Sorting")
    @PostMapping("")
    public ResponseEntity<?> createSorting(@Valid @RequestBody List<Sorting> addSorting , @RequestParam String loginUserID){
        List<Sorting> createSorting = sortingService.createSorting(addSorting, loginUserID);
        return new ResponseEntity<>(createSorting, HttpStatus.OK);
    }

    //Update Sorting
    @ApiOperation(response = Sorting.class, value = "Patch Sorting")
    @PatchMapping("/{productionOrderNo}")
    public ResponseEntity<?> updateSorting(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                           @RequestParam String languageId, @RequestParam String plantId,
                                            @RequestParam String warehouseId, @RequestParam String loginUserID,
                                           @Valid @RequestBody List<Sorting> modifySorting){
        List<Sorting> updateSorting = sortingService.updateSorting(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, modifySorting);
        return new ResponseEntity<>(updateSorting, HttpStatus.OK);
    }

    //Delete Sorting
    @ApiOperation(response = Sorting.class, value = "Delete Sorting")
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteSorting(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                           @RequestParam String plantId, @RequestParam String languageId,
                                           @RequestParam String warehouseId, @RequestParam String loginUserID){
     sortingService.deleteSorting(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Find Sorting
    @ApiOperation(response = Sorting.class, value = "Find Sorting")
    @PostMapping("/findSorting")
    public Stream<Sorting> findSorting(@RequestBody SearchSorting searchSorting) throws Exception{
        return sortingService.findSorting(searchSorting);
    }
}
