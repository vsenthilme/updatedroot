package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.customscosting.*;
import com.courier.overc360.api.midmile.replica.model.customscosting.FindCustomsCosting;
import com.courier.overc360.api.midmile.replica.model.customscosting.ReplicaCustomsCosting;
import com.courier.overc360.api.midmile.service.CustomsCostingService;
import com.opencsv.exceptions.CsvException;
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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Validated
@Api(tags = {"CustomsCosting"}, value = "CustomsCosting Operations related to CustomsCostingController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "CustomsCosting", description = "Operations related to CustomsCosting")})
@RequestMapping("/customsCosting")
@RestController
public class CustomsCostingController {

    @Autowired
    private CustomsCostingService customsCostingService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = CustomsCosting.class, value = "Create New CustomsCosting")
    @PostMapping("")
    public ResponseEntity<?> createCustomsCosting(@Valid @RequestBody List<CustomsCosting> addCustomsCosting, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<CustomsCosting> newCustomsCosting = customsCostingService.createCustomsCosting(addCustomsCosting, loginUserID);
        return new ResponseEntity<>(newCustomsCosting, HttpStatus.OK);
    }

    //Create
    @ApiOperation(response = CustomsCosting.class, value = "Create New CustomsCosting for CostText")
    @PostMapping("/cost/text")
    public ResponseEntity<?> createCustomsCostingForCostText(@Valid @RequestBody List<CustomsCosting> addCustomsCosting, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<CustomsCosting> newCustomsCosting = customsCostingService.createCustomCostingForCostText(addCustomsCosting, loginUserID);
        return new ResponseEntity<>(newCustomsCosting, HttpStatus.OK);
    }

    //Update
    @ApiOperation(response = CustomsCosting.class, value = "Update CustomsCosting")
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchCustomsCosting(@RequestParam String loginUserID,@Valid @RequestBody List<CustomsCosting> updateCustomsCosting)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<CustomsCosting> updatedCustomsCosting = customsCostingService.updateCustomsCosting(updateCustomsCosting, loginUserID);
        return new ResponseEntity<>(updatedCustomsCosting, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = CustomsCosting.class, value = "Delete CustomsCosting")
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteCustomsCosting(@Valid @RequestBody List<CustomsCosting> customsCostingList ,@RequestParam String loginUserID) {
        customsCostingService.deleteCustomsCosting(customsCostingList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Delete
    @ApiOperation(response = CustomsCosting.class, value = "Delete CustomsCosting List")
    @PostMapping("/delete/multiple")
    public ResponseEntity<?> deleteCustomsCostingMultiple(@Valid @RequestBody List<CustomsCosting> costCenter,
                                                          @RequestParam String loginUserID) {
        customsCostingService.deleteCustomCostingList(costCenter, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All CustomsCosting Details
    @ApiOperation(response = ReplicaCustomsCosting.class, value = "Get all CustomsCosting Details")
    @GetMapping("")
    public ResponseEntity<?> getAllCustomsCostingDetails() {
        List<ReplicaCustomsCosting> customsCostingList = customsCostingService.getAll();
        return new ResponseEntity<>(customsCostingList, HttpStatus.OK);
    }

    //Get CustomsCosting
    @ApiOperation(response = ReplicaCustomsCosting.class, value = "Get a CustomsCosting")
    @GetMapping("/{costCenter}")
    public ResponseEntity<?> getCustomsCosting(@PathVariable String costCenter, @RequestParam String companyId, @RequestParam String languageId
    ,@RequestParam String partnerId, @RequestParam Long lineNumber, @RequestParam Long cashNumber) {
        ReplicaCustomsCosting dbCustomsCosting = customsCostingService.getReplicaCustomsCosting(companyId, languageId, partnerId, costCenter, lineNumber, cashNumber);
        return new ResponseEntity<>(dbCustomsCosting, HttpStatus.OK);
    }

    //Find CustomsCosting
    @ApiOperation(response = ReplicaCustomsCosting.class, value = "Find CustomsCosting")
    @PostMapping("/find")
    public ResponseEntity<?> findCustomsCosting(@Valid @RequestBody FindCustomsCosting findCustomsCosting) throws Exception {
        List<ReplicaCustomsCosting> customsCostingList = customsCostingService.findCustomsCosting(findCustomsCosting);
        return new ResponseEntity<>(customsCostingList, HttpStatus.OK);
    }

    @ApiOperation(response = CustomCostingInvoice.class, value = "Customs Costing Invoice")
    @PostMapping("/findCustomsInvoice")
    public ResponseEntity<?> findCustoms(@Valid @RequestBody FindCustomInvoice findCustomInvoice) throws ExecutionException, InterruptedException {
        List<CustomCostingInvoice> customsCostingList = customsCostingService.findCustomCosting(findCustomInvoice);
        return new ResponseEntity<>(customsCostingList, HttpStatus.OK);
    }

    // Find Custom Costing Total PDF
    @ApiOperation(response = CustomCostingTotalResult.class, value = "Find Customs Costing Total")
    @PostMapping("/findCustomsCostingPdf")
    public ResponseEntity<?> findCustomsCostingPdf(@Valid @RequestBody CustomCostingTotal customCostingTotal){
        List<CustomCostingTotalResult>  customCostingTotalResults = customsCostingService.findCustomCostingTotal(customCostingTotal);
        return new ResponseEntity<>(customCostingTotalResults,HttpStatus.OK);
    }

    //==============================================Report Field Updation=============================================================
    //Approve Custom costing
    @ApiOperation(response = Optional.class, value = "Approve CustomsCosting for Report")
    @GetMapping("/approve/{partnerMasterAirWayBill}")
    public ResponseEntity<?> approveCustomsCosting(@RequestParam String companyId, @RequestParam String languageId,
                                                   @PathVariable String partnerMasterAirWayBill, @RequestParam String loginUserID) {
        customsCostingService.approveCustomsCosting(companyId, languageId, partnerMasterAirWayBill, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Approve Custom costing - Batch
    @ApiOperation(response = Optional.class, value = "Batch Approve CustomsCosting for Report")
    @PostMapping("/approve/batch")
    public ResponseEntity<?> batchApproveCustomsCosting(@RequestBody ApproveCustomCostingInput approveCustomCostingInput, @RequestParam String loginUserID) {
        customsCostingService.approveCustomsCosting(approveCustomCostingInput, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //FinanceApproval - Approve Custom costing - Batch
    @ApiOperation(response = Optional.class, value = "Batch Approve -FinanceApproval- CustomsCosting for Report")
    @PostMapping("/approve/batch/finance")
    public ResponseEntity<?> batchFinanceApprovalCustomsCosting(@RequestBody ApproveCustomCostingInput approveCustomCostingInput, @RequestParam String loginUserID) {
        customsCostingService.approveCustomsCostingFinanceApproval(approveCustomCostingInput, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}