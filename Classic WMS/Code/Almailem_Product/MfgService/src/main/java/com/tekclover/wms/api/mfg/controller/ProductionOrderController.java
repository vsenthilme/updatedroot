package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.prodcutionorder.ProductionOrder;
import com.tekclover.wms.api.mfg.model.prodcutionorder.ProductionOrderOutput;
import com.tekclover.wms.api.mfg.model.operation.SearchOperationHeader;
import com.tekclover.wms.api.mfg.service.ProductionOrderService;
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
import java.util.Optional;

@Slf4j
@Validated
@Api(tags = {"ProductionOrder"}, value = "ProductionOrder  Operations related to ProductionOrderController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ProductionOrder ", description = "Operations related to ProductionOrder")})
@RequestMapping("/productionheader")
@RestController
public class ProductionOrderController {

    @Autowired
    ProductionOrderService productionOrderService;

    @ApiOperation(response = ProductionOrderOutput.class, value = "Get a ProductionOrder") // label for swagger
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getProductionOrder(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                @RequestParam String languageId, @RequestParam String warehouseId) {
        ProductionOrderOutput productionorder = productionOrderService.getProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(productionorder, HttpStatus.OK);
    }

    @ApiOperation(response = ProductionOrderOutput.class, value = "Get a ProductionOrder") // label for swagger
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getProductionOrder(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String batchNumber) {
        ProductionOrder productionorder = productionOrderService.getProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(productionorder, HttpStatus.OK);
    }

    @ApiOperation(response = ProductionOrderOutput.class, value = "Search ProductionOrder") // label for swagger
    @PostMapping("/findProductionOrder")
    public List<ProductionOrderOutput> findProductionOrder(@RequestBody SearchOperationHeader searchProductionOrder)
            throws Exception {
        return productionOrderService.findProductionOrder(searchProductionOrder);
    }

    @ApiOperation(response = ProductionOrderOutput.class, value = "Create ProductionOrder Batch") // label for swagger
    @PostMapping("/batch")
    public ResponseEntity<?> postProductionOrderBatch(@Valid @RequestBody List<ProductionOrder> newProductionOrder, @RequestParam String loginUserID) {
        List<ProductionOrderOutput> createdProductionOrder = productionOrderService.createProductionOrderBatch(newProductionOrder, loginUserID);
        return new ResponseEntity<>(createdProductionOrder, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Create SFG ProductionOrder Batch") // label for swagger
    @PostMapping("/sfgProductionOrder")
    public ResponseEntity<?> postSFGProductionOrderBatch(@Valid @RequestBody List<ProductionOrder> newProductionOrder, @RequestParam String loginUserID) {
        Boolean createdProductionOrder = productionOrderService.createSFGProductionOrderBatch(newProductionOrder, loginUserID);
        return new ResponseEntity<>(createdProductionOrder, HttpStatus.OK);
    }

    @ApiOperation(response = ProductionOrder.class, value = "Patch ProductionOrder Batch") // label for swagger
    @PatchMapping("/patch")
    public ResponseEntity<?> patchProductionOrderBatch(@RequestParam String companyCodeId, @RequestParam String plantId,
                                                       @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID,
                                                       @Valid @RequestBody List<ProductionOrder> modifyProductionOrder) {
        List<ProductionOrderOutput> createdProductionOrder = productionOrderService.updateProductionOrderBatch(companyCodeId, plantId, languageId, warehouseId, loginUserID, modifyProductionOrder);
        return new ResponseEntity<>(createdProductionOrder, HttpStatus.OK);
    }

    @ApiOperation(response = ProductionOrder.class, value = "Delete ProductionOrder") // label for swagger
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteProductionOrder(@PathVariable String productionOrderNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                   @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID) {
        productionOrderService.deleteProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}