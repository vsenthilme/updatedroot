package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.ccr.*;
import com.courier.overc360.api.midmile.replica.model.ccr.FindCcr;
import com.courier.overc360.api.midmile.replica.model.ccr.ReplicaCcr;
import com.courier.overc360.api.midmile.replica.model.ccr.UpdateCCR;
import com.courier.overc360.api.midmile.service.CcrService;
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
@Api(tags = {"Ccr"}, value = "Ccr Operations related to CcrController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Ccr", description = "Operations related to Ccr")})
@RequestMapping("/ccr")
@RestController
public class CcrController {

    @Autowired
    CcrService ccrService;

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    // Create new Ccr
    @ApiOperation(response = Ccr.class, value = "Create new Ccr") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postCcr(@Valid @RequestBody List<AddCcr> addCcrList,
                                     @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Ccr> ccr = ccrService.createCcr(addCcrList, loginUserID);
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }

    // Update Ccr
    @ApiOperation(response = Ccr.class, value = "Update Ccr") // label for Swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchCcr(@RequestBody List<UpdateCcr> updateCcrList,
                                      @RequestParam String loginUserID)
            throws InvocationTargetException, IllegalAccessException, IOException, CsvException {
        List<Ccr> ccr = ccrService.updateCcr(updateCcrList, loginUserID);
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }

    // Delete Ccr
    @ApiOperation(response = Ccr.class, value = "Delete Ccr") // label for Swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteCcr(@Valid @RequestBody List<CcrDeleteInput> ccrDeleteInputs,
                                       @RequestParam String loginUserID) throws IOException, CsvException {
        ccrService.deleteCcr(ccrDeleteInputs, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    // Get All Ccr Details
    @ApiOperation(response = ReplicaCcr.class, value = "Get all Ccr Details")
    // label for swagger
    @GetMapping(" ")
    public ResponseEntity<?> getAllReplicaCcr() {
        List<ReplicaCcr> ccr = ccrService.getAllCcr();
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }

//    // Get a Ccr
//    @ApiOperation(response = ReplicaCcr.class, value = "Get a Ccr")
//    @GetMapping("/{ccrId}")
//    public ResponseEntity<?> getCcrReplica(@PathVariable String ccrId, @RequestParam String languageId,
//                                           @RequestParam String companyId, @RequestParam String partnerId,
//                                           @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill, @RequestParam String consoleId) {
//        ReplicaCcr ccr = ccrService.getCcrReplica(languageId, companyId, partnerId,
//                masterAirwayBill, houseAirwayBill, consoleId, ccrId);
//        return new ResponseEntity<>(ccr, HttpStatus.OK);
//    }

    // Get a Ccr
    @ApiOperation(response = ReplicaCcr.class, value = "Get a Ccr")
    @GetMapping("/{ccrId}")
    public ResponseEntity<?> getCCRReplica(@PathVariable String ccrId, @RequestParam String languageId,
                                           @RequestParam String companyId, @RequestParam String partnerId,
                                           @RequestParam String partnerMasterAirwayBill, @RequestParam String partnerHouseAirwayBill,
                                           @RequestParam String consoleId, @RequestParam String pieceId) {
        ReplicaCcr ccr = ccrService.getCcrReplica(languageId, companyId, partnerId,
                partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, pieceId);
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }

    // Find Ccr
    @ApiOperation(response = ReplicaCcr.class, value = "Find Ccr") // label for swagger
    @PostMapping("/findCcr")
    public ResponseEntity<?> findCcr(@Valid @RequestBody FindCcr findCcr) throws Exception {
        List<ReplicaCcr> ccrList = ccrService.findCcr(findCcr);
        return new ResponseEntity<>(ccrList, HttpStatus.OK);
    }

    /*---------------------------------------------------UPDATE CCR from PDF - BAYAN-----------------------------------------------------*/
    // Update Ccr from Bayan
    @ApiOperation(response = Ccr.class, value = "Update Ccr from Bayan PDF") // label for Swagger
    @PostMapping("/update/pdf")
    public ResponseEntity<?> patchCcrFromPdf(@RequestBody List<UpdateCCR> updateCcrList) throws IOException, CsvException {
        String ccr = ccrService.updateCCRFromBayan(updateCcrList);
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }

    // Update Bayan Value
    @ApiOperation(response = BayanUpdate.class, value = "Update Bayan Value")
    @PatchMapping("/update/bayan")
    public ResponseEntity<?> patchBayanValue(@Valid @RequestBody List<BayanUpdate> bayanUpdateList) {
        String bayan = ccrService.updateBayan(bayanUpdateList);
        return new ResponseEntity<>(bayan, HttpStatus.OK);
    }
}
