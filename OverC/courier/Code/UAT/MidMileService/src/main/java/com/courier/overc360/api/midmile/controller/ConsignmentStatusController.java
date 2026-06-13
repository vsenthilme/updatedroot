package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.consignmentstatus.AddConsignmentStatus;
import com.courier.overc360.api.midmile.primary.model.consignmentstatus.ConsignmentStatus;
import com.courier.overc360.api.midmile.primary.model.consignmentstatus.UpdateConsignmentStatus;
import com.courier.overc360.api.midmile.replica.model.consignmentstatus.FindConsignmentNew;
import com.courier.overc360.api.midmile.replica.model.consignmentstatus.FindConsignmentStatus;
import com.courier.overc360.api.midmile.replica.model.consignmentstatus.ReplicaConsignmentStatus;
import com.courier.overc360.api.midmile.service.ConsignmentStatusService;
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
@Api(tags = {"ConsignmentStatus"}, value = "ConsignmentStatus Operations related to ConsignmentStatus")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ConsignmentStatus", description = "Operations related to ConsignmentStatus")})
@RequestMapping("/consignmentStatus")
@RestController
public class ConsignmentStatusController {

    @Autowired
    ConsignmentStatusService consignmentStatusService;

    /*========================================PRIMARY==================================================================*/

    //Create Consignment Status
    @ApiOperation(response = ConsignmentStatus.class, value = "Create ConsignmentStatus")
    @PostMapping("")
    public ResponseEntity<?> postConsignmentStatus(@Valid @RequestBody AddConsignmentStatus addConsignmentStatus, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ConsignmentStatus createConsignmentStatus = consignmentStatusService.createConsignmentStatus(addConsignmentStatus, loginUserID);
        return new ResponseEntity<>(createConsignmentStatus, HttpStatus.OK);
    }

    //Update Consignment Status
    @ApiOperation(response = ConsignmentStatus.class, value = "Update ConsignmentStatus")
    @PatchMapping("/update")
    public ResponseEntity<?> patchConsignmentStatus(@RequestParam String languageId, @RequestParam String companyId,
                                                    @RequestParam String pieceId, @RequestParam String houseAirwayBill,
                                                    @RequestBody UpdateConsignmentStatus updateConsignmentStatus, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ConsignmentStatus updatedConsignmentStatus = consignmentStatusService.updateConsignmentStatus(languageId, companyId,
                houseAirwayBill, pieceId, updateConsignmentStatus, loginUserID);
        return new ResponseEntity<>(updatedConsignmentStatus, HttpStatus.OK);
    }

    // Delete ConsignmentStatus
    @ApiOperation(response = ConsignmentStatus.class, value = "Delete ConsignmentStatus") // label for swagger
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteConsignmentStatus(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String houseAirwayBill,
                                                     @RequestParam String pieceId, @RequestParam String loginUserID) {
        consignmentStatusService.deleteConsignmentDetails(languageId, companyId, houseAirwayBill, pieceId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*========================================REPLICA==================================================================*/

    //Get All
    @ApiOperation(response = ReplicaConsignmentStatus.class, value = "Get All ConsignmentStatus")
    @GetMapping("")
    public ResponseEntity<?> getAllConsignmentStatus() {
        List<ReplicaConsignmentStatus> consignmentStatusList = consignmentStatusService.getAllConsignmentStatus();
        return new ResponseEntity<>(consignmentStatusList, HttpStatus.OK);
    }

    //Get ConsignmentStatus
    @ApiOperation(response = ReplicaConsignmentStatus.class, value = "Get ConsignmentStatus")
    @GetMapping("/get")
    public ResponseEntity<?> getConsignmentStatus(@RequestParam String languageId, @RequestParam String companyId,
                                                  @RequestParam String pieceId, @RequestParam String houseAirwayBill) {
        ReplicaConsignmentStatus dbConsignmentStatus = consignmentStatusService.getReplicaConsignmentStatus(languageId, companyId, houseAirwayBill, pieceId);
        return new ResponseEntity<>(dbConsignmentStatus, HttpStatus.OK);
    }

    //Find ConsignmentStatus
    @ApiOperation(response = ReplicaConsignmentStatus.class, value = "Find ConsignmentStatus")
    @PostMapping("/find")
    public ResponseEntity<?> findConsignmentStatus(@Valid @RequestBody FindConsignmentStatus findConsignmentStatus) throws Exception {
        List<ReplicaConsignmentStatus> consignmentStatusList = consignmentStatusService.findConsignmentStatus(findConsignmentStatus);
        return new ResponseEntity<>(consignmentStatusList, HttpStatus.OK);
    }

    //Add ConsignmentStatus
    @ApiOperation(response = ConsignmentStatus.class, value = "Add ConsignmentStatus")
    @PostMapping("/consignmentAdd")
    public ResponseEntity<?> consignmentAdd(@Valid @RequestBody List<FindConsignmentNew> findConsignmentNew,@RequestParam String loginUserID) throws Exception {
        List<ConsignmentStatus> consignmentStatusList = consignmentStatusService.addConsignmentStatus(findConsignmentNew,loginUserID);
        return new ResponseEntity<>(consignmentStatusList, HttpStatus.OK);
    }



}
