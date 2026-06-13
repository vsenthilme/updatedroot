package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.diceslicechop.DiceSliceChop;
import com.tekclover.wms.api.mfg.model.diceslicechop.SearchDiceSliceChop;
import com.tekclover.wms.api.mfg.service.DiceSliceChopService;
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
@Api(tags = {"DiceSliceChop"}, value = "DiceSliceChop  Operations related to DiceSliceChopController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "DiceSliceChop ", description = "Operations related to DiceSliceChop")})
@RequestMapping("/diceSliceChop")
@RestController
public class DiceSliceChopController {

    @Autowired
    DiceSliceChopService diceSliceChopService;

    @ApiOperation(response = DiceSliceChop.class, value = "Get a DiceSliceChop")
    @GetMapping("")
    public ResponseEntity<?> getPowder(@RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String plantId, @RequestParam String receipeId, @RequestParam String operationNumber, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode) {
        DiceSliceChop diceSliceChop = diceSliceChopService.getDiceSliceChop(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
        return new ResponseEntity<>(diceSliceChop, HttpStatus.OK);
    }

    @ApiOperation(response = DiceSliceChop.class, value = "Search DiceSliceChop")
    @PostMapping("/findDiceSliceChop")
    public Stream<DiceSliceChop> findDiceSliceChop(@RequestBody SearchDiceSliceChop searchDiceSliceChop) throws Exception {
        return diceSliceChopService.findDiceSliceChop(searchDiceSliceChop);
    }

    @ApiOperation(response = DiceSliceChop.class, value = "Create DiceSliceChop Batch")
    @PostMapping("/batch")
    public ResponseEntity<?> postDiceSliceChopBatch(@Valid @RequestBody List<DiceSliceChop> newDiceSliceChop, @RequestParam String loginUserID) {
        List<DiceSliceChop> createdDiceSliceChop = diceSliceChopService.createDiceSliceChopBatch(newDiceSliceChop, loginUserID);
        return new ResponseEntity<>(createdDiceSliceChop, HttpStatus.OK);
    }

    @ApiOperation(response = DiceSliceChop.class, value = "Patch DiceSliceChop Batch")
    @PatchMapping("/batch")
    public ResponseEntity<?> patchDiceSliceChopBatch(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @Valid @RequestBody List<DiceSliceChop> modifyDiceSliceChop, @RequestParam String loginUserID) {
        List<DiceSliceChop> updatedPowder = diceSliceChopService.updateDiceSliceChopBatch(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID, modifyDiceSliceChop);
        return new ResponseEntity<>(updatedPowder, HttpStatus.OK);
    }

    @ApiOperation(response = DiceSliceChop.class, value = "Delete DiceSliceChop")
    @DeleteMapping("")
    public ResponseEntity<?> deleteDiceSliceChop(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String operationNumber, @RequestParam String receipeId, @RequestParam String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode, @RequestParam String loginUserID) {
        diceSliceChopService.deleteDiceSliceChop(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}