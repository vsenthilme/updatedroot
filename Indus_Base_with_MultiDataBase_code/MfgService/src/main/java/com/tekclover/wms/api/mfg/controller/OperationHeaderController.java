package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.operation.OperationHeader;
import com.tekclover.wms.api.mfg.model.operation.SearchOperationHeader;
import com.tekclover.wms.api.mfg.service.OperationHeaderService;
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
@Api(tags = {"OperationHeader"}, value = "OperationHeader  Operations related to OperationHeaderController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "OperationHeader ", description = "Operations related to OperationHeader")})
@RequestMapping("/operationheader")
@RestController
public class OperationHeaderController {

    @Autowired
    OperationHeaderService operationHeaderService;

    @ApiOperation(response = OperationHeader.class, value = "Get a OperationHeader") // label for swagger 
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getOperationHeader(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                @RequestParam String languageId, @RequestParam String warehouseId) {
        OperationHeader operationheader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(operationheader, HttpStatus.OK);
    }

    @ApiOperation(response = OperationHeader.class, value = "Search OperationHeader") // label for swagger
    @PostMapping("/findOperationHeader")
    public Stream<OperationHeader> findOperationHeader(@RequestBody SearchOperationHeader searchOperationHeader)
            throws Exception {
        return operationHeaderService.findOperationHeader(searchOperationHeader);
    }

    @ApiOperation(response = OperationHeader.class, value = "Create OperationHeader") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postOperationHeader(@Valid @RequestBody OperationHeader newOperationHeader, @RequestParam String loginUserID) {
        OperationHeader createdOperationHeader = operationHeaderService.createOperationHeader(newOperationHeader, loginUserID);
        return new ResponseEntity<>(createdOperationHeader, HttpStatus.OK);
    }

    @ApiOperation(response = OperationHeader.class, value = "Patch OperationHeader") // label for swagger
    @PatchMapping("/{productionOrderNo}")
    public ResponseEntity<?> patchOperationHeader(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                  @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID,
                                                  @Valid @RequestBody OperationHeader modifyOperationHeader) {
        OperationHeader createdOperationHeader = operationHeaderService.updateOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID, modifyOperationHeader);
        return new ResponseEntity<>(createdOperationHeader, HttpStatus.OK);
    }

    @ApiOperation(response = OperationHeader.class, value = "Create OperationHeader Batch") // label for swagger
    @PostMapping("/batch")
    public ResponseEntity<?> postOperationHeaderBatch(@Valid @RequestBody List<OperationHeader> newOperationHeader, @RequestParam String loginUserID) {
        List<OperationHeader> createdOperationHeader = operationHeaderService.createOperationHeaderBatch(newOperationHeader, loginUserID);
        return new ResponseEntity<>(createdOperationHeader, HttpStatus.OK);
    }

    @ApiOperation(response = OperationHeader.class, value = "Patch OperationHeader Batch") // label for swagger
    @PatchMapping("/batch")
    public ResponseEntity<?> patchOperationHeaderBatch(@RequestParam String companyCodeId, @RequestParam String plantId,
                                                       @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID,
                                                       @Valid @RequestBody List<OperationHeader> modifyOperationHeader) {
        List<OperationHeader> createdOperationHeader = operationHeaderService.updateOperationHeaderBatch(companyCodeId, plantId, languageId, warehouseId, loginUserID, modifyOperationHeader);
        return new ResponseEntity<>(createdOperationHeader, HttpStatus.OK);
    }

    @ApiOperation(response = OperationHeader.class, value = "Delete OperationHeader") // label for swagger
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteOperationHeader(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                   @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID) {
        operationHeaderService.deleteOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}