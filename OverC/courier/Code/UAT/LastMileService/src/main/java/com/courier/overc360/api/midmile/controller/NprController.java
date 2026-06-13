package com.courier.overc360.api.midmile.controller;


import com.courier.overc360.api.midmile.primary.model.drs.AddDrs;
import com.courier.overc360.api.midmile.primary.model.drs.Drs;
import com.courier.overc360.api.midmile.primary.model.npr.Npr;
import com.courier.overc360.api.midmile.replica.model.drs.FindDrs;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import com.courier.overc360.api.midmile.replica.model.npr.FindNpr;
import com.courier.overc360.api.midmile.replica.model.npr.ReplicaNpr;
import com.courier.overc360.api.midmile.service.NprService;
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
@Api(tags = {"Npr"}, value = "Npr Operations Related to NprController")
@SwaggerDefinition(tags = {@Tag(name = "Npr", description = "Operations related to Npr")})
@RequestMapping("/npr")
public class NprController {

    @Autowired
    private NprService nprService;

    // Create Npr List
    @ApiOperation(response = Npr.class, value = "Create Npr List") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postNprLiist(@Valid @RequestBody List<Npr> nprList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Npr> createdNpr = nprService.createNpr(nprList, loginUserID);
        return new ResponseEntity<>(createdNpr, HttpStatus.OK);
    }

    // Update Npr List
    @ApiOperation(response = Npr.class, value = "Update Npr List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchNprList(@RequestParam String loginUserID, @RequestBody List<Npr> updateNprList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Npr> updatedNpr = nprService.updateNprList(updateNprList, loginUserID);
        return new ResponseEntity<>(updatedNpr, HttpStatus.OK);
    }

    // Delete Npr List
    @ApiOperation(response = Npr.class, value = "Delete Npr") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteNprList(@RequestBody List<Npr> deleteNprList, @RequestParam String loginUserID) {
        nprService.deleteNprList(deleteNprList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Npr Details
    @ApiOperation(response = ReplicaNpr.class, value = "Get all Npr details") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllNpr() {
        List<ReplicaNpr> replicaNpr = nprService.getAllNpr();
        return new ResponseEntity<>(replicaNpr, HttpStatus.OK);
    }

    // Get Npr
    @ApiOperation(response = ReplicaNpr.class, value = "Get a Npr") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getNpr(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String pickupId) {

        ReplicaNpr replicaNpr = nprService.getReplicaNpr(languageId, companyId, pickupId);
        return new ResponseEntity<>(replicaNpr, HttpStatus.OK);
    }

    // Find Npr
    @ApiOperation(response = ReplicaNpr.class, value = "Find ReplicaNpr") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findNpr(@RequestBody FindNpr findNpr) throws Exception {
        List<ReplicaNpr> findNprs = nprService.findNpr(findNpr);
        return new ResponseEntity<>(findNprs, HttpStatus.OK);
    }



}
