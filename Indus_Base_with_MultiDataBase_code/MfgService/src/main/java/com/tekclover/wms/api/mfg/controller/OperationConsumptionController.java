package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.operation.SearchOperationConsumption;
import com.tekclover.wms.api.mfg.service.OperationConsumptionService;
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
@Api(tags = {"OperationConsumption"}, value = "OperationConsumption  Operations related to OperationConsumptionController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "OperationConsumption ", description = "Operations related to OperationConsumption")})
@RequestMapping("/operationconsumption")
@RestController
public class OperationConsumptionController {

    @Autowired
    OperationConsumptionService operationConsumptionService;

    @ApiOperation(response = OperationConsumption.class, value = "Get a OperationConsumption") // label for swagger 
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getOperationConsumption(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                              @RequestParam String languageId, @RequestParam String warehouseId, 
                                              @RequestParam Long productionOrderLineNo, @RequestParam String itemCode) {
        OperationConsumption operationconsumption = operationConsumptionService.getOperationConsumption(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, productionOrderLineNo, itemCode);
        return new ResponseEntity<>(operationconsumption, HttpStatus.OK);
    }

    @ApiOperation(response = OperationConsumption.class, value = "Get a OperationConsumptions") // label for swagger 
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getOperationConsumption(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                              @RequestParam String languageId, @RequestParam String warehouseId) {
        List<OperationConsumption> operationconsumption = operationConsumptionService.getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(operationconsumption, HttpStatus.OK);
    }

    @ApiOperation(response = OperationConsumption.class, value = "Search OperationConsumption") // label for swagger
    @PostMapping("/findOperationConsumption")
    public Stream<OperationConsumption> findOperationConsumption(@RequestBody SearchOperationConsumption searchOperationConsumption)
            throws Exception {
        return operationConsumptionService.findOperationConsumption(searchOperationConsumption);
    }

    @ApiOperation(response = OperationConsumption.class, value = "Create OperationConsumption") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postOperationConsumption(@Valid @RequestBody List<OperationConsumption> newOperationConsumption, @RequestParam String loginUserID) {
        List<OperationConsumption> createdOperationConsumption = operationConsumptionService.createBatchOperationConsumption(newOperationConsumption, loginUserID);
        return new ResponseEntity<>(createdOperationConsumption, HttpStatus.OK);
    }

    @ApiOperation(response = OperationConsumption.class, value = "Patch OperationConsumption") // label for swagger
    @PatchMapping("/{productionOrderNo}")
    public ResponseEntity<?> patchOperationConsumption(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID,
                                                @Valid @RequestBody List<OperationConsumption> modifyOperationConsumption) {
        List<OperationConsumption> createdOperationConsumption = operationConsumptionService.updateOperationConsumption(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID, modifyOperationConsumption);
        return new ResponseEntity<>(createdOperationConsumption, HttpStatus.OK);
    }

    @ApiOperation(response = OperationConsumption.class, value = "Patch OperationConsumption after picklist update qty")    // label for swagger
    @GetMapping("/update/{productionOrderNo}")
    public ResponseEntity<?> patchOperationConsumptionV2(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                         @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String orderType,
                                                         @RequestParam String batchNumber, @RequestParam String parentProductionOrderNo) {
        operationConsumptionService.updateOperationConsumption(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber, parentProductionOrderNo, orderType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(response = OperationConsumption.class, value = "Delete OperationConsumption") // label for swagger
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteOperationConsumption(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                 @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID) {
        operationConsumptionService.deleteOperationConsumption(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}