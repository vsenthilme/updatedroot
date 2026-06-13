package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.consignor.AddConsignor;
import com.courier.overc360.api.idmaster.primary.model.consignor.Consignor;
import com.courier.overc360.api.idmaster.primary.model.consignor.ConsignorDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.consignor.UpdateConsignor;
import com.courier.overc360.api.idmaster.replica.model.consignor.FindConsignor;
import com.courier.overc360.api.idmaster.replica.model.consignor.ReplicaConsignor;
import com.courier.overc360.api.idmaster.service.ConsignorService;
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

@Slf4j
@Validated
@Api(tags = {"Consignor"}, value = "Consignor Operations related to ConsignorController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Consignor", description = "Operations related to Consignor")})
@RequestMapping("/consignor")
@RestController
public class ConsignorController {

    @Autowired
    ConsignorService consignorService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------*/

    // Create Consignor
    @ApiOperation(response = Consignor.class, value = "Create new Consignor") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postConsignor(@Valid @RequestBody AddConsignor addConsignor, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Consignor newConsignor = consignorService.createConsignor(addConsignor, loginUserID);
        return new ResponseEntity<>(newConsignor, HttpStatus.OK);
    }

    // Update Consignor
    @ApiOperation(response = Consignor.class, value = "Update Consignor") // label for swagger
    @PatchMapping("/{consignorId}")
    public ResponseEntity<?> patchConsignor(@PathVariable String consignorId, @RequestParam String languageId, @RequestParam String subProductId,
                                            @RequestParam String loginUserID, @RequestParam String companyId, @RequestParam String productId,
                                            @RequestParam String customerId, @RequestParam String subProductValue, @Valid @RequestBody UpdateConsignor updateConsignor)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Consignor updatedConsignor = consignorService.updateConsignor(languageId, companyId, subProductId,
                subProductValue, productId, customerId, consignorId, updateConsignor, loginUserID);
        return new ResponseEntity<>(updatedConsignor, HttpStatus.OK);
    }

    // Delete Consignor
    @ApiOperation(response = Consignor.class, value = "Delete Consignor") // label for swagger
    @DeleteMapping("/{consignorId}")
    public ResponseEntity<?> deleteConsignor(@PathVariable String consignorId, @RequestParam String languageId, @RequestParam String companyId,
                                             @RequestParam String subProductId, @RequestParam String productId, @RequestParam String customerId,
                                             @RequestParam String subProductValue, @RequestParam String loginUserID) {
        consignorService.deleteConsignor(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create Consignors - bulk
    @ApiOperation(response = Consignor.class, value = "Create new Consignors - bulk") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postConsignorBulk(@Valid @RequestBody List<AddConsignor> addConsignorList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Consignor> newConsignors = consignorService.createConsignorBulk(addConsignorList, loginUserID);
        return new ResponseEntity<>(newConsignors, HttpStatus.OK);
    }

    // Update Consignors - bulk
    @ApiOperation(response = Consignor.class, value = "Update Consignors - bulk") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchConsignorBulk(@Valid @RequestBody List<UpdateConsignor> updateConsignorList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Consignor> updatedConsignors = consignorService.updateConsignorBulk(updateConsignorList, loginUserID);
        return new ResponseEntity<>(updatedConsignors, HttpStatus.OK);
    }

    // Delete Consignors - bulk
    @ApiOperation(response = Consignor.class, value = "Delete Consignors - bulk") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteConsignorBulk(@Valid @RequestBody List<ConsignorDeleteInput> consignorDeleteInputs, @RequestParam String loginUserID) {
        consignorService.deleteConsignorBulk(consignorDeleteInputs, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------*/

    // Get All Consignor Details
    @ApiOperation(response = ReplicaConsignor.class, value = "Get all Consignor Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllConsignorDetails() {
        List<ReplicaConsignor> consignorList = consignorService.getAllConsignors();
        return new ResponseEntity<>(consignorList, HttpStatus.OK);
    }

    // Get Consignor
    @ApiOperation(response = ReplicaConsignor.class, value = "Get a Consignor") // label for swagger
    @GetMapping("/{consignorId}")
    public ResponseEntity<?> getConsignor(@PathVariable String consignorId, @RequestParam String languageId, @RequestParam String companyId,
                                          @RequestParam String subProductId, @RequestParam String subProductValue,
                                          @RequestParam String productId, @RequestParam String customerId) {
        ReplicaConsignor dbConsignor = consignorService.getConsignorReplica(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId);
        return new ResponseEntity<>(dbConsignor, HttpStatus.OK);
    }

    // Find Consignors
    @ApiOperation(response = ReplicaConsignor.class, value = "Find Consignor") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findConsignors(@Valid @RequestBody FindConsignor findConsignor) throws Exception {
        List<ReplicaConsignor> consignorList = consignorService.findConsignors(findConsignor);
        return new ResponseEntity<>(consignorList, HttpStatus.OK);
    }

}
