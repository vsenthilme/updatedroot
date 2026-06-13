package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.ndr.AddNdr;
import com.courier.overc360.api.midmile.primary.model.ndr.Ndr;
import com.courier.overc360.api.midmile.primary.model.ndr.UpdateNdr;
import com.courier.overc360.api.midmile.replica.model.ndr.FindNdr;
import com.courier.overc360.api.midmile.replica.model.ndr.ReplicaNdr;
import com.courier.overc360.api.midmile.service.NdrService;
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
@RestController
@Api(tags = {"Ndr"}, value = "Ndr Operations Related to NdrController")
@SwaggerDefinition(tags = {@Tag(name = "Ndr", description = "Operations related to Ndr")})
@RequestMapping("/ndr")
public class NdrController {
    @Autowired
    private NdrService ndrService;

    // Create Ndr List
    @ApiOperation(response = Ndr.class, value = "Create Ndr List") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postNdrList(@Valid @RequestBody List<AddNdr> ndrList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Ndr> createdNdr = ndrService.createNdr(ndrList, loginUserID);
        return new ResponseEntity<>(createdNdr, HttpStatus.OK);
    }

    // Update Ndr List
    @ApiOperation(response = Ndr.class, value = "Update Ndr List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchNdrList(@RequestParam String loginUserID, @RequestBody List<UpdateNdr> updateNdrList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Ndr> updatedNdr = ndrService.updateNdr(updateNdrList, loginUserID);
        return new ResponseEntity<>(updatedNdr, HttpStatus.OK);
    }

    // Delete Ndr List
    @ApiOperation(response = Ndr.class, value = "Delete Ndr") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteNdrList(@RequestBody List<Ndr> deleteNdrList, @RequestParam String loginUserID) {
        ndrService.deleteNdr(deleteNdrList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Ndr Details
    @ApiOperation(response = ReplicaNdr.class, value = "Get all Ndr details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllNdr() {
        List<ReplicaNdr> replicaNdr = ndrService.getAllNdr();
        return new ResponseEntity<>(replicaNdr, HttpStatus.OK);
    }

    // Get Ndr
    @ApiOperation(response = ReplicaNdr.class, value = "Get a Ndr") // label for swagger
    @GetMapping("/{deliveryId}")
    public ResponseEntity<?> getNdr(@PathVariable String deliveryId, @RequestParam String languageId, @RequestParam String companyId) {

        ReplicaNdr replicaNdr = ndrService.getReplicaNdr(languageId, companyId, deliveryId);
        return new ResponseEntity<>(replicaNdr, HttpStatus.OK);
    }

    // Find Ndr
    @ApiOperation(response = ReplicaNdr.class, value = "Find ReplicaNdr") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findNdr(@RequestBody FindNdr findNdr) throws Exception {
        List<ReplicaNdr> findNdrs = ndrService.findNdr(findNdr);
        return new ResponseEntity<>(findNdrs, HttpStatus.OK);
    }


}
