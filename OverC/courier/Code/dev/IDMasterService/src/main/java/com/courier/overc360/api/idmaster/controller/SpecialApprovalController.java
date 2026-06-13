package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.replica.model.specialapproval.FindSpecialApproval;
import com.courier.overc360.api.idmaster.primary.model.specialapproval.UpdateSpecialApproval;
import com.courier.overc360.api.idmaster.primary.model.specialapproval.AddSpecialApproval;
import com.courier.overc360.api.idmaster.primary.model.specialapproval.SpecialApproval;
import com.courier.overc360.api.idmaster.replica.model.specialapproval.ReplicaSpecialApproval;
import com.courier.overc360.api.idmaster.service.SpecialApprovalService;
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
@Api(tags = {"SpecialApproval"}, value = "SpecialApproval  Operations related to SpecialApprovalController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "SpecialApproval", description = "Operations related to SpecialApproval")})
@RequestMapping("/specialApproval")
@RestController
public class SpecialApprovalController {

    @Autowired
    SpecialApprovalService specialApprovalService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/


    // Create SpecialApproval
    @ApiOperation(response = SpecialApproval.class, value = "Create SpecialApproval") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postSpecialApproval(@Valid @RequestBody AddSpecialApproval addSpecialApproval, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        SpecialApproval specialApproval = specialApprovalService.createSpecialApproval(addSpecialApproval, loginUserID);
        return new ResponseEntity<>(specialApproval, HttpStatus.OK);
    }

    // Update SpecialApproval
    @ApiOperation(response = SpecialApproval.class, value = "Update SpecialApproval") // label for swagger
    @PatchMapping("/{specialApprovalId}")
    public ResponseEntity<?> patchSpecialApproval(@PathVariable String specialApprovalId, @RequestParam String companyId, @RequestParam String languageId,
                                                  @RequestParam String loginUserID, @RequestBody UpdateSpecialApproval updateSpecialApproval)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        SpecialApproval updatedSpecialApproval =
                specialApprovalService.updateSpecialApproval(companyId, languageId, specialApprovalId, loginUserID, updateSpecialApproval);
        return new ResponseEntity<>(updatedSpecialApproval, HttpStatus.OK);
    }

    // Delete SpecialApproval
    @ApiOperation(response = SpecialApproval.class, value = "Delete SpecialApproval") // label for swagger
    @DeleteMapping("/{specialApprovalId}")
    public ResponseEntity<?> deleteSpecialApproval(@PathVariable String specialApprovalId, @RequestParam String languageId, @RequestParam String companyId,
                                                   @RequestParam String loginUserID) {
        specialApprovalService.deleteSpecialApproval(companyId, languageId, specialApprovalId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All SpecialApproval Details
    @ApiOperation(response = ReplicaSpecialApproval.class, value = "Get all ReplicaSpecialApproval details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllSpecialApproval() {
        List<ReplicaSpecialApproval> specialApprovalList = specialApprovalService.getAllSpecialApproval();
        return new ResponseEntity<>(specialApprovalList, HttpStatus.OK);
    }

    // Get SpecialApproval
    @ApiOperation(response = ReplicaSpecialApproval.class, value = "Get a ReplicaSpecialApproval") // label for swagger
    @GetMapping("/{specialApprovalId}")
    public ResponseEntity<?> getSpecialApproval(@PathVariable String specialApprovalId, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaSpecialApproval dbspecialApproval = specialApprovalService.getReplicaSpecialApproval(companyId, languageId, specialApprovalId);
        return new ResponseEntity<>(dbspecialApproval, HttpStatus.OK);
    }

    // Find SpecialApproval
    @ApiOperation(response = ReplicaSpecialApproval.class, value = "Find ReplicaSpecialApproval") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findSpecialApproval(@Valid @RequestBody FindSpecialApproval findSpecialApproval) throws Exception {
        List<ReplicaSpecialApproval> specialApprovalList = specialApprovalService.findSpecialApproval(findSpecialApproval);
        return new ResponseEntity<>(specialApprovalList, HttpStatus.OK);
    }
}
