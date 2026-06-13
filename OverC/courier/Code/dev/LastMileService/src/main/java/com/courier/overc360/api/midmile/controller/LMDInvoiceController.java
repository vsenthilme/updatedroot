package com.courier.overc360.api.midmile.controller;


import com.courier.overc360.api.midmile.primary.model.invoice.DeleteInvoice;
import com.courier.overc360.api.midmile.primary.model.invoice.LMDInvoiceHeader;
import com.courier.overc360.api.midmile.replica.model.invoice.FindLMDInvoiceHeader;
import com.courier.overc360.api.midmile.replica.model.invoice.LMDInvoice;
import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaLMDInvoiceHeader;
import com.courier.overc360.api.midmile.service.LMDInvoiceService;
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
import java.text.ParseException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"LMDInvoice"}, value = "LMDInvoice Operations related to Invoice Controller") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "LMDInvoiceTable", description = "Operations related to LMDInvoice Controller")})
@RequestMapping("/invoice")
@RestController
public class LMDInvoiceController {
    @Autowired
    LMDInvoiceService invoiceService;

    @ApiOperation(response = LMDInvoiceHeader.class, value = "Create LMDInvoice")
    @PostMapping("/create")
    public ResponseEntity<?> createInvoice(@Valid @RequestBody List<LMDInvoiceHeader> lmdInvoiceHeaders, @RequestParam String loginUserID) {
        List<LMDInvoiceHeader> response = invoiceService.createInvoice(lmdInvoiceHeaders, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = LMDInvoiceHeader.class, value = "Update LMDInvoice")
    @PatchMapping("/update")
    public ResponseEntity<?> updateInvoice(@Valid @RequestBody List<LMDInvoiceHeader> lmdInvoiceHeaders, @RequestParam String loginUserID) {
        List<LMDInvoiceHeader> rs = invoiceService.updateInvoice(lmdInvoiceHeaders, loginUserID);
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @ApiOperation(response = DeleteInvoice.class, value = "Delete Invoice")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteInvoice(@Valid @RequestBody List<DeleteInvoice> deleteInvoices, @RequestParam String loginUserID) {
         invoiceService.deleteInvoice(deleteInvoices, loginUserID);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Ndr
    @ApiOperation(response = ReplicaLMDInvoiceHeader.class, value = "Find ReplicaLMDInvoiceHeader") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findNdr(@RequestBody FindLMDInvoiceHeader findLMDInvoiceHeader) throws Exception {
        List<ReplicaLMDInvoiceHeader> invoice = invoiceService.findInvoice(findLMDInvoiceHeader);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @ApiOperation(response = LMDInvoice.class, value = "Manual LMDInvoice Create")
    @PostMapping("/manual")
    public ResponseEntity<?> findManual(@Valid @RequestBody LMDInvoice lmdInvoice, @RequestParam String loginUserID) throws ParseException {
        LMDInvoiceHeader response = invoiceService.findLMDInvoice(lmdInvoice, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
