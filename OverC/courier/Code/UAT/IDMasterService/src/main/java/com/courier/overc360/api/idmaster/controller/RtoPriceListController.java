package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.rtopricelist.AddRtoPrice;
import com.courier.overc360.api.idmaster.primary.model.rtopricelist.RtoPriceList;
import com.courier.overc360.api.idmaster.replica.model.rtopricelist.FindRtoPrice;
import com.courier.overc360.api.idmaster.replica.model.rtopricelist.ReplicaRtoPrice;
import com.courier.overc360.api.idmaster.service.RtoPriceListService;
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
@Api(tags = {"RtoPriceList"}, value = "RtoPriceList operations related to RtoPriceListController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "RtoPriceList", description = "Operations related to RtoPriceList")})
@RequestMapping("/rtoPriceList")
@RestController
public class RtoPriceListController {


    @Autowired
    private RtoPriceListService rtoPriceListService;


    // Create RtoPriceList
    @ApiOperation(response = RtoPriceList.class, value = "Create RtoPriceList") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postRtoPriceList(@Valid @RequestBody List<AddRtoPrice> addRtoPrices, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<RtoPriceList> createRtoPrice = rtoPriceListService.createRtoPrice(addRtoPrices, loginUserID);
        return new ResponseEntity<>(createRtoPrice, HttpStatus.OK);
    }

    // Update RtoPrice List
    @ApiOperation(response = RtoPriceList.class, value = "Update RtoPrice List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchRtoPrice(@RequestParam String loginUserID, @RequestBody List<AddRtoPrice> updateRtoPrice)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<RtoPriceList> updatedRtoPrice = rtoPriceListService.updateRtoPrice(updateRtoPrice, loginUserID);
        return new ResponseEntity<>(updatedRtoPrice, HttpStatus.OK);
    }

    // Delete RtoPrice List
    @ApiOperation(response = RtoPriceList.class, value = "Delete Rto Price") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteRtoPrice(@RequestBody List<RtoPriceList> deleteRtoPrice, @RequestParam String loginUserID) {
        rtoPriceListService.deleteRtoPrice(deleteRtoPrice, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All RtoPrice Details
    @ApiOperation(response = ReplicaRtoPrice.class, value = "Get all RtoPrice details") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllRtoPrice() {
        List<ReplicaRtoPrice> replicaRtoPrice = rtoPriceListService.getAllRtoPrice();
        return new ResponseEntity<>(replicaRtoPrice, HttpStatus.OK);
    }


    // Get Rto Price
    @ApiOperation(response = ReplicaRtoPrice.class, value = "Get a Rto Price") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getRtoPrice(@RequestParam String languageId, @RequestParam String companyId,
                                         @RequestParam String partnerId, @RequestParam Long lineNo) {

        ReplicaRtoPrice replicaRtoPrice = rtoPriceListService.getReplicaRtoPrice(languageId, companyId, partnerId, lineNo);
        return new ResponseEntity<>(replicaRtoPrice, HttpStatus.OK);
    }

    // Find Rto price
    @ApiOperation(response = ReplicaRtoPrice.class, value = "Find Rto Price") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findRtoPrice(@RequestBody FindRtoPrice findRtoPrice) throws Exception {
        List<ReplicaRtoPrice> findRtos = rtoPriceListService.findRtoPrice(findRtoPrice);
        return new ResponseEntity<>(findRtos, HttpStatus.OK);
    }


}
