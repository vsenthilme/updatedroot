package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.fulFillmentPrice.AddFulfillmentPrice;
import com.courier.overc360.api.idmaster.primary.model.fulFillmentPrice.FulfillmentPrice;
import com.courier.overc360.api.idmaster.replica.model.fulFillmentPrice.FindFulfillmentPrice;
import com.courier.overc360.api.idmaster.replica.model.fulFillmentPrice.ReplicaFulfillmentPrice;
import com.courier.overc360.api.idmaster.service.FulfillmentPriceService;
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
@Api(tags = {"FulfillmentPrice"}, value = "FulfillmentPrice operations related to FulfillmentPriceController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "FulfillmentPrice", description = "Operations related to FulfillmentPrice")})
@RequestMapping("/fulfillmentPrice")
@RestController
public class FulfillmentPriceController {


    @Autowired
    private FulfillmentPriceService fulfillmentPriceService;


    // Create Fulfillment Price
    @ApiOperation(response = FulfillmentPrice.class, value = "Create Fulfillment Price") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postFulfillmentPrice(@Valid @RequestBody List<AddFulfillmentPrice> addFulfillmentPrices, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<FulfillmentPrice> createFulfillment = fulfillmentPriceService.createFulfillmentPrice(addFulfillmentPrices, loginUserID);
        return new ResponseEntity<>(createFulfillment, HttpStatus.OK);
    }

    // Update Fulfillment Price List
    @ApiOperation(response = FulfillmentPrice.class, value = "Update Fulfillment Price List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchFulfillmentList(@RequestParam String loginUserID, @RequestBody List<AddFulfillmentPrice> updateFulfillment)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<FulfillmentPrice> updatedFulfillment = fulfillmentPriceService.updateFulfillment(updateFulfillment, loginUserID);
        return new ResponseEntity<>(updatedFulfillment, HttpStatus.OK);
    }

    // Delete Fulfillment Price List
    @ApiOperation(response = FulfillmentPrice.class, value = "Delete Fulfillment Price") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteFulfillment(@RequestBody List<FulfillmentPrice> deleteFulfillment, @RequestParam String loginUserID) {
        fulfillmentPriceService.deleteFulfillmentList(deleteFulfillment, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Fulfillment Details
    @ApiOperation(response = ReplicaFulfillmentPrice.class, value = "Get all Fulfillment Details") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllFulfillment() {
        List<ReplicaFulfillmentPrice> replicaFulfillment = fulfillmentPriceService.getAllFulfillment();
        return new ResponseEntity<>(replicaFulfillment, HttpStatus.OK);
    }

    // Get Fulfillment Price
    @ApiOperation(response = ReplicaFulfillmentPrice.class, value = "Get a Fulfillment Price") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getFulfillment(@RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String partnerId, @RequestParam Long lineNo) {

        ReplicaFulfillmentPrice replicaFulfillmentPrice = fulfillmentPriceService.getFulfillmentid(languageId, companyId, partnerId, lineNo);
        return new ResponseEntity<>(replicaFulfillmentPrice, HttpStatus.OK);
    }

    // Find Fulfillment Price
    @ApiOperation(response = ReplicaFulfillmentPrice.class, value = "Find Fulfillment Price") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findFulfillment(@RequestBody FindFulfillmentPrice findFulfillmentPrice) throws Exception {
        List<ReplicaFulfillmentPrice> findFulfillment = fulfillmentPriceService.findFulfillmentList(findFulfillmentPrice);
        return new ResponseEntity<>(findFulfillment, HttpStatus.OK);
    }

}
