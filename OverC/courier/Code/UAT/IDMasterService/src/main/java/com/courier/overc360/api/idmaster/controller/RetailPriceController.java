package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.retailPriceList.AddRetailPrice;
import com.courier.overc360.api.idmaster.primary.model.retailPriceList.RetailPrice;
import com.courier.overc360.api.idmaster.replica.model.retailPriceList.FindRetailPrice;
import com.courier.overc360.api.idmaster.replica.model.retailPriceList.ReplicaRetailPrice;
import com.courier.overc360.api.idmaster.service.RetailPriceService;
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
@Api(tags = {"RetailPrice"}, value = "RetailPrice operations related to RetailPriceController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "RetailPrice", description = "Operations related to RetailPrice")})
@RequestMapping("/retailPrice")
@RestController
public class RetailPriceController {

    @Autowired
    private RetailPriceService retailPriceService;


    // Create RetailPrice
    @ApiOperation(response = RetailPrice.class, value = "Create RetailPrice") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postRetailPrice(@Valid @RequestBody List<AddRetailPrice> addRetailPrices, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<RetailPrice> createRetailPrice = retailPriceService.createRetailPrice(addRetailPrices, loginUserID);
        return new ResponseEntity<>(createRetailPrice, HttpStatus.OK);
    }

    // Update RetailPrice List
    @ApiOperation(response = RetailPrice.class, value = "Update RetailPrice List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchRetailPrice(@RequestParam String loginUserID, @RequestBody List<AddRetailPrice> updateRetailPrice)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<RetailPrice> updatedRetailPrice = retailPriceService.updateRetailPrice(updateRetailPrice, loginUserID);
        return new ResponseEntity<>(updatedRetailPrice, HttpStatus.OK);
    }

    // Delete RetailPrice List
    @ApiOperation(response = RetailPrice.class, value = "Delete Retail Price") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteRetailPrice(@RequestBody List<RetailPrice> deleteRetailPrice, @RequestParam String loginUserID) {
        retailPriceService.deleteRetailPrice(deleteRetailPrice, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All RetailPrice Details
    @ApiOperation(response = ReplicaRetailPrice.class, value = "Get all RetailPrice details") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllRetailPrice() {
        List<ReplicaRetailPrice> replicaRetailPrice = retailPriceService.getAllRetailList();
        return new ResponseEntity<>(replicaRetailPrice, HttpStatus.OK);
    }


    // Get Retail Price
    @ApiOperation(response = ReplicaRetailPrice.class, value = "Get a Retail Price") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getRetailPrice(@RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String partnerId, @RequestParam Long lineNo) {

        ReplicaRetailPrice replicaRetailPrice = retailPriceService.getRetailPriceId(languageId, companyId, partnerId, lineNo);
        return new ResponseEntity<>(replicaRetailPrice, HttpStatus.OK);
    }

    // Find Retail price
    @ApiOperation(response = ReplicaRetailPrice.class, value = "Find Retail Price") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findRetailPrice(@RequestBody FindRetailPrice findRetailPrice) throws Exception {
        List<ReplicaRetailPrice> findRetails = retailPriceService.findRetail(findRetailPrice);
        return new ResponseEntity<>(findRetails, HttpStatus.OK);
    }


}
