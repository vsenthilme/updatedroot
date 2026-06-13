package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.AddUnconsolidation;
import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.Unconsolidation;
import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.UnconsolidationDeleteInput;
import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.UpdateUnconsolidation;
import com.courier.overc360.api.midmile.replica.model.console.unconsolidation.FindUnconsolidation;
import com.courier.overc360.api.midmile.replica.model.console.unconsolidation.ReplicaUnconsolidation;
import com.courier.overc360.api.midmile.service.UnconsolidationService;
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
@Api(tags = {"Unconsolidation"}, value = "Unconsolidation Operations related to UnconsolidationController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Unconsolidation", description = "Operations related to Unconsolidation")})
@RequestMapping("/unconsolidation")
@RestController
public class UnconsolidationController {


    @Autowired
    UnconsolidationService unconsolidationService;

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    // Create new Unconsolidation
    @ApiOperation(response = Unconsolidation.class, value = "Create new Unconsolidation") // label for swagger
    @PostMapping("/create")
    public ResponseEntity<?> postUnconsolidation(@Valid @RequestBody AddUnconsolidation addUnconsolidation,
                                                 @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Unconsolidation unconsolidation = unconsolidationService.createUnconsolidation(addUnconsolidation, loginUserID);
        return new ResponseEntity<>(unconsolidation, HttpStatus.OK);
    }

    // Update Unconsolidation
    @ApiOperation(response = Unconsolidation.class, value = "Update Unconsolidation") // label for Swagger
    @PatchMapping("/update")
    public ResponseEntity<?> patchUnconsolidation(@Valid @RequestBody UpdateUnconsolidation updateUnconsolidation,
                                                  @RequestParam String loginUserID)
            throws InvocationTargetException, IllegalAccessException, IOException, CsvException {
        Unconsolidation unconsolidation = unconsolidationService.updateUnconsolidation(updateUnconsolidation, loginUserID);
        return new ResponseEntity<>(unconsolidation, HttpStatus.OK);
    }

    // Delete Unconsolidations
    @ApiOperation(response = Unconsolidation.class, value = "Delete Unconsolidations") // label for Swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteUnconsolidation(@Valid @RequestBody List<UnconsolidationDeleteInput> deleteInputs,
                                                   @RequestParam String loginUserID) throws IOException, CsvException {
        unconsolidationService.deleteUnconsolidation(deleteInputs, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    // Get All Unconsolidation Details
    @ApiOperation(response = ReplicaUnconsolidation.class, value = "Get all Unconsolidation Details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllUnconsolidations() {
        List<ReplicaUnconsolidation> unconsolidations = unconsolidationService.getAllUnconsolidations();
        return new ResponseEntity<>(unconsolidations, HttpStatus.OK);
    }

    // Get a Console
    @ApiOperation(response = ReplicaUnconsolidation.class, value = "Get Unconsolidation") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getUnconsolidation(@RequestParam String languageId, @RequestParam String companyId,
                                                @RequestParam String partnerId, @RequestParam String pieceId,
                                                @RequestParam String partnerMasterAirwayBill, @RequestParam String partnerHouseAirwayBill) {
        ReplicaUnconsolidation console = unconsolidationService.getUnconsolidationReplica(
                languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, pieceId);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }


    // Find Unconsolidated Shipments
    @ApiOperation(response = ReplicaUnconsolidation.class, value = "Find Unconsolidated Shipments") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> fetchUnconsolidated(@Valid @RequestBody FindUnconsolidation findConsole) throws Exception {
        List<ReplicaUnconsolidation> unconsolidationList = unconsolidationService.findUnconsolidatedShipments(findConsole);
        return new ResponseEntity<>(unconsolidationList, HttpStatus.OK);
    }

}
