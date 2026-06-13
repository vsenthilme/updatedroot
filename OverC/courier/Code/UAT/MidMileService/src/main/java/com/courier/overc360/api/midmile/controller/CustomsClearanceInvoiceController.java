package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.console.Console;
import com.courier.overc360.api.midmile.primary.model.customsclearanceinvoice.AddCustomsClearanceInvoice;
import com.courier.overc360.api.midmile.primary.model.customsclearanceinvoice.CustomsClearanceInvoice;
import com.courier.overc360.api.midmile.primary.model.customsclearanceinvoice.UpdateCustomsClearanceInvoice;
import com.courier.overc360.api.midmile.replica.model.customsclearanceinvoice.FindCustomsClearanceInvoice;
import com.courier.overc360.api.midmile.replica.model.customsclearanceinvoice.ReplicaCustomsClearanceInvoice;
import com.courier.overc360.api.midmile.service.CustomsClearanceInvoiceService;
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
@Api(tags = {"CustomsClearanceInvoice"}, value = "CustomsClearanceInvoice Operations related to CustomsClearanceInvoice Controller") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "CustomsClearanceInvoice", description = "Operations related to CustomsClearanceInvoice")})
@RequestMapping("/customsClearanceInvoice")
@RestController
public class CustomsClearanceInvoiceController {

    @Autowired
    CustomsClearanceInvoiceService customsClearanceInvoiceService;

    /*--------------------------------------PRIMARY----------------------------------------*/

    // Create CustomsClearanceInvoice
    @ApiOperation(response = CustomsClearanceInvoice.class, value = "Create CustomsClearanceInvoice") // label for swagger
    @PostMapping("/create")
    public ResponseEntity<?> postCustomsClearanceInvoice(@Valid @RequestBody AddCustomsClearanceInvoice newCustomsClearanceInvoice, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CustomsClearanceInvoice createdCustomsClearanceInvoice = customsClearanceInvoiceService.createCustomsClearanceInvoice(newCustomsClearanceInvoice, loginUserID);
        return new ResponseEntity<>(createdCustomsClearanceInvoice, HttpStatus.OK);
    }

    // Create CustomsClearanceInvoice with console update
    @ApiOperation(response = CustomsClearanceInvoice.class, value = "Create CustomsClearanceInvoice") // label for swagger
    @PostMapping("/createUpdate")
    public ResponseEntity<?> postCustomsClearanceOnConsoleUpdate(@Valid @RequestBody Console newCustomsClearanceInvoice, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        customsClearanceInvoiceService.createCustomsClearance(newCustomsClearanceInvoice, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Update CustomsClearanceInvoice
    @ApiOperation(response = CustomsClearanceInvoice.class, value = "Update CustomsClearanceInvoice") // label for swagger
    @PatchMapping("/update")
    public ResponseEntity<?> patchCustomsClearanceInvoice(@RequestParam String languageId, @RequestParam String companyId,
                                       @RequestParam String partnerHouseAirwayBill, @RequestParam String houseAirwayBill, @RequestParam String invoiceNo,
                                       @RequestParam String loginUserID, @RequestBody UpdateCustomsClearanceInvoice updateCustomsClearanceInvoice)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CustomsClearanceInvoice updatedCustomsClearanceInvoice = customsClearanceInvoiceService.updateCustomsClearanceInvoice(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo,loginUserID, updateCustomsClearanceInvoice);
        return new ResponseEntity<>(updatedCustomsClearanceInvoice, HttpStatus.OK);
    }

    // Delete CustomsClearanceInvoice
    @ApiOperation(response = CustomsClearanceInvoice.class, value = "Delete CustomsClearanceInvoice") // label for swagger
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomsClearanceInvoice(@RequestParam String languageId, @RequestParam String companyId,
                                        @RequestParam String partnerHouseAirwayBill, @RequestParam String houseAirwayBill, @RequestParam String invoiceNo, @RequestParam String loginUserID) {
        customsClearanceInvoiceService.deleteCustomsClearanceInvoice(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All CustomsClearanceInvoice Details
    @ApiOperation(response = ReplicaCustomsClearanceInvoice.class, value = "Get all ReplicaCustomsClearanceInvoice details") // label for swagger
    @GetMapping("/getall")
    public ResponseEntity<?> getAllCustomsClearanceInvoices() {
        List<ReplicaCustomsClearanceInvoice> replicaCustomsClearanceInvoiceList = customsClearanceInvoiceService.getAllCustomsClearanceInvoice();
        return new ResponseEntity<>(replicaCustomsClearanceInvoiceList, HttpStatus.OK);
    }

    // Get CustomsClearanceInvoice
    @ApiOperation(response = ReplicaCustomsClearanceInvoice.class, value = "Get a ReplicaCustomsClearanceInvoice") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getCustomsClearanceInvoice(@RequestParam String languageId, @RequestParam String companyId,
                                     @RequestParam String partnerHouseAirwayBill, @RequestParam String houseAirwayBill, @RequestParam String invoiceNo) {

        ReplicaCustomsClearanceInvoice replicaCustomsClearanceInvoice = customsClearanceInvoiceService.replicaGetCustomsClearanceInvoice(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo);
        return new ResponseEntity<>(replicaCustomsClearanceInvoice, HttpStatus.OK);
    }

    // Find CustomsClearanceInvoice
    @ApiOperation(response = ReplicaCustomsClearanceInvoice.class, value = "Find ReplicaCustomsClearanceInvoice") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCity(@RequestBody FindCustomsClearanceInvoice findCustomsClearanceInvoice) throws Exception {
        List<ReplicaCustomsClearanceInvoice> createdCustomsClearanceInvoice = customsClearanceInvoiceService.findCustomsClearanceInvoice(findCustomsClearanceInvoice);
        return new ResponseEntity<>(createdCustomsClearanceInvoice, HttpStatus.OK);
    }
}
