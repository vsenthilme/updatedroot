package com.courier.overc360.api.midmile.controller;


import com.courier.overc360.api.midmile.primary.model.bagtracking.AddBagTracking;
import com.courier.overc360.api.midmile.primary.model.bagtracking.BagTracking;
import com.courier.overc360.api.midmile.primary.model.fueltracking.FuelTracking;
import com.courier.overc360.api.midmile.primary.model.invoice.InvoiceHeader;
import com.courier.overc360.api.midmile.replica.model.fueltracking.FindFuelTracking;
import com.courier.overc360.api.midmile.replica.model.fueltracking.ReplicaFuelTracking;
import com.courier.overc360.api.midmile.replica.model.invoice.FindInvoiceHeader;
import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaInvoiceHeader;
import com.courier.overc360.api.midmile.service.InvoiceService;
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
@Api(tags = {"Invoice"}, value = "Invoice  Operations related to InvoiceController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Invoice", description = "Operations related to Invoice")})
@RequestMapping("/invoiceHeader")
@RestController
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/
    // Create BagTracking
    @ApiOperation(response = InvoiceHeader.class, value = "Create Invoice") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postBagTracking(@Valid @RequestBody List<InvoiceHeader> invoiceHeader, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<InvoiceHeader> invoiceHeaderList = invoiceService.createInvoice(invoiceHeader, loginUserID);
        return new ResponseEntity<>(invoiceHeaderList, HttpStatus.OK);
    }

    // Update InvoiceHeader
    @ApiOperation(response = InvoiceHeader.class, value = "Update Invoice")
    @PatchMapping("/update")
    public ResponseEntity<?> patchInvoice(@Valid @RequestBody List<InvoiceHeader> invoiceHeaders, @RequestParam String loginUserID) {
        List<InvoiceHeader> invoiceHeaderList = invoiceService.updateInvoice(invoiceHeaders, loginUserID);
        return new ResponseEntity<>(invoiceHeaderList, HttpStatus.OK);
    }
    //Delete InvoiceHeader
    @ApiOperation(response = InvoiceHeader.class, value = "Delete InvoiceHeader")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteInvoiceHeader(@Valid @RequestBody List<InvoiceHeader> invoiceHeaders, @RequestParam String loginUserID) {
        invoiceService.deleteInvoiceHeader(invoiceHeaders, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All InvoiceHeader Details
    @ApiOperation(response = ReplicaInvoiceHeader.class, value = "Get all InvoiceHeader Details")
    @GetMapping("")
    public ResponseEntity<?> getAllInvoiceHeaderDetails() {
        List<ReplicaInvoiceHeader> invoiceHeaderList = invoiceService.getAll();
        return new ResponseEntity<>(invoiceHeaderList, HttpStatus.OK);
    }

    //Get InvoiceHeader
    @ApiOperation(response = ReplicaInvoiceHeader.class, value = "Get a InvoiceHeader")
    @GetMapping("/{invoiceNo}")
    public ResponseEntity<?> getInvoiceHeader(@PathVariable String invoiceNo, @RequestParam String companyId, @RequestParam String languageId) {
        ReplicaInvoiceHeader dbInvoiceHeader = invoiceService.getReplicaInvoiceHeader(companyId, languageId, invoiceNo);
        return new ResponseEntity<>(dbInvoiceHeader, HttpStatus.OK);
    }

    //Find InvoiceHeader
    @ApiOperation(response = ReplicaInvoiceHeader.class, value = "Find InvoiceHeader")
    @PostMapping("/find")
    public ResponseEntity<?> findInvoiceHeader(@Valid @RequestBody FindInvoiceHeader findInvoiceHeader) throws Exception {
        List<ReplicaInvoiceHeader> invoiceHeaderList = invoiceService.findInvoiceHeader(findInvoiceHeader);
        return new ResponseEntity<>(invoiceHeaderList, HttpStatus.OK);
    }


}
