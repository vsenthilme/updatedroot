package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.movingshipmentpricelist.AddMovingShipmentPriceList;
import com.courier.overc360.api.idmaster.primary.model.movingshipmentpricelist.MovingShipmentPriceList;
import com.courier.overc360.api.idmaster.replica.model.movingshipmentpricelist.FindMovingShipmentPriceList;
import com.courier.overc360.api.idmaster.replica.model.movingshipmentpricelist.ReplicaMovingShipmentPriceList;
import com.courier.overc360.api.idmaster.service.MovingShipmentPriceListService;
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
@Api(tags = {"MovingShipmentPriceList"}, value = "MovingShipmentPriceList operations related to MovingShipmentPriceListController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "MovingShipmentPriceList", description = "Operations related to MovingShipmentPriceList")})
@RequestMapping("/movingShipmentPriceList")
@RestController
public class MovingShipmentPriceListController {

    @Autowired
    private MovingShipmentPriceListService movingShipmentPriceListService;


    // Create MovingShipmentPriceList
    @ApiOperation(response = MovingShipmentPriceList.class, value = "Create MovingShipmentPriceList")
    // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postMovingShipmentPriceList(@Valid @RequestBody List<AddMovingShipmentPriceList> addMovingShipmentPriceLists, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<MovingShipmentPriceList> createMovingShipmentPriceList = movingShipmentPriceListService.createMovingShipmentPriceList(addMovingShipmentPriceLists, loginUserID);
        return new ResponseEntity<>(createMovingShipmentPriceList, HttpStatus.OK);
    }

    // Update MovingShipmentPriceList
    @ApiOperation(response = MovingShipmentPriceList.class, value = "Update MovingShipmentPriceList ")
    // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchMovingShipmentPriceList(@RequestParam String loginUserID, @RequestBody List<AddMovingShipmentPriceList> updateMovingShipmentPriceList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<MovingShipmentPriceList> updatedMovingShipmentPriceList = movingShipmentPriceListService.updateMovingShipmentPriceList(updateMovingShipmentPriceList, loginUserID);
        return new ResponseEntity<>(updatedMovingShipmentPriceList, HttpStatus.OK);
    }

    // Delete MovingShipmentPriceList
    @ApiOperation(response = MovingShipmentPriceList.class, value = "Delete MovingShipmentPriceList")
    // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteMovingShipmentPriceList(@RequestBody List<MovingShipmentPriceList> deleteMovingShipmentPriceList, @RequestParam String loginUserID) {
        movingShipmentPriceListService.deleteMovingShipmentPriceList(deleteMovingShipmentPriceList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All MovingShipmentPriceList Details
    @ApiOperation(response = ReplicaMovingShipmentPriceList.class, value = "Get all MovingShipmentPriceList details")
    // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMovingShipmentPriceList() {
        List<ReplicaMovingShipmentPriceList> replicaMovingShipmentPriceList = movingShipmentPriceListService.getAllMovingShipmentPriceList();
        return new ResponseEntity<>(replicaMovingShipmentPriceList, HttpStatus.OK);
    }


    // Get MovingShipmentPriceList
    @ApiOperation(response = ReplicaMovingShipmentPriceList.class, value = "Get a MovingShipmentPriceList")
    // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getMovingShipmentPriceList(@RequestParam String languageId, @RequestParam String companyId,
                                                        @RequestParam String partnerId, @RequestParam Long lineNo) {

        ReplicaMovingShipmentPriceList replicaMovingShipmentPriceList = movingShipmentPriceListService.getReplicaMovingShipmentPrice(languageId, companyId, partnerId, lineNo);
        return new ResponseEntity<>(replicaMovingShipmentPriceList, HttpStatus.OK);
    }

    // Find MovingShipmentPriceList
    @ApiOperation(response = ReplicaMovingShipmentPriceList.class, value = "Find MovingShipmentPriceList")
    // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findMovingShipmentPriceList(@RequestBody FindMovingShipmentPriceList findMovingShipmentPriceList) throws Exception {
        List<ReplicaMovingShipmentPriceList> findMovingShipment = movingShipmentPriceListService.findMovingShipmentPriceList(findMovingShipmentPriceList);
        return new ResponseEntity<>(findMovingShipment, HttpStatus.OK);
    }


}
