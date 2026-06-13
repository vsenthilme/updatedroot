package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.operation.OperationLine;
import com.tekclover.wms.api.mfg.model.operation.SearchOperationLine;
import com.tekclover.wms.api.mfg.service.OperationLineService;
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
@Api(tags = {"OperationLine"}, value = "OperationLine  Operations related to OperationLineController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "OperationLine ", description = "Operations related to OperationLine")})
@RequestMapping("/operationline")
@RestController
public class OperationLineController {

    @Autowired
    OperationLineService operationLineService;

    @ApiOperation(response = OperationLine.class, value = "Get a OperationLine") // label for swagger 
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getOperationLine(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                              @RequestParam String languageId, @RequestParam String warehouseId,
                                              @RequestParam Long productionOrderLineNo, @RequestParam String itemCode) {
        OperationLine operationline = operationLineService.getOperationLine(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, productionOrderLineNo, itemCode);
        return new ResponseEntity<>(operationline, HttpStatus.OK);
    }

    @ApiOperation(response = OperationLine.class, value = "Get a OperationLines") // label for swagger
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getOperationLine(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                              @RequestParam String languageId, @RequestParam String warehouseId) {
        List<OperationLine> operationline = operationLineService.getOperationLines(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(operationline, HttpStatus.OK);
    }

    @ApiOperation(response = OperationLine.class, value = "Search OperationLine") // label for swagger
    @PostMapping("/findOperationLine")
    public Stream<OperationLine> findOperationLine(@RequestBody SearchOperationLine searchOperationLine)
            throws Exception {
        return operationLineService.findOperationLine(searchOperationLine);
    }

    @ApiOperation(response = OperationLine.class, value = "Create OperationLine") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postOperationLine(@Valid @RequestBody List<OperationLine> newOperationLine, @RequestParam String loginUserID) {
        List<OperationLine> createdOperationLine = operationLineService.createOperationLine(newOperationLine, loginUserID);
        return new ResponseEntity<>(createdOperationLine, HttpStatus.OK);
    }

    @ApiOperation(response = OperationLine.class, value = "Patch OperationLine") // label for swagger
    @PatchMapping("/{productionOrderNo}")
    public ResponseEntity<?> patchOperationLine(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID,
                                                @Valid @RequestBody List<OperationLine> modifyOperationLine) {
        List<OperationLine> createdOperationLine = operationLineService.updateOperationLine(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID, modifyOperationLine);
        return new ResponseEntity<>(createdOperationLine, HttpStatus.OK);
    }

    @ApiOperation(response = OperationLine.class, value = "Delete OperationLine") // label for swagger
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteOperationLine(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                 @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID) {
        operationLineService.deleteOperationLine(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}