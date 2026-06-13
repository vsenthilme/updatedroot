package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.asrpricelist.AddAsrPriceList;
import com.courier.overc360.api.idmaster.primary.model.asrpricelist.AsrPriceList;
import com.courier.overc360.api.idmaster.replica.model.asrpriceList.FindAsrPriceList;
import com.courier.overc360.api.idmaster.replica.model.asrpriceList.ReplicaAsrPriceList;
import com.courier.overc360.api.idmaster.service.AsrPriceListService;
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
@Api(tags = {"AsrPriceList"}, value = "AsrPriceList operations related to AsrPriceListController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "AsrPriceList", description = "Operations related to AsrPriceList")})
@RequestMapping("/asrPriceList")
@RestController
public class AsrPriceListController {

    @Autowired
    private AsrPriceListService asrPriceListService;


    // Create AsrPriceList
    @ApiOperation(response = AsrPriceList.class, value = "Create AsrPriceList") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postAsrPriceList(@Valid @RequestBody List<AddAsrPriceList> addAsrPrices, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<AsrPriceList> createAsrPrice = asrPriceListService.createAsrPrice(addAsrPrices, loginUserID);
        return new ResponseEntity<>(createAsrPrice, HttpStatus.OK);
    }

    // Update AsrPrice List
    @ApiOperation(response = AsrPriceList.class, value = "Update AsrPrice List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchAsrPrice(@RequestParam String loginUserID, @RequestBody List<AddAsrPriceList> updateAsrPrice)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<AsrPriceList> updatedAsrPrice = asrPriceListService.updateAsrPrice(updateAsrPrice, loginUserID);
        return new ResponseEntity<>(updatedAsrPrice, HttpStatus.OK);
    }

    // Delete AsrPrice List
    @ApiOperation(response = AsrPriceList.class, value = "Delete Asr Price") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteAsrPrice(@RequestBody List<AsrPriceList> deleteAsrPrice, @RequestParam String loginUserID) {
        asrPriceListService.deleteAsrPrice(deleteAsrPrice, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All AsrPrice Details
    @ApiOperation(response = ReplicaAsrPriceList.class, value = "Get all AsrPrice details") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAsrPrice() {
        List<ReplicaAsrPriceList> replicaAsrPrice = asrPriceListService.getAllAsrPrice();
        return new ResponseEntity<>(replicaAsrPrice, HttpStatus.OK);
    }


    // Get Asr Price
    @ApiOperation(response = ReplicaAsrPriceList.class, value = "Get a Asr Price") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getAsrPrice(@RequestParam String languageId, @RequestParam String companyId,
                                         @RequestParam String partnerId, @RequestParam Long lineNo) {

        ReplicaAsrPriceList replicaAsrPrice = asrPriceListService.getReplicaAsrPrice(languageId, companyId, partnerId, lineNo);
        return new ResponseEntity<>(replicaAsrPrice, HttpStatus.OK);
    }

    // Find Asr price
    @ApiOperation(response = ReplicaAsrPriceList.class, value = "Find Asr Price") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findAsrPrice(@RequestBody FindAsrPriceList findAsrPrice) throws Exception {
        List<ReplicaAsrPriceList> findAsr = asrPriceListService.findAsrPrice(findAsrPrice);
        return new ResponseEntity<>(findAsr, HttpStatus.OK);
    }


}
